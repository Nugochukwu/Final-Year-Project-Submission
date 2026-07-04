/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventorymanagementsystemv_01;

import custom_ui.CustomTitleBar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Ugochukwu Nwodo
 */
    public class AddItemScreen extends javax.swing.JFrame {
    private CustomTitleBar titleBar;
    private int currentUserId;
    private staff_dashboard parentDashboard;

   
     // Creates new form AddItemScreen

// --- Camera counter fields ---
    private static final int MIN_ITEM_AREA = 500;
    private javax.swing.JLabel    previewLabel;
    private javax.swing.JLabel    statusLabel;
    private javax.swing.JComboBox<ItemCounter.CameraDevice> cameraComboBox;
    private javax.swing.JTextField ipStreamField;
    private javax.swing.JButton    captureBtn;
    private javax.swing.JButton    uploadBtn;

    public AddItemScreen(int userId, staff_dashboard parent) {
        this.currentUserId = userId;
        this.parentDashboard = parent;
        setUndecorated(true);
        initComponents();
        setupCameraPanel();

        javax.swing.JPanel wrapper = new javax.swing.JPanel(new java.awt.BorderLayout());
        wrapper.setOpaque(true);
        wrapper.setBackground(new java.awt.Color(0, 0, 0, 0)); 

        titleBar = new CustomTitleBar(this);
        titleBar.setCloseAction(CustomTitleBar.CloseAction.DISPOSE);
        titleBar.setShowMinimize(false);
        titleBar.setShowClose(true);
        titleBar.setCornerRadius(20);
        titleBar.setBarColor(new java.awt.Color(45,59,111));
        titleBar.setCloseNormalColor(new java.awt.Color(45,59,111));
        titleBar.setMinimizeNormalColor(new java.awt.Color(45,59,111));
        titleBar.setCloseHoverColor(new java.awt.Color(255, 0, 0));
        titleBar.setTitleText("Add Item Screen");

        wrapper.add(titleBar, java.awt.BorderLayout.NORTH);
        wrapper.add(roundedPanel1, java.awt.BorderLayout.CENTER);
        
        getRootPane().setOpaque(false);
        getRootPane().setBackground(new java.awt.Color(0, 0, 0, 0));

        setContentPane(wrapper);
        setSize(480, 680);
        setLocationRelativeTo(null);
        
        
    }


    private void setupCameraPanel() {

    javax.swing.JPanel cameraSection = new javax.swing.JPanel();
    cameraSection.setOpaque(false);
    cameraSection.setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.insets  = new java.awt.Insets(4, 4, 4, 4);
    gbc.fill    = java.awt.GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    // Camera dropdown
    cameraComboBox = new javax.swing.JComboBox<>();
    loadCameraDropdown();
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    cameraSection.add(cameraComboBox, gbc);

    // IP field — hidden unless IP Camera selected
    ipStreamField = new javax.swing.JTextField("http://192.168.1.100:8080/video");
    ipStreamField.setVisible(false);
    gbc.gridy = 1;
    cameraSection.add(ipStreamField, gbc);

    // Capture and upload buttons
    captureBtn = new javax.swing.JButton("Capture & Count");
    uploadBtn  = new javax.swing.JButton("Upload Image");
    gbc.gridy = 2; gbc.gridwidth = 1;
    gbc.gridx = 0; cameraSection.add(captureBtn, gbc);
    gbc.gridx = 1; cameraSection.add(uploadBtn,  gbc);

    // Preview
    previewLabel = new javax.swing.JLabel("No image captured",
            javax.swing.SwingConstants.CENTER);
    previewLabel.setPreferredSize(new java.awt.Dimension(300, 160));
    previewLabel.setBorder(javax.swing.BorderFactory
            .createLineBorder(java.awt.Color.GRAY));
    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
    cameraSection.add(previewLabel, gbc);

    // Status
    statusLabel = new javax.swing.JLabel("", javax.swing.SwingConstants.CENTER);
    statusLabel.setForeground(java.awt.Color.DARK_GRAY);
    gbc.gridy = 4;
    cameraSection.add(statusLabel, gbc);

    // Wire events
    cameraComboBox.addActionListener(e -> {
        ItemCounter.CameraDevice selected =
            (ItemCounter.CameraDevice) cameraComboBox.getSelectedItem();
        ipStreamField.setVisible(selected != null && selected.isIPCamera());
        revalidate();
        repaint();
    });
    captureBtn.addActionListener(e -> handleCapture());
    uploadBtn.addActionListener(e  -> handleUpload());

    roundedPanel1.setLayout(new java.awt.BorderLayout());
    roundedPanel1.add(roundedPanel2, java.awt.BorderLayout.CENTER);
    roundedPanel1.add(cameraSection, java.awt.BorderLayout.SOUTH);

    // Expand frame to fit everything
    setSize(500, 1000);
    // wor in progress :)  Autocomplete popup for item name field
javax.swing.JPopupMenu suggestionPopup = new javax.swing.JPopupMenu();

jTextField1.getDocument().addDocumentListener(
        new javax.swing.event.DocumentListener() {
    private void update() {
        String text = jTextField1.getText();
        suggestionPopup.setVisible(false);
        suggestionPopup.removeAll();

        List<String> suggestions =
                ItemRecommender.recommend(currentUserId, text);
        if (suggestions.isEmpty()) return;

        for (String s : suggestions) {
            javax.swing.JMenuItem item = new javax.swing.JMenuItem(s);
            item.setFont(new java.awt.Font("Segoe UI",
                    java.awt.Font.PLAIN, 12));
            item.addActionListener(e -> {
                jTextField1.setText(s);
                suggestionPopup.setVisible(false);
            });
            suggestionPopup.add(item);
        }
        suggestionPopup.show(jTextField1, 0, jTextField1.getHeight());
        jTextField1.requestFocus();
    }

    public void insertUpdate(javax.swing.event.DocumentEvent e)  { update(); }
    public void removeUpdate(javax.swing.event.DocumentEvent e)  { update(); }
    public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
});
}

    private void loadCameraDropdown() {
        cameraComboBox.removeAllItems();
        try {
            for (ItemCounter.CameraDevice dev : ItemCounter.getAvailableCameras()) {
                cameraComboBox.addItem(dev);
            }
        } catch (Throwable t) {
            // OpenCV not loaded — camera scanning unavailable
            System.err.println("Camera scan failed: " + t.getMessage());
            cameraComboBox.addItem(
                new ItemCounter.CameraDevice(-2, "Camera unavailable (OpenCV not loaded)", ""));
        }
        // Always add IP camera option — works without OpenCV
        cameraComboBox.addItem(
            new ItemCounter.CameraDevice(-1, "IP Camera (Phone/Network)", ""));
}
    //  Capture from selected camera source
    private void handleCapture() {
    ItemCounter.CameraDevice selected =
        (ItemCounter.CameraDevice) cameraComboBox.getSelectedItem();
    if (selected == null) return;

    // Block capture for the unavailable placeholder
    if (selected.index == -2) {
        statusLabel.setText("Camera unavailable — use Upload Image instead.");
        statusLabel.setForeground(new java.awt.Color(180, 60, 60));
        return;
    }

    statusLabel.setText("Capturing...");
    captureBtn.setEnabled(false);

    new Thread(() -> {
        try {
            java.awt.image.BufferedImage preview;
            if (selected.index == -1) {
                preview = ItemCounter.previewFromIPStream(
                        ipStreamField.getText().trim());
            } else {
                preview = ItemCounter.previewFromCamera(selected.index);
            }
            final java.awt.image.BufferedImage finalPreview = preview;
            javax.swing.SwingUtilities.invokeLater(() -> {
                captureBtn.setEnabled(true);
                if (finalPreview != null) showPreview(finalPreview);
                runGeminiCount(finalPreview);
            });
        } catch (Throwable t) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                captureBtn.setEnabled(true);
                statusLabel.setText("Capture failed: " + t.getMessage());
                statusLabel.setForeground(new java.awt.Color(180, 60, 60));
            });
        }
    }).start();
}

