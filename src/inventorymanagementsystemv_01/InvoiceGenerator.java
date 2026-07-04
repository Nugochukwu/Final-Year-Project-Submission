package inventorymanagementsystemv_01;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class InvoiceGenerator {

    // -------------------------------------------------------
    //  Data model for a single invoice line item
    // -------------------------------------------------------
    public static class InvoiceItem {
        public String name;
        public String type;
        public int    currentQty;
        public int    requestedQty;

        public InvoiceItem(String name, String type,
                           int currentQty, int requestedQty) {
            this.name         = name;
            this.type         = type;
            this.currentQty   = currentQty;
            this.requestedQty = requestedQty;
        }
    }

    // -------------------------------------------------------
    //  Pull low stock items from database
    // -------------------------------------------------------
    public static List<InvoiceItem> getLowStockItems(int userId) {
        List<InvoiceItem> items = new ArrayList<>();
        String sql = "SELECT name, type, quantity FROM inventory " +
                     "WHERE userId = ? AND quantity < 10 ORDER BY quantity ASC";
        try (
                Connection con = DatabaseConfig.getConnection();
                //DriverManager.getConnection("jdbc:mysql://localhost/sys", "root", "Vitamin101##");
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new InvoiceItem(
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getInt("quantity"),
                    10 - rs.getInt("quantity") // default request = top up to 10
                ));
            }
        } catch (SQLException e) {
            System.err.println("Low stock fetch failed: " + e.getMessage());
        }
        return items;
    }

    // -------------------------------------------------------
    //  Generate PDF invoice
    // -------------------------------------------------------
    public static boolean generatePDF(List<InvoiceItem> items,
                                      String outputPath,
                                      String logoPath) {
        try {
            Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(doc, new FileOutputStream(outputPath));
            doc.open();

            // --- Fonts ---
            Font titleFont  = new Font(Font.FontFamily.HELVETICA, 14,
                                       Font.BOLD,   new BaseColor(33, 55, 98));
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 8,
                                       Font.BOLD,   BaseColor.WHITE);
            Font bodyFont   = new Font(Font.FontFamily.HELVETICA, 8,
                                       Font.NORMAL, BaseColor.BLACK);
            Font labelFont  = new Font(Font.FontFamily.HELVETICA, 8,
                                       Font.BOLD,   new BaseColor(80, 80, 80));
            Font smallFont  = new Font(Font.FontFamily.HELVETICA, 7,
                                       Font.NORMAL, new BaseColor(120, 120, 120));

            // --- Logo + Company header ---
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{1f, 2f});
            headerTable.setSpacingAfter(8);

            // Logo cell
            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            
            java.io.InputStream logoStream = InvoiceGenerator.class
                    .getClassLoader().getResourceAsStream(logoPath);
            if (logoStream != null) {
                byte[] logoBytes = logoStream.readAllBytes();
                Image logo = Image.getInstance(logoBytes);
                logo.scaleToFit(100, 60);
                logoCell.addElement(logo);
            } else {
                logoCell.addElement(new Phrase(
                    CompanySettings.getCompanyName(), titleFont));
            }
            
            headerTable.addCell(logoCell);

            // Company info cell
            PdfPCell infoCell = new PdfPCell();
            infoCell.setBorder(Rectangle.NO_BORDER);
            infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoCell.addElement(new Phrase(
                CompanySettings.getCompanyName() + "\n", titleFont));
            infoCell.addElement(new Phrase(
                CompanySettings.getAddress() + "\n", labelFont));
            infoCell.addElement(new Phrase(
                "Tel: " + CompanySettings.getContactPhone() + "\n", labelFont));
            infoCell.addElement(new Phrase(
                "Email: " + CompanySettings.getContactEmail(), labelFont));
            headerTable.addCell(infoCell);
            doc.add(headerTable);

            // --- Divider line ---
            PdfPTable divider = new PdfPTable(1);
            divider.setWidthPercentage(100);
            divider.setSpacingAfter(8);
            PdfPCell divCell = new PdfPCell();
            divCell.setBackgroundColor(new BaseColor(33, 55, 98));
            divCell.setFixedHeight(3);
            divCell.setBorder(Rectangle.NO_BORDER);
            divider.addCell(divCell);
            doc.add(divider);

            // --- Invoice title + metadata ---
            String refNumber = "PR-" + new SimpleDateFormat(
                    "yyyyMMdd-HHmm").format(new Date());
            String dateStr   = new SimpleDateFormat(
                    "dd MMMM yyyy").format(new Date());

            PdfPTable metaTable = new PdfPTable(2);
            metaTable.setWidthPercentage(100);
            metaTable.setSpacingAfter(8);

            PdfPCell titleCell = new PdfPCell(
                new Phrase("PURCHASE REQUEST", titleFont));
            titleCell.setBorder(Rectangle.NO_BORDER);
            metaTable.addCell(titleCell);

            PdfPCell metaRight = new PdfPCell();
            metaRight.setBorder(Rectangle.NO_BORDER);
            metaRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
            metaRight.addElement(new Phrase("Reference: " + refNumber + "\n",
                                            labelFont));
            metaRight.addElement(new Phrase("Date: " + dateStr, labelFont));
            metaTable.addCell(metaRight);
            doc.add(metaTable);

            // --- Items table ---
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(80);
            table.setWidths(new float[]{3f, 1.5f, 1.5f, 1.5f});
            table.setSpacingAfter(10);

            // Table headers
            String[] headers = {"Item Name", "Type",
                                 "Current Stock", "Qty Requested"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(new BaseColor(33, 55, 98));
                cell.setPadding(4);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColor(new BaseColor(33, 55, 98));
                table.addCell(cell);
            }

            // Table rows
            boolean alt = false;
            for (InvoiceItem item : items) {
                BaseColor rowColor = alt
                    ? new BaseColor(240, 244, 255)
                    : BaseColor.WHITE;

                PdfPCell nameCell = new PdfPCell(
                    new Phrase(item.name, bodyFont));
                nameCell.setBackgroundColor(rowColor);
                nameCell.setPadding(3);
                table.addCell(nameCell);

                PdfPCell typeCell = new PdfPCell(
                    new Phrase(item.type, bodyFont));
                typeCell.setBackgroundColor(rowColor);
                typeCell.setPadding(3);
                typeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(typeCell);

                PdfPCell qtyCell = new PdfPCell(
                    new Phrase(String.valueOf(item.currentQty), bodyFont));
                qtyCell.setBackgroundColor(rowColor);
                qtyCell.setPadding(3);
                qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                // Highlight critical stock in red
                if (item.currentQty <= 3)
                    qtyCell.setBackgroundColor(new BaseColor(255, 220, 220));
                table.addCell(qtyCell);

                PdfPCell reqCell = new PdfPCell(
                    new Phrase(String.valueOf(item.requestedQty), bodyFont));
                reqCell.setBackgroundColor(rowColor);
                reqCell.setPadding(3);
                reqCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(reqCell);

                alt = !alt;
            }
            doc.add(table);

            // --- Notes section ---
            doc.add(new Paragraph("Notes / Justification:", labelFont));
            doc.add(new Paragraph(" ", smallFont));
            PdfPTable notesTable = new PdfPTable(1);
            notesTable.setWidthPercentage(100);
            notesTable.setSpacingAfter(30);
            PdfPCell notesCell = new PdfPCell();
            notesCell.setFixedHeight(40);
            notesCell.setBorderColor(new BaseColor(200, 200, 200));
            notesTable.addCell(notesCell);
            doc.add(notesTable);

            // --- Signature section ---
            PdfPTable sigTable = new PdfPTable(2);
            sigTable.setWidthPercentage(100);
            sigTable.setWidths(new float[]{1f, 1f});

            PdfPCell sig1 = new PdfPCell();
            sig1.setBorder(Rectangle.NO_BORDER);
            sig1.addElement(new Phrase("Requested by:", labelFont));
            sig1.addElement(new Phrase("\n\n\n", bodyFont));
            sig1.addElement(new Phrase(
                "________________________________", bodyFont));
            sig1.addElement(new Phrase(
                "\nName & Signature                    Date", smallFont));
            sigTable.addCell(sig1);

            PdfPCell sig2 = new PdfPCell();
            sig2.setBorder(Rectangle.NO_BORDER);
            sig2.addElement(new Phrase("Approved by:", labelFont));
            sig2.addElement(new Phrase("\n\n\n", bodyFont));
            sig2.addElement(new Phrase(
                "________________________________", bodyFont));
            sig2.addElement(new Phrase(
                "\nName & Signature                    Date", smallFont));
            sigTable.addCell(sig2);
            doc.add(sigTable);

            // --- Footer ---
            doc.add(new Paragraph("\n", smallFont));
            Paragraph footer = new Paragraph(
                "This document was generated by " +
                CompanySettings.getCompanyName() +
                " Inventory Management System  •  " + dateStr, smallFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            doc.add(footer);

            doc.close();
            return true;

        } catch (Exception e) {
            System.err.println("PDF generation failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // -------------------------------------------------------
    //  Generate Word (.docx) invoice
    // -------------------------------------------------------
    public static boolean generateDOCX(List<InvoiceItem> items,
                                       String outputPath,
                                       String logoPath) {
        try (XWPFDocument doc = new XWPFDocument()) {

            String dateStr   = new SimpleDateFormat(
                    "dd MMMM yyyy").format(new Date());
            String refNumber = "PR-" + new SimpleDateFormat(
                    "yyyyMMdd-HHmm").format(new Date());

            // --- Logo ---
            java.io.InputStream logoStream = InvoiceGenerator.class
                    .getClassLoader().getResourceAsStream(logoPath);
            if (logoStream != null) {
                XWPFParagraph logoPara = doc.createParagraph();
                XWPFRun logoRun = logoPara.createRun();
                int fmt = logoPath.toLowerCase().endsWith(".png")
                        ? XWPFDocument.PICTURE_TYPE_PNG
                        : XWPFDocument.PICTURE_TYPE_JPEG;
                logoRun.addPicture(logoStream, fmt, "logo", 914400, 548640);
            }

            // --- Company name ---
            XWPFParagraph compPara = doc.createParagraph();
            compPara.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun compRun = compPara.createRun();
            compRun.setText(CompanySettings.getCompanyName());
            compRun.setBold(true);
            compRun.setFontSize(18);
            compRun.setColor("21 37 62".replace(" ", ""));

            // Company details
            addDocxLine(doc, CompanySettings.getAddress(), 9, false);
            addDocxLine(doc, "Tel: " + CompanySettings.getContactPhone(), 9, false);
            addDocxLine(doc, "Email: " + CompanySettings.getContactEmail(), 9, false);
            addDocxLine(doc, " ", 9, false);

            // --- Title ---
            XWPFParagraph titlePara = doc.createParagraph();
            XWPFRun titleRun = titlePara.createRun();
            titleRun.setText("PURCHASE REQUEST");
            titleRun.setBold(true);
            titleRun.setFontSize(16);
            titleRun.setColor("213742");

            addDocxLine(doc, "Reference: " + refNumber, 10, false);
            addDocxLine(doc, "Date: " + dateStr, 10, false);
            addDocxLine(doc, " ", 10, false);

            // --- Items table ---
            XWPFTable table = doc.createTable(items.size() + 1, 4);
            table.setWidth("100%");

            // Header row
            String[] headers = {"Item Name", "Type",
                                 "Current Stock", "Qty Requested"};
            XWPFTableRow headerRow = table.getRow(0);
            for (int i = 0; i < headers.length; i++) {
                XWPFTableCell cell = headerRow.getCell(i);
                cell.setText(headers[i]);
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                run.setBold(true);
                run.setFontSize(10);
            }

            // Data rows
            for (int r = 0; r < items.size(); r++) {
                InvoiceItem item = items.get(r);
                XWPFTableRow row = table.getRow(r + 1);
                row.getCell(0).setText(item.name);
                row.getCell(1).setText(item.type);
                row.getCell(2).setText(String.valueOf(item.currentQty));
                row.getCell(3).setText(String.valueOf(item.requestedQty));
            }

            // --- Notes ---
            addDocxLine(doc, " ", 10, false);
            addDocxLine(doc, "Notes / Justification:", 10, true);
            addDocxLine(doc, " ", 10, false);
            addDocxLine(doc, " ", 10, false);
            addDocxLine(doc, " ", 10, false);

            // --- Signatures ---
            addDocxLine(doc, " ", 10, false);
            addDocxLine(doc, "Requested by:                              " +
                             "Approved by:", 10, true);
            addDocxLine(doc, " ", 10, false);
            addDocxLine(doc, " ", 10, false);
            addDocxLine(doc, "________________________________            " +
                             "________________________________", 10, false);
            addDocxLine(doc, "Name & Signature          Date             " +
                             "Name & Signature          Date", 9, false);

            // --- Footer ---
            addDocxLine(doc, " ", 9, false);
            XWPFParagraph footerPara = doc.createParagraph();
            footerPara.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun footerRun = footerPara.createRun();
            footerRun.setText("Generated by " +
                CompanySettings.getCompanyName() +
                " Inventory Management System  •  " + dateStr);
            footerRun.setFontSize(8);
            footerRun.setColor("888888");

            // Write file
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                doc.write(fos);
            }
            return true;

        } catch (Exception e) {
            System.err.println("DOCX generation failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static void addDocxLine(XWPFDocument doc, String text,
                                    int size, boolean bold) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.setText(text);
        r.setFontSize(size);
        r.setBold(bold);
    }
}