private void handleUpload() {
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Images", "jpg", "jpeg", "png", "bmp"));
    if (chooser.showOpenDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION)
        return;

    java.io.File file = chooser.getSelectedFile();
    showPreview(new javax.swing.ImageIcon(file.getAbsolutePath()).getImage());
    statusLabel.setText("Counting with Gemini...");
    uploadBtn.setEnabled(false);

    new Thread(() -> {
        ItemCounter.CountResult result = GeminiConfig.isConfigured()
            ? ItemCounter.countWithGemini(file, GeminiConfig.getApiKey())
            : ItemCounter.countFromFile(file, MIN_ITEM_AREA);

        javax.swing.SwingUtilities.invokeLater(() -> {
            uploadBtn.setEnabled(true);
            applyCountResult(result);
        });
    }).start();
}

// Shared Gemini counting logic
private void runGeminiCount(java.awt.image.BufferedImage image) {
    if (image == null) { statusLabel.setText("No image."); return; }
    statusLabel.setText("AI is counting...");
    new Thread(() -> {
        ItemCounter.CountResult result;
        // Prefer Claude if key available, then Gemini, then OpenCV
        if (!ClaudeConfig.getApiKey().isBlank()) {
            result = ItemCounter.countWithClaude(image,
                    ClaudeConfig.getApiKey());
            if (result.isSuccess()) {
                final int c = result.count;
                javax.swing.SwingUtilities.invokeLater(() -> {
                    jTextField2.setText(String.valueOf(c));
                    statusLabel.setText("Claude counted " + c + " items");
                    statusLabel.setForeground(
                            new java.awt.Color(33, 100, 60));
                });
                return;
            }
        }
        if (GeminiConfig.isConfigured()) {
            result = ItemCounter.countWithGemini(image,
                    GeminiConfig.getApiKey());
        } else {
            result = new ItemCounter.CountResult(-1, "No AI key configured");
        }
        final ItemCounter.CountResult finalResult = result;
        javax.swing.SwingUtilities.invokeLater(
                () -> applyCountResult(finalResult));
    }).start();
}

// Apply result and update quantity field
private void applyCountResult(ItemCounter.CountResult result) {
    if (result.isSuccess()) {
        jTextField2.setText(String.valueOf(result.count));
        statusLabel.setText("Gemini counted " + result.count + " items");
        statusLabel.setForeground(new java.awt.Color(60, 180, 60));
    } else {
        // Fall back to OpenCV if Gemini fails
        statusLabel.setText("Gemini unavailable — using OpenCV");
        statusLabel.setForeground(new java.awt.Color(180, 100, 0));
    }
}

    // -------------------------------------------------------
    //  Scale and display image in the preview label
    // -------------------------------------------------------
    private void showPreview(java.awt.image.BufferedImage img) {
        showPreview(new javax.swing.ImageIcon(img).getImage());
    }

    private void showPreview(java.awt.Image img) {
        int w = previewLabel.getWidth()  > 0 ? previewLabel.getWidth()  : 300;
        int h = previewLabel.getHeight() > 0 ? previewLabel.getHeight() : 160;
        java.awt.Image scaled = img.getScaledInstance(w, h,
                java.awt.Image.SCALE_SMOOTH);
        previewLabel.setIcon(new javax.swing.ImageIcon(scaled));
        previewLabel.setText("");
    }

    // -------------------------------------------------------
    //  Everything below is UNCHANGED from your original file
    // -------------------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new custom_ui.RoundedPanel();
        roundedPanel2 = new custom_ui.RoundedPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        typeSelector = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        departmentSelector = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        roundedPanel1.setBackground(new java.awt.Color(45, 59, 111));
        roundedPanel1.setForeground(new java.awt.Color(45, 59, 111));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(800, 450));

        roundedPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundedPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setText("Add Item");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setToolTipText("");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Name");

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Quantity");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Type");

        typeSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Equipment", "Consumables", " " }));
        typeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeSelectorActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Department");

        departmentSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Electrical Power and Machines", "Electronics and Control", "Networking and Telecom", "Multimeter Allocation" }));
        departmentSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departmentSelectorActionPerformed(evt);
            }
        });

        jLabel5.setText("Description");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel2Layout.createSequentialGroup()
                                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(23, 23, 23)
                                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                                        .addComponent(typeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4))
                                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(departmentSelector, 0, 1, Short.MAX_VALUE)))
                            .addComponent(jLabel5))
                        .addContainerGap())
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(typeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(departmentSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed
    private void addItem(String ItemName, String Type, int Quantity, String Department, String Description) {
        // completely unchanged — MySQL code stays as is
        Connection con = null;
        PreparedStatement ps = null;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DatabaseConfig.getConnection();
                    //DriverManager.getConnection("jdbc:mysql://localhost/sys", "root", "Vitamin101##");
            con.setAutoCommit(false);
           
            String checkSql = "SELECT itemId, quantity FROM inventory " +
                  "WHERE name = ? AND userId = ?";
        PreparedStatement checkPs = con.prepareStatement(checkSql);
        checkPs.setString(1, ItemName);
        checkPs.setInt(2, currentUserId);
        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {
            int existingId  = rs.getInt("itemId");
            int existingQty = rs.getInt("quantity");
            int newQty      = existingQty + Quantity;
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "\"" + ItemName + "\" already exists with " +
                existingQty + " in stock.\n\n" +
                "Add " + Quantity + " more? New total will be " + newQty + ".",
                "Item Already Exists",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                con.close();
                return; // user cancelled — do nothing
            }
            // FIX: just SET quantity = ? directly, newQty is already calculated
            String updateSql = "UPDATE inventory SET quantity = ? WHERE itemId = ?";
            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setInt(1, newQty);
            updatePs.setInt(2, existingId);
            updatePs.executeUpdate();

            con.commit(); // FIX: was missing — needed because autoCommit is false

            JOptionPane.showMessageDialog(this,
                "\"" + ItemName + "\" ->.\n" +
                "Stock Updated " + existingQty +
                " to " + newQty + ".");
            // After successful insert or update in addItem():
            ItemRecommender.record(currentUserId, ItemName);
        } else {
            String insertSql = "INSERT INTO inventory (name, type, quantity, department, description, userId) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPs = con.prepareStatement(insertSql);
            insertPs.setString(1, ItemName);
            insertPs.setString(2, Type);
            insertPs.setInt(3, Quantity);
            insertPs.setString(4, Department);
            insertPs.setString(5, Description);
            insertPs.setInt(6, currentUserId);
            insertPs.executeUpdate();

            con.commit(); 

            JOptionPane.showMessageDialog(this,
                "\"" + ItemName + "\" added successfully.");
            if (parentDashboard != null){
                parentDashboard.refreshInventoryFromAdd(); // NEW
            }
        }
        } catch (SQLException ex) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex1) { }
            }
            JOptionPane.showMessageDialog(this,
                "Database error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } //catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
        //} 
        finally {
            try {
                if (ps  != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException closeEx) {
                System.err.println("Error closing resources: " + closeEx.getMessage());
            }
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String name     = jTextField1.getText().toUpperCase();
            String type     = typeSelector.getSelectedItem().toString();
            String department = departmentSelector.getSelectedItem().toString();
            String description = jTextField3.getText();
            int    quantity = Integer.parseInt(jTextField2.getText());
            addItem(name, type, quantity, department, description);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Quantity must be a whole number.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void typeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeSelectorActionPerformed

    private void departmentSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departmentSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_departmentSelectorActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddItemScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddItemScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddItemScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddItemScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //int userId = -1;
                //new AddItemScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> departmentSelector;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private custom_ui.RoundedPanel roundedPanel1;
    private custom_ui.RoundedPanel roundedPanel2;
    private javax.swing.JComboBox<String> typeSelector;
    // End of variables declaration//GEN-END:variables
}
