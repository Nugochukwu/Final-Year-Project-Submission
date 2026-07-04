/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventorymanagementsystemv_01;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import custom_ui.CustomTitleBar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static javax.swing.SwingConstants.CENTER;


/**
 *
 * @author Ugochukwu Nwodo
 */
public class staff_dashboard extends javax.swing.JFrame {
    private int currentUserId;
    private CustomTitleBar titleBar;
    private AddItemScreen addItemScreen;
    private java.awt.CardLayout cardLayout;
    private javax.swing.JPanel contentArea; 
    private javax.swing.JTextField searchField;
    private javax.swing.JPanel inventoryCard;
    private String currentUserRole = "staff";
    private String currentUserName = "";

    
    /**
     * Creates new form staff_dashboard
     */
    public staff_dashboard(int userId) {
        this.currentUserId = userId;
        DashboardSettings.load(currentUserId);
        loadUserRole();
        GeminiConfig.load(currentUserId);
        ClaudeConfig.load(currentUserId);
        GitHubConfig.load(currentUserId);
        setUndecorated(true);
        initComponents();
        //overide generated content 
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(roundedPanel1, java.awt.BorderLayout.CENTER);
        // --- CardLayout setup ---
        cardLayout  = new java.awt.CardLayout();
        contentArea = new javax.swing.JPanel(cardLayout);
        contentArea.setOpaque(false);
        // New Inventory Card
        javax.swing.JPanel inventoryCard = buildInventoryCard();
        // Dashboard card — stat cards
        javax.swing.JPanel dashboardCard = buildDashboardCard();

        javax.swing.JPanel reportsCard = buildReportsCard();
        javax.swing.JPanel settingsCard = buildSettingsCard();

        contentArea.add(inventoryCard,  "INVENTORY");
        contentArea.add(dashboardCard,  "DASHBOARD");
        contentArea.add(reportsCard,    "REPORTS");
        contentArea.add(settingsCard,   "SETTINGS");
        contentArea.add(buildLendingCard(), "LENDING");
        
        roundedPanel3.setLayout(new java.awt.BorderLayout());
        roundedPanel3.add(contentArea, java.awt.BorderLayout.CENTER);

        
        cardLayout.show(contentArea, "INVENTORY");
    loadTable();

        
        javax.swing.JPanel wrapper = new javax.swing.JPanel(new java.awt.BorderLayout());
        wrapper.setOpaque(true);
        wrapper.setBackground(new java.awt.Color(0, 0, 0, 0)); 

        titleBar = new CustomTitleBar(this);
        titleBar.setCloseAction(CustomTitleBar.CloseAction.EXIT);
        titleBar.setShowMinimize(true);
        titleBar.setShowClose(true);
        titleBar.setShowMaximize(true);
        titleBar.setCornerRadius(20);
        titleBar.setBarColor(new java.awt.Color(103, 153, 255));
        titleBar.setCloseNormalColor(new java.awt.Color(103, 153, 255));
        titleBar.setMinimizeNormalColor(new java.awt.Color(103, 153, 255));
        titleBar.setCloseHoverColor(new java.awt.Color(255, 0, 0));
        titleBar.setTitleText("Laboratory Inventory");

        wrapper.add(titleBar, java.awt.BorderLayout.NORTH);
        wrapper.add(roundedPanel1, java.awt.BorderLayout.CENTER);

        
        getRootPane().setOpaque(false);
        getRootPane().setBackground(new java.awt.Color(0, 0, 0, 0));

        setContentPane(wrapper);
        setMinimumSize(new java.awt.Dimension(1000, 650));
        setSize(1200, 850);
        setLocationRelativeTo(null);
        loadCredentials();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new custom_ui.RoundedPanel();
        roundedPanel2 = new custom_ui.RoundedPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roundedPanel11 = new custom_ui.RoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        roundedPanel3 = new custom_ui.RoundedPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roundedPanel1.setBackground(new java.awt.Color(45, 59, 111));
        roundedPanel1.setForeground(new java.awt.Color(45, 59, 111));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(1200, 750));

        roundedPanel2.setBackground(new java.awt.Color(45, 59, 111));
        roundedPanel2.setForeground(new java.awt.Color(45, 59, 111));

        jButton1.setBackground(new java.awt.Color(45, 59, 111));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Dashboard");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(45, 59, 111));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Inventory");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(45, 59, 111));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Reports");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(45, 59, 111));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Settings");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Main");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Manage");

        roundedPanel11.setBackground(new java.awt.Color(45, 59, 111));
        roundedPanel11.setForeground(new java.awt.Color(120, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("User Credentials");

        javax.swing.GroupLayout roundedPanel11Layout = new javax.swing.GroupLayout(roundedPanel11);
        roundedPanel11.setLayout(roundedPanel11Layout);
        roundedPanel11Layout.setHorizontalGroup(
            roundedPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel11Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        roundedPanel11Layout.setVerticalGroup(
            roundedPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel11Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jButton5.setBackground(new java.awt.Color(45, 59, 111));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Lending");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2))
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel3))
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roundedPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
                .addComponent(roundedPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );

        roundedPanel3.setBackground(new java.awt.Color(45, 59, 111));
        roundedPanel3.setForeground(new java.awt.Color(45, 59, 111));

        javax.swing.GroupLayout roundedPanel3Layout = new javax.swing.GroupLayout(roundedPanel3);
        roundedPanel3.setLayout(roundedPanel3Layout);
        roundedPanel3Layout.setHorizontalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 922, Short.MAX_VALUE)
        );
        roundedPanel3Layout.setVerticalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundedPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundedPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // In staff_dashboard constructor
    

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Dashboard button
        //cardLayout.show(contentArea, "DASHBOARD");
        refreshCard("DASHBOARD", this::buildDashboardCard);
        setActiveButton(jButton1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Inventory button
        cardLayout.show(contentArea, "INVENTORY");
        setActiveButton(jButton2);
        loadTable(); // refresh data when switching to inventory
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void loadUserRole() {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT name, role FROM users WHERE userId = ?")) {
            ps.setInt(1, currentUserId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentUserName = rs.getString("name");
                currentUserRole = rs.getString("role");
                if (currentUserRole == null) currentUserRole = "staff";
            }
        } catch (SQLException e) {
            System.err.println("Role load failed: " + e.getMessage());
        }
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(currentUserRole);
    }
    
    public void loadCredentials(){
        Connection con = null;
            PreparedStatement ps = null;
            java.sql.ResultSet rs = null;
            try{
                
                //Class.forName("com.mysql.cj.jdbc.Driver");
                con = DatabaseConfig.getConnection();//DriverManager.getConnection("jdbc:mysql://localhost/sys", "root", "Vitamin101##");
                System.out.println("Connection established. Default autocommit status: " + con.getAutoCommit());
                con.setAutoCommit(false);
                System.out.println("Autocommit status AFTER setAutoCommit(false): " + con.getAutoCommit());
                String confirmUserCredentials = "SELECT name FROM users WHERE userId = ?";
                ps = con.prepareStatement(confirmUserCredentials);
                ps.setInt(1, currentUserId);
                rs = ps.executeQuery();

                if (rs.next())
                {
                    jLabel1.setText(rs.getString("name"));
                }else
                {
                    jLabel1.setText("User");
                }


            }catch(SQLException ex){
                if (con != null){
                    try{
                        System.out.println("Caught SQLException. Attempting rollback. Current autocommit: "+ con.getAutoCommit());
                        con.rollback();
                    }catch(SQLException ex1){
                        //    Logger.getLogger(Registration.class.getName()).log(Level.SEVERE,null,ex1);
                    }
                }
                JOptionPane.showMessageDialog(this,"Database error: "+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            //catch (ClassNotFoundException ex) {
               // Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            //} 
            finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {

                }
            }
    }
    public void loadTable(){
            Connection con = null;
            PreparedStatement ps = null;
            java.sql.ResultSet rs = null;
            try{
                
                //Class.forName("com.mysql.cj.jdbc.Driver");
                con = DatabaseConfig.getConnection();//DriverManager.getConnection("jdbc:mysql://localhost/sys", "root", "Vitamin101##");
                System.out.println("Connection established. Default autocommit status: " + con.getAutoCommit());
                con.setAutoCommit(false);
                System.out.println("Autocommit status AFTER setAutoCommit(false): " + con.getAutoCommit());
                String confirmUserCredentials = "SELECT name, type, quantity, department, description FROM inventory WHERE userId = ?";
                ps = con.prepareStatement(confirmUserCredentials);
                ps.setInt(1, currentUserId);
                rs = ps.executeQuery();

                if (rs.next())
                {
                    
                    refreshTable(rs);
                }else
                {
                    
                }


            }catch(SQLException ex){
                if (con != null){
                    try{
                        System.out.println("Caught SQLException. Attempting rollback. Current autocommit: "+ con.getAutoCommit());
                        con.rollback();
                    }catch(SQLException ex1){
                        //    Logger.getLogger(Registration.class.getName()).log(Level.SEVERE,null,ex1);
                    }
                }
                JOptionPane.showMessageDialog(this,"Database error: "+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            //catch (ClassNotFoundException ex) {
               // Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            //} 
            finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {

                }
            }
    }
    
    public void refreshTable(ResultSet rs) throws SQLException{
            DefaultTableModel model = new DefaultTableModel();
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++){
                model.addColumn(rsmd.getColumnName(i));
            }
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i); // Get data for each column
                }
                model.addRow(rowData);
            }
            //jTable1.setModel(model);
    }
    public void refreshInventoryFromAdd() {
    javax.swing.SwingUtilities.invokeLater(() -> {
        loadTable();
        // Trigger doSearch by firing the search field's key listener
        // doSearch is captured inside buildInventoryCard so we
        // use the stored searchField reference instead
        if (searchField != null) {
            // Fire a fake key event to trigger live reload
            for (java.awt.event.KeyListener kl :
                    searchField.getKeyListeners()) {
                kl.keyReleased(new java.awt.event.KeyEvent(
                    searchField,
                    java.awt.event.KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    java.awt.event.KeyEvent.VK_UNDEFINED, ' '));
            }
            searchField.requestFocusInWindow();
        }
    });
}
    // Rebuilds the named card and swaps it into the same CardLayout slot,
// then shows it — ensures every visit re-runs the underlying queries.
private void refreshCard(String name,
        java.util.function.Supplier<javax.swing.JPanel> builder) {
    int targetIndex = -1;
    java.awt.Component[] comps = contentArea.getComponents();
    // Find index by matching constraints isn't directly exposed by CardLayout,
    // so we track insertion order instead — cards were added in this order:
    // INVENTORY(0), DASHBOARD(1), REPORTS(2), SETTINGS(3), LENDING(4)
    java.util.Map<String, Integer> order = java.util.Map.of(
        "INVENTORY", 0, "DASHBOARD", 1, "REPORTS", 2,
        "SETTINGS", 3, "LENDING", 4
    );
    Integer idx = order.get(name);
    if (idx != null && idx < comps.length) {
        contentArea.remove(idx);
        contentArea.add(builder.get(), name, idx);
    } else {
        contentArea.add(builder.get(), name);
    }
    contentArea.revalidate();
    contentArea.repaint();
    cardLayout.show(contentArea, name);
}
    private void setActiveButton(javax.swing.JButton active) {
    javax.swing.JButton[] navBtns = {jButton1, jButton2, jButton4,jButton5, jButton7};
    for (javax.swing.JButton btn : navBtns) {
        btn.setBackground(new java.awt.Color(45, 50, 111));
        btn.setForeground(java.awt.Color.WHITE);
    }
    // Highlight the active one
    active.setBackground(new java.awt.Color(103, 143, 255));
    active.setForeground(java.awt.Color.WHITE);
}
    private static void tryLaunchDroidCam() {
    new Thread(() -> {
        try {
            // Common DroidCam install paths on Windows
            String[] paths = {
                "C:\\Program Files (x86)\\DroidCam\\DroidCam.exe",
                "C:\\Program Files\\DroidCam\\DroidCam.exe",
                "C:\\Program Files\\DroidCam\\Client\\bin\\64bit\\droidCam.exe"
                    
            };
            for (String path : paths) {
                if (new java.io.File(path).exists()) {
                    Runtime.getRuntime().exec(path);
                    System.out.println("DroidCam launched: " + path);
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Could not launch DroidCam: " + e.getMessage());
        }
    }).start();
}
    private javax.swing.JLabel makeFormLabel(String text,
        java.awt.Font font, java.awt.Color color) {
        javax.swing.JLabel lbl = new javax.swing.JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        return lbl;
    }
    private custom_ui.RoundedPanel buildDashboardVisibilitySection() {
    custom_ui.RoundedPanel panel = new custom_ui.RoundedPanel(12);
    panel.setBackground(java.awt.Color.WHITE);
    panel.setLayout(new java.awt.GridBagLayout());
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));
    panel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 9999));

    java.awt.GridBagConstraints gc = defaultGc();

    addSettingsSectionTitle(panel, gc, 0,
        "Dashboard Stat Cards",
        "Choose which stat cards appear at the top of the Dashboard.");

    javax.swing.JCheckBox cbTotal = makeSettingsCheckbox(
            "Total Items",  DashboardSettings.showTotalItems);
    javax.swing.JCheckBox cbLow   = makeSettingsCheckbox(
            "Low Stock",    DashboardSettings.showLowStock);
    javax.swing.JCheckBox cbTypes = makeSettingsCheckbox(
            "Item Types",   DashboardSettings.showItemTypes);
    javax.swing.JCheckBox cbToday = makeSettingsCheckbox(
            "Added Today",  DashboardSettings.showAddedToday);

    gc.gridwidth = 1; gc.weightx = 0.5;
    gc.gridx = 0; gc.gridy = 2; panel.add(cbTotal, gc);
    gc.gridx = 1;               panel.add(cbLow,   gc);
    gc.gridx = 0; gc.gridy = 3; panel.add(cbTypes, gc);
    gc.gridx = 1;               panel.add(cbToday, gc);

    // Table visibility
    addSettingsSectionTitle(panel, gc, 4,
        "Dashboard Tables",
        "Choose which tables appear in the Dashboard.");

    // Reset for full-width
    gc.gridwidth = 1; gc.weightx = 0.5;

    javax.swing.JCheckBox cbLowTbl  = makeSettingsCheckbox(
            "Low Stock Table",  DashboardSettings.showLowStockTbl);
    javax.swing.JCheckBox cbRecent  = makeSettingsCheckbox(
            "Recently Added",   DashboardSettings.showRecentTbl);
    javax.swing.JCheckBox cbInvTbl  = makeSettingsCheckbox(
            "Inventory Table",  DashboardSettings.showInventoryTbl);
    javax.swing.JCheckBox cbLendTbl = makeSettingsCheckbox(
            "Lending History",  DashboardSettings.showLendingTbl);
    javax.swing.JCheckBox cbrmvTbl = makeSettingsCheckbox(
            "Removed Item History",  DashboardSettings.showRemovedTbl);

    gc.gridx = 0; gc.gridy = 6; panel.add(cbLowTbl,  gc);
    gc.gridx = 1;               panel.add(cbRecent,  gc);
    gc.gridx = 0; gc.gridy = 7; panel.add(cbInvTbl,  gc);
    gc.gridx = 1;               panel.add(cbLendTbl, gc);
    gc.gridx = 0; gc.gridy = 8; panel.add(cbrmvTbl, gc);

    // Apply button
    javax.swing.JButton applyBtn = makeSettingsBtn("Save & Apply Dashboard");
    applyBtn.setPreferredSize(new java.awt.Dimension(200, 36));
    gc.gridx = 0; gc.gridy = 9;
    gc.gridwidth = 2; gc.weightx = 1.0;
    gc.insets = new java.awt.Insets(12, 0, 0, 0);
    panel.add(applyBtn, gc);

    applyBtn.addActionListener(e -> {
        DashboardSettings.showTotalItems   = cbTotal.isSelected();
        DashboardSettings.showLowStock     = cbLow.isSelected();
        DashboardSettings.showItemTypes    = cbTypes.isSelected();
        DashboardSettings.showAddedToday   = cbToday.isSelected();
        DashboardSettings.showLowStockTbl  = cbLowTbl.isSelected();
        DashboardSettings.showRecentTbl    = cbRecent.isSelected();
        DashboardSettings.showInventoryTbl = cbInvTbl.isSelected();
        DashboardSettings.showLendingTbl   = cbLendTbl.isSelected();
        DashboardSettings.showRemovedTbl   = cbrmvTbl.isSelected();
        DashboardSettings.save(currentUserId);

        // Rebuild dashboard in place
        contentArea.remove(1);
        contentArea.add(buildDashboardCard(), "DASHBOARD", 1);
        contentArea.revalidate();
        contentArea.repaint();
        showSaved("Dashboard settings saved.");
    });

    return (panel);
}

private javax.swing.JCheckBox makeSettingsCheckbox(
        String label, boolean selected) {
    javax.swing.JCheckBox cb = new javax.swing.JCheckBox(label, selected);
    cb.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    cb.setOpaque(false);
    return cb;
}

private void addSettingsSectionTitle(javax.swing.JPanel panel,
        java.awt.GridBagConstraints gc, int startRow,
        String title, String subtitle) {
    gc.gridx = 0; gc.gridy = startRow;
    gc.gridwidth = 2; gc.weightx = 1.0;
    gc.insets = new java.awt.Insets(0, 0, 2, 0);

    javax.swing.JLabel titleLbl = new javax.swing.JLabel(title);
    titleLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    titleLbl.setForeground(new java.awt.Color(33, 55, 98));
    panel.add(titleLbl, gc);

    if (!subtitle.isBlank()) {
        javax.swing.JLabel subLbl = new javax.swing.JLabel(subtitle);
        subLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        subLbl.setForeground(new java.awt.Color(130, 130, 150));
        gc.gridy = startRow + 1;
        gc.insets = new java.awt.Insets(0, 0, 8, 0);
        panel.add(subLbl, gc);
    }
}
private javax.swing.JPanel buildSettingsCard() {
    javax.swing.JPanel card = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 0));
    card.setOpaque(false);
    card.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // Scrollable container for all settings sections
    javax.swing.JPanel scrollContent = new javax.swing.JPanel();
    scrollContent.setOpaque(false);
    scrollContent.setLayout(new javax.swing.BoxLayout(
            scrollContent, javax.swing.BoxLayout.Y_AXIS));

    // ---- AI Settings ----
    scrollContent.add(buildSettingsSection("AI Counting Keys",
        "Enter API keys for AI image counting services.",
        panel -> {
            java.awt.GridBagConstraints gc = defaultGc();

            addSettingsSectionTitle(panel, gc, 0,
                "AI Counting Keys",
                "Enter API keys for AI image counting.");

            javax.swing.JTextField geminiField = makeSettingsField(
                    GeminiConfig.getApiKey());
            javax.swing.JTextField claudeField  = makeSettingsField(
                    ClaudeConfig.getApiKey());

            gc.gridy = 2; gc.gridwidth = 2;
            panel.add(makeFormLabel("Gemini API Key:", labelFontSettings(),
                    mutedColor()), gc);
            gc.gridy = 3;
            panel.add(geminiField, gc);
            gc.gridy = 4;
            panel.add(makeFormLabel("Claude API Key:", labelFontSettings(),
                    mutedColor()), gc);
            gc.gridy = 5;
            panel.add(claudeField, gc);

            javax.swing.JButton saveAI = makeSettingsBtn("Save AI Keys");
            gc.gridy = 6;
            gc.insets = new java.awt.Insets(10, 0, 0, 0);
            panel.add(saveAI, gc);

            saveAI.addActionListener(e -> {
                GeminiConfig.setApiKey(geminiField.getText().trim());
                ClaudeConfig.setApiKey(claudeField.getText().trim());
                GeminiConfig.save(currentUserId);
                ClaudeConfig.save(currentUserId);
                showSaved("AI keys saved.");
            });
        }));


    
    scrollContent.add(javax.swing.Box.createVerticalStrut(10));
    // ---- Dashboard Visibility (existing checkboxes) ----
   // scrollContent.add(buildDashboardVisibilitySection());
    
   scrollContent.add(javax.swing.Box.createVerticalStrut(10));
    
scrollContent.add(buildSettingsSection(
    "Microsoft OneDrive — Sign-Out & Sign-In Sync", "",
    panel -> {
        java.awt.GridBagConstraints gc = defaultGc();

        // ---- Section title ----
        addSettingsSectionTitle(panel, gc, 0,
            "OneDrive Connection",
            "Fill in your Azure app details and OneDrive " +
            "file information, then click Authenticate.");

        // ---- Input fields ----
        javax.swing.JTextField clientIdF =
            makeSettingsField(MicrosoftGraphConfig.clientId);
        javax.swing.JTextField secretF =
            makeSettingsField(MicrosoftGraphConfig.clientSecret);
        javax.swing.JTextField tenantF =
            makeSettingsField(MicrosoftGraphConfig.tenantId);
        javax.swing.JTextField ownerEmailF =
            makeSettingsField(MicrosoftGraphConfig.fileOwnerEmail);
        javax.swing.JTextField signOutIdF =
            makeSettingsField(MicrosoftGraphConfig.signOutFileId);
        javax.swing.JTextField signInIdF =
            makeSettingsField(MicrosoftGraphConfig.signInFileId);
        javax.swing.JTextField signOutSheetF =
            makeSettingsField(MicrosoftGraphConfig.signOutSheet);
        javax.swing.JTextField signInSheetF =
            makeSettingsField(MicrosoftGraphConfig.signInSheet);

        // Make secret field masked
        javax.swing.JPasswordField secretPF =
                new javax.swing.JPasswordField(
                        MicrosoftGraphConfig.clientSecret);
        secretPF.setFont(new java.awt.Font(
                "Segoe UI", java.awt.Font.PLAIN, 12));
        secretPF.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(
                    new java.awt.Color(220, 224, 245), 1),
            javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        Object[][] fieldDefs = {
            {"Azure Client ID:",     clientIdF},
            {"Client Secret (Leave blank for public client apps)",       secretPF},
            {"Tenant ID:",           tenantF},
            {"Your OneDrive Email:", ownerEmailF},
            {"Sign-Out File ID:",    signOutIdF},
            {"Sign-In File ID:",     signInIdF},
            {"Sign-Out Sheet Name:", signOutSheetF},
            {"Sign-In Sheet Name:",  signInSheetF}
        };

        for (int i = 0; i < fieldDefs.length; i++) {
            gc.gridy = i * 2 + 2;
            gc.gridwidth = 2; gc.weightx = 1.0;
            gc.insets = new java.awt.Insets(4, 0, 1, 0);
            panel.add(makeFormLabel(
                    fieldDefs[i][0].toString(),
                    labelFontSettings(), mutedColor()), gc);
            gc.gridy = i * 2 + 3;
            gc.insets = new java.awt.Insets(0, 0, 2, 0);
            panel.add((java.awt.Component) fieldDefs[i][1], gc);
        }

        // Helper label explaining where to find file ID
        int hintRow = fieldDefs.length * 2 + 2;
        gc.gridy = hintRow;
        gc.insets = new java.awt.Insets(4, 0, 10, 0);
        javax.swing.JLabel hint = new javax.swing.JLabel(
            "<html><i>File ID: from your SharePoint URL, " +
            "copy the GUID in sourcedoc={...}</i></html>");
        hint.setFont(new java.awt.Font(
                "Segoe UI", java.awt.Font.PLAIN, 10));
        hint.setForeground(new java.awt.Color(150, 150, 170));
        panel.add(hint, gc);

        // ---- Auth status indicator ----
        javax.swing.JLabel statusLbl = new javax.swing.JLabel(
            MicrosoftGraphConfig.isAuthenticated()
                ? "Status: Authenticated"
                : "Status: Not authenticated",
            javax.swing.SwingConstants.CENTER);
        statusLbl.setFont(new java.awt.Font(
                "Segoe UI", java.awt.Font.BOLD, 11));
        statusLbl.setForeground(
            MicrosoftGraphConfig.isAuthenticated()
                ? new java.awt.Color(40, 160, 70)
                : new java.awt.Color(180, 60, 60));
        statusLbl.setBorder(javax.swing.BorderFactory
                .createEmptyBorder(6, 0, 6, 0));
        gc.gridy = hintRow + 1;
        gc.insets = new java.awt.Insets(0, 0, 6, 0);
        panel.add(statusLbl, gc);

        // ---- Buttons ----
        javax.swing.JPanel msButtons = new javax.swing.JPanel(
            new java.awt.FlowLayout(
                    java.awt.FlowLayout.LEFT, 6, 0));
        msButtons.setOpaque(false);

        javax.swing.JButton saveBtn     = makeSettingsBtn("Save");
        javax.swing.JButton authBtn     = makeSettingsBtn("Authenticate");
        javax.swing.JButton syncOutBtn  = makeSettingsBtn("Sync Sign-Out");
        javax.swing.JButton syncInBtn   = makeSettingsBtn("Sync Sign-In");
        javax.swing.JButton syncAllBtn  = makeSettingsBtn("Sync Both");
        javax.swing.JButton clearAuthBtn = makeSettingsBtn("Clear Auth");

        authBtn.setBackground(new java.awt.Color(0, 120, 212));
        syncOutBtn.setBackground(new java.awt.Color(40, 140, 70));
        syncInBtn.setBackground(new java.awt.Color(40, 140, 70));
        syncAllBtn.setBackground(new java.awt.Color(20, 100, 160));
        clearAuthBtn.setBackground(new java.awt.Color(160, 60, 60));

        msButtons.add(saveBtn);
        msButtons.add(authBtn);
        msButtons.add(syncOutBtn);
        msButtons.add(syncInBtn);
        msButtons.add(syncAllBtn);
        msButtons.add(clearAuthBtn);

        gc.gridy = hintRow + 2;
        gc.insets = new java.awt.Insets(4, 0, 0, 0);
        panel.add(msButtons, gc);

        // ---- Save action ----
        saveBtn.addActionListener(e -> {
            MicrosoftGraphConfig.clientId =
                    clientIdF.getText().trim();
            MicrosoftGraphConfig.clientSecret =
                    new String(secretPF.getPassword()).trim();
            MicrosoftGraphConfig.tenantId =
                    tenantF.getText().trim();
            MicrosoftGraphConfig.fileOwnerEmail =
                    ownerEmailF.getText().trim();
            MicrosoftGraphConfig.signOutFileId =
                    signOutIdF.getText().trim();
            MicrosoftGraphConfig.signInFileId =
                    signInIdF.getText().trim();
            MicrosoftGraphConfig.signOutSheet =
                    signOutSheetF.getText().trim();
            MicrosoftGraphConfig.signInSheet =
                    signInSheetF.getText().trim();
            MicrosoftGraphConfig.save(currentUserId);
            showSaved("OneDrive settings saved.");
        });

        // ---- Authenticate action ----
        authBtn.addActionListener(e -> {
            // Save fields first
            MicrosoftGraphConfig.clientId =
                    clientIdF.getText().trim();
            MicrosoftGraphConfig.clientSecret =
                    new String(secretPF.getPassword()).trim();
            MicrosoftGraphConfig.tenantId =
                    tenantF.getText().trim();
            MicrosoftGraphConfig.fileOwnerEmail =
                    ownerEmailF.getText().trim();
            MicrosoftGraphConfig.save(currentUserId);

            if (MicrosoftGraphConfig.clientId.isBlank() ||
                    MicrosoftGraphConfig.tenantId.isBlank()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Fill in Client ID and Tenant ID first.",
                    "Missing Fields",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            authBtn.setEnabled(false);
            authBtn.setText("Starting...");

            new Thread(() -> {
                try {
                    // Try refresh token first
                    if (!MicrosoftGraphConfig.refreshToken.isBlank()) {
                        boolean refreshed =
                            GraphAuthManager.refreshAccessToken(
                                    currentUserId);
                        if (refreshed) {
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                authBtn.setEnabled(true);
                                authBtn.setText("Authenticate");
                                statusLbl.setText(
                                        "Status: Authenticated");
                                statusLbl.setForeground(
                                        new java.awt.Color(40, 160, 70));
                                showSaved("Re-authenticated using " +
                                          "saved token.");
                            });
                            return;
                        }
                    }

                    // Fresh device code flow
                    java.util.Map<String, String> flow =
                        GraphAuthManager.startDeviceCodeFlow();
                    String userCode  = flow.get("user_code");
                    String verifyUrl = flow.get("verification_uri");
                    String devCode   = flow.get("device_code");

                    if (userCode == null) {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            authBtn.setEnabled(true);
                            authBtn.setText("Authenticate");
                            javax.swing.JOptionPane.showMessageDialog(
                                this,
                                "Failed to start auth flow. " +
                                "Check Client ID and Tenant ID.",
                                "Auth Error",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        });
                        return;
                    }

                    // Show the code to the user
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        authBtn.setText("Waiting for login...");

                        // Build a copyable dialog
                        javax.swing.JPanel msgPanel =
                                new javax.swing.JPanel(
                                new java.awt.BorderLayout(0, 8));
                        msgPanel.setPreferredSize(
                                new java.awt.Dimension(420, 130));

                        javax.swing.JLabel instructions =
                                new javax.swing.JLabel(
                                "<html>1. Open this URL in your browser:" +
                                "<br><b>" + verifyUrl + "</b><br><br>" +
                                "2. Enter this code when prompted:</html>");
                        instructions.setFont(new java.awt.Font(
                                "Segoe UI", java.awt.Font.PLAIN, 12));

                        javax.swing.JTextField codeField =
                                new javax.swing.JTextField(userCode);
                        codeField.setFont(new java.awt.Font(
                                "Segoe UI", java.awt.Font.BOLD, 18));
                        codeField.setHorizontalAlignment(
                                javax.swing.SwingConstants.CENTER);
                        codeField.setEditable(false);
                        codeField.setBackground(
                                new java.awt.Color(240, 248, 255));

                        msgPanel.add(instructions,
                                java.awt.BorderLayout.NORTH);
                        msgPanel.add(codeField,
                                java.awt.BorderLayout.CENTER);

                        javax.swing.JOptionPane.showMessageDialog(
                            this, msgPanel,
                            "Microsoft Login Required",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    });

                    // Poll for token
                    boolean ok = GraphAuthManager.pollForToken(
                            devCode, currentUserId);

                    javax.swing.SwingUtilities.invokeLater(() -> {
                        authBtn.setEnabled(true);
                        authBtn.setText("Authenticate");
                        if (ok) {
                            statusLbl.setText("Status: Authenticated");
                            statusLbl.setForeground(
                                    new java.awt.Color(40, 160, 70));
                            showSaved("Authentication successful. " +
                                "Token saved — you won't need to " +
                                "log in again.");
                        } else {
                            statusLbl.setText(
                                    "Status: Authentication failed");
                            statusLbl.setForeground(
                                    new java.awt.Color(180, 60, 60));
                            javax.swing.JOptionPane.showMessageDialog(
                                this,
                                "Authentication timed out or failed. " +
                                "Please try again.",
                                "Auth Failed",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                    });

                } catch (Exception ex) {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        authBtn.setEnabled(true);
                        authBtn.setText("Authenticate");
                        javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Auth error: " + ex.getMessage(),
                            "Error",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();
        });

        // ---- Sync actions ----
        Runnable doSyncOut = () -> {
            syncOutBtn.setEnabled(false);
            syncOutBtn.setText("Syncing...");
            new Thread(() -> {
                boolean ok = OneDriveManager.syncSignOutSheet(
                        currentUserId);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    syncOutBtn.setEnabled(true);
                    syncOutBtn.setText("Sync Sign-Out");
                    showSaved(ok
                        ? "Sign-Out sheet synced successfully."
                        : "Sign-Out sync failed. Check console.");
                });
            }).start();
        };

        Runnable doSyncIn = () -> {
            syncInBtn.setEnabled(false);
            syncInBtn.setText("Syncing...");
            new Thread(() -> {
                boolean ok = OneDriveManager.syncSignInSheet(
                        currentUserId);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    syncInBtn.setEnabled(true);
                    syncInBtn.setText("Sync Sign-In");
                    showSaved(ok
                        ? "Sign-In sheet synced successfully."
                        : "Sign-In sync failed. Check console.");
                });
            }).start();
        };

        syncOutBtn.addActionListener(e -> doSyncOut.run());
        syncInBtn.addActionListener(e  -> doSyncIn.run());

        syncAllBtn.addActionListener(e -> {
            syncAllBtn.setEnabled(false);
            syncAllBtn.setText("Syncing...");
            new Thread(() -> {
                boolean out = OneDriveManager.syncSignOutSheet(
                        currentUserId);
                boolean in  = OneDriveManager.syncSignInSheet(
                        currentUserId);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    syncAllBtn.setEnabled(true);
                    syncAllBtn.setText("Sync Both");
                    if (out && in) {
                        showSaved("Both sheets synced.");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Partial sync failure:\n" +
                            "Sign-Out: " + (out ? "OK" : "FAILED") +
                            "\nSign-In: " + (in  ? "OK" : "FAILED"),
                            "Sync Result",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                    }
                });
            }).start();
        });

        // ---- Clear auth ----
        clearAuthBtn.addActionListener(e -> {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Clear saved authentication token?\n" +
                "You will need to log in again next time.",
                "Clear Authentication",
                javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                MicrosoftGraphConfig.accessToken  = "";
                MicrosoftGraphConfig.refreshToken = "";
                MicrosoftGraphConfig.saveRefreshToken(
                        currentUserId, "");
                statusLbl.setText("Status: Not authenticated");
                statusLbl.setForeground(
                        new java.awt.Color(180, 60, 60));
            }
        });
    }));
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(
            scrollContent,
            javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(null);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setOpaque(false);
    
    
card.add(scrollPane, java.awt.BorderLayout.CENTER);
return wrapInScrollCard(card);
}

// Helper to build a white rounded section with a consumer to populate it
private custom_ui.RoundedPanel buildSettingsSection(String title,
        String subtitle,
        java.util.function.Consumer<custom_ui.RoundedPanel> builder) {
    custom_ui.RoundedPanel panel = new custom_ui.RoundedPanel(12);
    panel.setBackground(java.awt.Color.WHITE);
    panel.setLayout(new java.awt.GridBagLayout());
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16,16,16,16));
    panel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 9999));
    builder.accept(panel);
    return panel;
}

private java.awt.GridBagConstraints defaultGc() {
    java.awt.GridBagConstraints gc = new java.awt.GridBagConstraints();
    gc.fill    = java.awt.GridBagConstraints.HORIZONTAL;
    gc.weightx = 1.0;
    gc.gridx   = 0;
    gc.gridwidth = 2;
    gc.insets  = new java.awt.Insets(3, 0, 3, 0);
    return gc;
}

private java.awt.Font labelFontSettings() {
    return new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11);
}

private java.awt.Color mutedColor() {
    return new java.awt.Color(120, 120, 140);
}

private javax.swing.JTextField makeSettingsField(String value) {
    javax.swing.JTextField f = new javax.swing.JTextField(
            value == null ? "" : value);
    f.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    f.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(220, 224, 245), 1),
        javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    return f;
}

private javax.swing.JButton makeSettingsBtn(String label) {
    javax.swing.JButton btn = new javax.swing.JButton(label);
    styleReportButton(btn, new java.awt.Color(33, 55, 98));
    return btn;
}

private void showSaved(String msg) {
    javax.swing.JOptionPane.showMessageDialog(this, msg,
        "Saved", javax.swing.JOptionPane.INFORMATION_MESSAGE);
}

private void loadLabsIntoModel(
        javax.swing.table.DefaultTableModel model) {
    model.setRowCount(0);
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT labName, labSlug FROM labs WHERE userId = ?")) {
        ps.setInt(1, currentUserId);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            model.addRow(new Object[]{
                rs.getString("labName"),
                rs.getString("labSlug")});
    } catch (SQLException e) {
        System.err.println("Labs load failed: " + e.getMessage());
    }
}

private void saveLabToDb(String name, String slug) {
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "INSERT OR IGNORE INTO labs (labName, labSlug, userId) " +
            "VALUES (?,?,?)")) {
        ps.setString(1, name);
        ps.setString(2, slug);
        ps.setInt(3, currentUserId);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Lab save failed: " + e.getMessage());
    }
}

private void deleteLabFromDb(String slug) {
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "DELETE FROM labs WHERE labSlug = ? AND userId = ?")) {
        ps.setString(1, slug);
        ps.setInt(2, currentUserId);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Lab delete failed: " + e.getMessage());
    }
}


// Helper — styled checkbox

private javax.swing.JPanel buildInventoryCard() {

    // -------------------------------------------------------
    //  Search and filter bar (pinned — never scrolls away)
    // -------------------------------------------------------
    custom_ui.RoundedPanel searchBar = new custom_ui.RoundedPanel(12);
    searchBar.setBackground(java.awt.Color.WHITE);
    searchBar.setLayout(new java.awt.FlowLayout(
            java.awt.FlowLayout.LEFT, 10, 8));
    searchBar.setPreferredSize(new java.awt.Dimension(0, 52));
    searchBar.setMaximumSize(new java.awt.Dimension(
            Integer.MAX_VALUE, 52));

    javax.swing.JTextField searchField = new javax.swing.JTextField(20);
    searchField.putClientProperty(
            "JTextField.placeholderText", "Search by name...");
    searchField.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 12));

    String[] typeOptions = {"All Types", "Equipment", "Material",
                            "Tool", "Electronics", "Other"};
    javax.swing.JComboBox<String> typeFilter =
            new javax.swing.JComboBox<>(typeOptions);
    typeFilter.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 12));
    String[] deptOptions = {
    "All Departments",
    "Electrical Power and Machines",
    "Electronics and Control",
    "Networking and Telecom",
    "Multimeter Allocation",
    "General"
};
    javax.swing.JComboBox<String> deptFilter =
        new javax.swing.JComboBox<>(deptOptions);
deptFilter.setFont(new java.awt.Font(
        "Segoe UI", java.awt.Font.PLAIN, 12));

    javax.swing.JButton searchBtn = new javax.swing.JButton("Search");
    javax.swing.JButton clearBtn  = new javax.swing.JButton("Clear");
    styleReportButton(searchBtn, new java.awt.Color(33, 55, 98));
    styleReportButton(clearBtn,  new java.awt.Color(120, 120, 120));

    searchBar.add(new javax.swing.JLabel("Search:"));
    searchBar.add(searchField);
    searchBar.add(new javax.swing.JLabel("Type:"));
    searchBar.add(new javax.swing.JLabel("Department:"));
    searchBar.add(typeFilter);
    searchBar.add(new javax.swing.JLabel("Dept:"));
    searchBar.add(deptFilter);
    searchBar.add(searchBtn);
    searchBar.add(clearBtn);

    // -------------------------------------------------------
    //  Inventory table (fills remaining space, scrolls inside)
    // -------------------------------------------------------
    custom_ui.RoundedPanel tablePanel = new custom_ui.RoundedPanel(12);
    tablePanel.setBackground(java.awt.Color.WHITE);
    tablePanel.setLayout(new java.awt.BorderLayout());
    tablePanel.setBorder(javax.swing.BorderFactory
            .createEmptyBorder(8, 8, 8, 8));

    javax.swing.JLabel tableTitle = new javax.swing.JLabel(
            "INVENTORY LIST");
    tableTitle.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.BOLD, 14));
    tableTitle.setForeground(new java.awt.Color(33, 55, 98));
    tableTitle.setBorder(javax.swing.BorderFactory
            .createEmptyBorder(4, 4, 8, 4));

    String[] cols = {"ID", "Item", "Quantity","Unit", "Type","Department", "Description", "Status"};
    javax.swing.table.DefaultTableModel inventoryModel =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    javax.swing.JTable inventoryTable =
            new javax.swing.JTable(inventoryModel);
    inventoryTable.getTableHeader().setToolTipText(
            "Double-click Qty, Unit, Type, Department or " + "Description to edit inline");
    inventoryTable.setRowHeight(30);
    inventoryTable.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 12));
    inventoryTable.getTableHeader().setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.BOLD, 11));
    inventoryTable.getTableHeader().setBackground(
            new java.awt.Color(245, 245, 245));
    inventoryTable.setShowVerticalLines(false);
    inventoryTable.setGridColor(new java.awt.Color(240, 240, 240));
    inventoryTable.setSelectionBackground(
            new java.awt.Color(80, 150, 80));
    // Replace single selection with multiple:
    inventoryTable.setSelectionMode(
            javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // Hide ID
    inventoryTable.getColumnModel().getColumn(0).setMinWidth(0);
    inventoryTable.getColumnModel().getColumn(0).setMaxWidth(0);
    inventoryTable.getColumnModel().getColumn(0).setWidth(0);

    // Give Description column more room
    inventoryTable.getColumnModel().getColumn(6)
            .setPreferredWidth(200);

    // Shrink Unit column — it just shows "pieces", "sets" etc.
    inventoryTable.getColumnModel().getColumn(3)
            .setPreferredWidth(60);
    // Status column renderer stays on column 7 now
    inventoryTable.getColumnModel().getColumn(7).setCellRenderer(
        new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object val, boolean sel,
                    boolean focus, int row, int col) {
                super.getTableCellRendererComponent(
                        t, val, sel, focus, row, col);
                String status = val != null ? val.toString() : "";
                switch (status) {
                    case "Lent Out"  ->
                        setForeground(new java.awt.Color(200, 100, 0));
                    case "Low Stock" ->
                        setForeground(new java.awt.Color(220, 50, 50));
                    default          ->
                        setForeground(new java.awt.Color(40, 160, 80));
                }
                return this;
            }
        });
    
    
    javax.swing.JScrollPane tableScroll =
            new javax.swing.JScrollPane(inventoryTable);
    tableScroll.setBorder(
            javax.swing.BorderFactory.createEmptyBorder());

    tablePanel.add(tableTitle, java.awt.BorderLayout.NORTH);
    tablePanel.add(tableScroll, java.awt.BorderLayout.CENTER);

    // -------------------------------------------------------
    //  Action buttons (pinned below table)
    // -------------------------------------------------------
    javax.swing.JPanel btnPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 4));
    btnPanel.setOpaque(false);

    javax.swing.JButton addBtn       = new javax.swing.JButton("+ Add Item");
    javax.swing.JButton reduceBtn    = new javax.swing.JButton("Reduce Quantity");
    javax.swing.JButton removeBtn    = new javax.swing.JButton("Remove Item");
    javax.swing.JButton lendBtn      = new javax.swing.JButton("Lend Item");
    javax.swing.JButton lendingBtn   = new javax.swing.JButton("View Lendings");
    javax.swing.JButton refreshBtn   = new javax.swing.JButton("Refresh");
    javax.swing.JButton csvImportBtn = new javax.swing.JButton("Import CSV");
    javax.swing.JButton equipImportBtn = new javax.swing.JButton("Import Equipment List");
    javax.swing.JButton csvExportBtn = new javax.swing.JButton("Export CSV");
    javax.swing.JButton exportEqBtn  = new javax.swing.JButton("Export Equipment List");


    styleReportButton(addBtn,       new java.awt.Color(40, 160, 80));
    styleReportButton(reduceBtn,    new java.awt.Color(200, 130, 0));
    styleReportButton(removeBtn,    new java.awt.Color(200, 50, 50));
    //styleReportButton(lendBtn,      new java.awt.Color(33, 100, 180));
    //styleReportButton(lendingBtn,   new java.awt.Color(100, 60, 180));
    styleReportButton(refreshBtn,   new java.awt.Color(80, 80, 80));
    //styleReportButton(csvImportBtn, new java.awt.Color(60, 130, 180));
    styleReportButton(equipImportBtn, new java.awt.Color(33, 55, 140));
    //styleReportButton(csvExportBtn, new java.awt.Color(100, 100, 100));
    styleReportButton(exportEqBtn, new java.awt.Color(33, 55, 140));
    
    // -------------------------------------------------------
//  Role-based access — non-admin sees restricted buttons
// -------------------------------------------------------
if (!isAdmin()) {
    // Non-admins never see these buttons at all, regardless of selection
    addBtn.setVisible(false);
    removeBtn.setVisible(false);
    reduceBtn.setVisible(false);
    equipImportBtn.setVisible(false);
    csvImportBtn.setVisible(false);
    exportEqBtn.setVisible(false);
    csvExportBtn.setVisible(false);

    javax.swing.JLabel viewOnlyBanner = new javax.swing.JLabel(
        "  View Only — contact an administrator to make changes",
        javax.swing.SwingConstants.LEFT);
    viewOnlyBanner.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.ITALIC, 11));
    viewOnlyBanner.setForeground(new java.awt.Color(180, 100, 0));
    viewOnlyBanner.setOpaque(true);
    viewOnlyBanner.setBackground(new java.awt.Color(255, 248, 220));
    viewOnlyBanner.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(220, 180, 80), 1),
        javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    btnPanel.add(viewOnlyBanner);
} else {
    // Admin: Remove and Reduce only appear when at least one row is selected
    removeBtn.setVisible(false);
    reduceBtn.setVisible(false);

    inventoryTable.getSelectionModel().addListSelectionListener(ev -> {
        boolean hasSelection = inventoryTable.getSelectedRowCount() > 0;
        removeBtn.setVisible(hasSelection);
        reduceBtn.setVisible(hasSelection);
        btnPanel.revalidate();
        btnPanel.repaint();
    });
}

// Block double-click editing for non-admins
// (the mouseClicked handler already has this check further down,
//  but add it as the first line if it's missing)

// Also block double-click editing for non-admins
// Add this check at the very start of the mouseClicked handler:
// if (!isAdmin()) return;
    btnPanel.add(addBtn);
    btnPanel.add(removeBtn);
    btnPanel.add(reduceBtn);
    //btnPanel.add(lendBtn);
    //btnPanel.add(lendingBtn);
    btnPanel.add(refreshBtn);
    //btnPanel.add(csvImportBtn);
    //btnPanel.add(csvExportBtn);
    btnPanel.add(equipImportBtn);
    btnPanel.add(exportEqBtn);
    // -------------------------------------------------------
    //  Load initial data
    // -------------------------------------------------------
    loadInventoryTable(inventoryModel, "", "All Types","All Departments");

    // -------------------------------------------------------
    //  Search / filter listeners
    // -------------------------------------------------------
    Runnable doSearch = () -> loadInventoryTable(
            inventoryModel,
            searchField.getText().trim(),
            typeFilter.getSelectedItem().toString(),
            deptFilter.getSelectedItem().toString());

    searchBtn.addActionListener(e -> {
        doSearch.run();
        java.awt.EventQueue.invokeLater(
                searchField::requestFocusInWindow);
    });

    clearBtn.addActionListener(e -> {
        searchField.setText("");
        typeFilter.setSelectedIndex(0);
        deptFilter.setSelectedIndex(0);
        loadInventoryTable(inventoryModel, "", "All Types","All Departments");
        java.awt.EventQueue.invokeLater(
                searchField::requestFocusInWindow);
    });

    searchField.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override public void keyReleased(java.awt.event.KeyEvent e) {
            doSearch.run();
            java.awt.EventQueue.invokeLater(searchField::requestFocusInWindow);
        }
    });

    typeFilter.addActionListener(e -> {
        doSearch.run();
        java.awt.EventQueue.invokeLater(
                searchField::requestFocusInWindow);
    });
    deptFilter.addActionListener(e -> {
        doSearch.run();
        java.awt.EventQueue.invokeLater(
                searchField::requestFocusInWindow);
    });

    // -------------------------------------------------------
    //  Add Item
    // -------------------------------------------------------
    addBtn.addActionListener(e -> {
        try{
            if (addItemScreen == null || !addItemScreen.isDisplayable()) {
                addItemScreen = new AddItemScreen(currentUserId, this);
                addItemScreen.setVisible(true);
            } else {
                addItemScreen.toFront();
            }
        } catch (Throwable t) {
            // This will show you the REAL error
            javax.swing.JOptionPane.showMessageDialog(this,
                "Could not open Add Item screen:\n" + t.getMessage() +
                "\n\n" + t.getClass().getName(),
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            t.printStackTrace();
        }
    });

    // -------------------------------------------------------
    //  CSV import / export
    // -------------------------------------------------------
    csvImportBtn.addActionListener(e -> {
        importInventoryCSV();
        doSearch.run();
    });
    equipImportBtn.addActionListener(e -> importEquipmentListCSV());
    csvExportBtn.addActionListener(e -> exportInventoryCSV());
    exportEqBtn.addActionListener(e -> exportEquipmentList());
    
    // -------------------------------------------------------
    //  Remove Item
    // -------------------------------------------------------
    removeBtn.addActionListener(e -> {
        int[] selectedRows = inventoryTable.getSelectedRows();
        if (selectedRows.length == 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Select one or more items first.", "No Selection",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Build confirmation message
        StringBuilder names = new StringBuilder();
        for (int row : selectedRows) {
            names.append("  • ")
                 .append(inventoryModel.getValueAt(row, 1))
                 .append("\n");
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Permanently delete " + selectedRows.length + " item(s)?\n\n"
            + names,
            "Confirm Delete",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);
        if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

        // Second confirmation for bulk deletes
        if (selectedRows.length > 1) {
            int confirm2 = javax.swing.JOptionPane.showConfirmDialog(this,
                "Are you absolutely sure? This cannot be undone.",
                "Final Confirmation",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);
            if (confirm2 != javax.swing.JOptionPane.YES_OPTION) return;
        }
        
        // Ask for a removal reason
    String reason = javax.swing.JOptionPane.showInputDialog(this,
        "Reason for removal (optional):", "Damaged / Write-off / Other");
    if (reason == null) reason = "Manual removal";

    final String finalReason = reason;
    int deleted = 0;

    try (Connection con = DatabaseConfig.getConnection()) {
        con.setAutoCommit(false);

        PreparedStatement fetch = con.prepareStatement(
            "SELECT name, type, department, quantity, " +
            "quantityUnit, description FROM inventory WHERE itemId = ?");

        PreparedStatement archive = con.prepareStatement(
            "INSERT INTO removed_items " +
            "(itemName, itemType, department, quantity, unit, " +
            " description, removedBy, reason, userId) " +
            "VALUES (?,?,?,?,?,?,?,?,?)");

        PreparedStatement del = con.prepareStatement(
            "DELETE FROM inventory WHERE itemId = ?");

        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int itemId = Integer.parseInt(
                inventoryModel.getValueAt(selectedRows[i], 0).toString());

            // Archive the record before deleting
            fetch.setInt(1, itemId);
            ResultSet rs = fetch.executeQuery();
            if (rs.next()) {
                archive.setString(1, rs.getString("name"));
                archive.setString(2, rs.getString("type"));
                archive.setString(3, rs.getString("department"));
                archive.setInt(4, rs.getInt("quantity"));
                archive.setString(5, rs.getString("quantityUnit"));
                archive.setString(6, rs.getString("description"));
                archive.setString(7, currentUserName);
                archive.setString(8, finalReason);
                archive.setInt(9, currentUserId);
                archive.executeUpdate();
            }

            del.setInt(1, itemId);
            del.executeUpdate();
            deleted++;
        }

        con.commit();
        javax.swing.JOptionPane.showMessageDialog(this,
            deleted + " item(s) removed and archived.");
        doSearch.run();
        loadTable();
        searchField.requestFocusInWindow();

    } catch (SQLException ex) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Delete failed: " + ex.getMessage());
    }
        java.awt.EventQueue.invokeLater(searchField::requestFocusInWindow);
    });

    // -------------------------------------------------------
    //  Reduce Quantity
    // -------------------------------------------------------
    reduceBtn.addActionListener(e -> {
        int[] selectedRows = inventoryTable.getSelectedRows();
        if (selectedRows.length == 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Select one or more items first.", "No Selection",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        String input = javax.swing.JOptionPane.showInputDialog(this,
            "Reduce quantity of " + selectedRows.length +
            " selected item(s) by how many?", "1");
        if (input == null || input.isBlank()) return;

        try {
            int reduceBy = Integer.parseInt(input.trim());
            if (reduceBy <= 0) return;

            try (Connection con = DatabaseConfig.getConnection()) {
                con.setAutoCommit(false);
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE inventory SET quantity = MAX(0, quantity - ?) " +
                    "WHERE itemId = ? AND userId = ?");
                for (int row : selectedRows) {
                    int itemId = Integer.parseInt(
                        inventoryModel.getValueAt(row, 0).toString());
                    ps.setInt(1, reduceBy);
                    ps.setInt(2, itemId);
                    ps.setInt(3, currentUserId);
                    ps.executeUpdate();
                }
                con.commit();
                doSearch.run();
                loadTable();
                searchField.requestFocusInWindow();
            }
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Enter a valid number.");
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Update failed: " + ex.getMessage());
        }
        java.awt.EventQueue.invokeLater(searchField::requestFocusInWindow);//give search field focus
    });

    // -------------------------------------------------------
    //  Lend Item
    // -------------------------------------------------------
    lendBtn.addActionListener(e -> {
        int row = inventoryTable.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Select an item to lend.", "No Selection",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        String itemName = inventoryModel.getValueAt(row, 1).toString();
        int itemId     = Integer.parseInt(
                inventoryModel.getValueAt(row, 0).toString());
        int currentQty = Integer.parseInt(
                inventoryModel.getValueAt(row, 2).toString());

        if (currentQty <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "No stock available to lend.");
            return;
        }

        javax.swing.JTextField borrowerF = new javax.swing.JTextField();
        javax.swing.JTextField reasonF   = new javax.swing.JTextField();
        javax.swing.JTextField qtyF      = new javax.swing.JTextField("1");
        javax.swing.JTextField dateF     = new javax.swing.JTextField(
            new java.text.SimpleDateFormat("yyyy-MM-dd").format(
            new java.util.Date(System.currentTimeMillis() +
            7L * 24 * 60 * 60 * 1000)));

        String[] reasonOptions = {"Lab Practical", "Student Project",
                                  "Staff Use", "Maintenance", "Other"};
        javax.swing.JComboBox<String> reasonCombo =
                new javax.swing.JComboBox<>(reasonOptions);

        Object[] fields = {
            "Item:", new javax.swing.JLabel(itemName),
            "Borrower Name:", borrowerF,
            "Reason:", reasonCombo,
            "Additional Notes:", reasonF,
            "Quantity to Lend:", qtyF,
            "Expected Return Date (yyyy-MM-dd):", dateF
        };

        if (javax.swing.JOptionPane.showConfirmDialog(this,
                fields, "Lend Item",
                javax.swing.JOptionPane.OK_CANCEL_OPTION)
                != javax.swing.JOptionPane.OK_OPTION) return;

        try {
            int lendQty = Integer.parseInt(qtyF.getText().trim());
            if (lendQty <= 0 || lendQty > currentQty) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Invalid quantity. Available: " + currentQty);
                return;
            }

            String reason = reasonCombo.getSelectedItem() +
                (reasonF.getText().isBlank() ? ""
                 : " — " + reasonF.getText().trim());

            try (Connection con = DatabaseConfig.getConnection()) {
                PreparedStatement lendPs = con.prepareStatement(
                    "INSERT INTO lending " +
                    "(item_id, item_name, quantity, reason, " +
                    " borrower, return_date, userId) " +
                    "VALUES (?,?,?,?,?,?,?)");
                lendPs.setInt(1, itemId);
                lendPs.setString(2, itemName);
                lendPs.setInt(3, lendQty);
                lendPs.setString(4, reason);
                lendPs.setString(5, borrowerF.getText().trim());
                lendPs.setString(6, dateF.getText().trim());
                lendPs.setInt(7, currentUserId);
                lendPs.executeUpdate();

                PreparedStatement updatePs = con.prepareStatement(
                    "UPDATE inventory SET quantity = quantity - ? " +
                    "WHERE itemId = ? AND userId = ?");
                updatePs.setInt(1, lendQty);
                updatePs.setInt(2, itemId);
                updatePs.setInt(3, currentUserId);
                updatePs.executeUpdate();
            }

            javax.swing.JOptionPane.showMessageDialog(this,
                lendQty + "x \"" + itemName + "\" lent.\n" +
                "Expected return: " + dateF.getText().trim());
            doSearch.run();
            loadTable();

        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Enter a valid quantity.");
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Lending failed: " + ex.getMessage());
        }
    });

    // -------------------------------------------------------
    //  View Lendings / Refresh
    // -------------------------------------------------------
    lendingBtn.addActionListener(e -> showLendingDialog());
    refreshBtn.addActionListener(e -> {
        doSearch.run();
        java.awt.EventQueue.invokeLater(
                searchField::requestFocusInWindow);
    });

    // -------------------------------------------------------
    //  Double-click inline editing
    // -------------------------------------------------------
    inventoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (!isAdmin()) return;
        if (e.getClickCount() != 2) return;
        int row = inventoryTable.rowAtPoint(e.getPoint());
        int col = inventoryTable.columnAtPoint(e.getPoint());
        if (row < 0) return;

        int    itemId = Integer.parseInt(
                inventoryModel.getValueAt(row, 0).toString());
        String name   = inventoryModel.getValueAt(row, 1).toString();
        int    qty    = Integer.parseInt(
                inventoryModel.getValueAt(row, 2).toString());
        String unit   = inventoryModel.getValueAt(row, 3).toString();
        String type   = inventoryModel.getValueAt(row, 4).toString();
        String dept   = inventoryModel.getValueAt(row, 5).toString();
        String desc   = inventoryModel.getValueAt(row, 6).toString();

        switch (col) {

            // --- Column 2: Quantity ---
            case 2 -> {
                String[] options = {"Increase", "Reduce"};
                int choice = javax.swing.JOptionPane.showOptionDialog(
                    staff_dashboard.this,
                    "Modify quantity of \"" + name + "\":",
                    "Edit Quantity",
                    javax.swing.JOptionPane.DEFAULT_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
                if (choice < 0) return;

                String input = javax.swing.JOptionPane.showInputDialog(
                    staff_dashboard.this,
                    (choice == 0 ? "Increase" : "Reduce") +
                    " by how many? (current: " + qty + ")", "1");
                if (input == null || input.isBlank()) return;

                try {
                    int delta  = Integer.parseInt(input.trim());
                    if (delta <= 0) return;
                    int newQty = choice == 0
                            ? qty + delta : qty - delta;
                    if (newQty < 0) {
                        javax.swing.JOptionPane.showMessageDialog(
                            staff_dashboard.this,
                            "Cannot reduce below zero.");
                        return;
                    }
                    updateInventoryField(itemId,
                            "quantity", newQty);
                    doSearch.run();
                    loadTable();
                } catch (NumberFormatException ex) {
                    javax.swing.JOptionPane.showMessageDialog(
                        staff_dashboard.this,
                        "Enter a valid number.");
                }
            }

            // --- Column 3: Unit ---
            case 3 -> {
                String[] units = {"pieces", "sets", "pairs",
                    "boxes", "rolls", "metres", "litres", "kg"};
                String newUnit = (String)
                    javax.swing.JOptionPane.showInputDialog(
                        staff_dashboard.this,
                        "Change quantity unit for \"" + name + "\":",
                        "Edit Unit",
                        javax.swing.JOptionPane.QUESTION_MESSAGE,
                        null, units,
                        unit.isBlank() ? "pieces" : unit);
                if (newUnit == null || newUnit.equals(unit)) return;
                updateInventoryField(itemId,
                        "quantityUnit", newUnit);
                doSearch.run();
            }

            // --- Column 4: Type ---
            case 4 -> {
                String[] types = {"Equipment", "Material",
                                  "Tool", "Electronics", "Other"};
                String newType = (String)
                    javax.swing.JOptionPane.showInputDialog(
                        staff_dashboard.this,
                        "Change type of \"" + name + "\":",
                        "Edit Type",
                        javax.swing.JOptionPane.QUESTION_MESSAGE,
                        null, types, type);
                if (newType == null || newType.equals(type)) return;
                updateInventoryField(itemId, "type", newType);
                doSearch.run();
            }

            // --- Column 5: Department ---
            case 5 -> {
                String[] depts = {
                    "Electrical Power and Machines",
                    "Electronics and Control",
                    "Networking and Telecom",
                    "Multimeter Allocation",
                    "General"
                };
                String newDept = (String)
                    javax.swing.JOptionPane.showInputDialog(
                        staff_dashboard.this,
                        "Change department for \"" + name + "\":",
                        "Edit Department",
                        javax.swing.JOptionPane.QUESTION_MESSAGE,
                        null, depts,
                        dept.isBlank() ? depts[0] : dept);
                if (newDept == null || newDept.equals(dept)) return;
                updateInventoryField(itemId,
                        "department", newDept);
                doSearch.run();
            }

            // --- Column 6: Description ---
            case 6 -> {
                javax.swing.JTextArea textArea =
                        new javax.swing.JTextArea(desc, 4, 30);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setFont(new java.awt.Font(
                        "Segoe UI", java.awt.Font.PLAIN, 12));
                javax.swing.JScrollPane sp =
                        new javax.swing.JScrollPane(textArea);
                sp.setPreferredSize(
                        new java.awt.Dimension(320, 100));

                int result = javax.swing.JOptionPane.showConfirmDialog(
                    staff_dashboard.this,
                    sp,
                    "Edit Description for \"" + name + "\"",
                    javax.swing.JOptionPane.OK_CANCEL_OPTION);
                if (result != javax.swing.JOptionPane.OK_OPTION)
                    return;
                String newDesc = textArea.getText().trim();
                if (newDesc.equals(desc)) return;
                updateInventoryField(itemId,
                        "description", newDesc);
                doSearch.run();
            }
        }
    }
});

    // Focus search field when card is shown
    searchBar.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentShown(java.awt.event.ComponentEvent e) {
            java.awt.EventQueue.invokeLater(
                    searchField::requestFocusInWindow);
        }
    });

    // -------------------------------------------------------
    //  Assemble — BorderLayout so table fills all space
    //  between the pinned search bar and pinned button row
    // -------------------------------------------------------
    javax.swing.JPanel inner = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 8));
    inner.setOpaque(false);
    inner.setBorder(javax.swing.BorderFactory.createEmptyBorder(
            12, 12, 12, 12));

    inner.add(searchBar,  java.awt.BorderLayout.NORTH);
    inner.add(tablePanel, java.awt.BorderLayout.CENTER);
    inner.add(btnPanel,   java.awt.BorderLayout.SOUTH);

    this.inventoryCard = inner;
    return wrapInScrollCard(inner);
}

private void updateInventoryField(int itemId,
        String column, Object value) {
    // Whitelist allowed column names to prevent SQL injection
    java.util.Set<String> allowed = java.util.Set.of(
        "quantity", "quantityUnit", "type",
        "department", "description");
    if (!allowed.contains(column)) {
        System.err.println("Blocked update to column: " + column);
        return;
    }
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "UPDATE inventory SET " + column +
            " = ? WHERE itemId = ? AND userId = ?")) {
        if (value instanceof Integer) {
            ps.setInt(1, (Integer) value);
        } else {
            ps.setString(1, value.toString());
        }
        ps.setInt(2, itemId);
        ps.setInt(3, currentUserId);
        ps.executeUpdate();
    } catch (SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Update failed: " + e.getMessage());
    }
}

private void loadInventoryTable(
        javax.swing.table.DefaultTableModel model,
        String search, String typeFilter,
        String deptFilter) {

    model.setRowCount(0);
    StringBuilder sql = new StringBuilder(
        "SELECT itemId, name, quantity, quantityUnit, " +
        "type, department, description " +
        "FROM inventory WHERE 1=1"); // <-- FIXED: added WHERE 1=1

    if (!search.isBlank())
        sql.append(" AND name LIKE ?");
    if (!typeFilter.equals("All Types"))
        sql.append(" AND type = ?");
    if (!deptFilter.equals("All Departments"))
        sql.append(" AND department = ?");

    sql.append(" ORDER BY department ASC, name ASC");

    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
                 sql.toString())) {
        int p = 1;
        
        if (!search.isBlank())
            ps.setString(p++, "%" + search + "%");
        if (!typeFilter.equals("All Types"))
            ps.setString(p++, typeFilter);
        if (!deptFilter.equals("All Departments"))
            ps.setString(p++, deptFilter);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int    qty  = rs.getInt("quantity");
            String unit = rs.getString("quantityUnit");
            if (unit == null) unit = "";
            String status = qty <= 0  ? "Lent Out"
                          : qty < 10  ? "Low Stock"
                          : "In Stock";
            model.addRow(new Object[]{
                rs.getInt("itemId"),
                rs.getString("name"),
                qty, unit,
                rs.getString("type"),
                rs.getString("department") == null
                    ? "" : rs.getString("department"),
                rs.getString("description") == null
                    ? "" : rs.getString("description"),
                status
            });
        }
    } catch (SQLException e) {
        System.err.println("Inventory load: " + e.getMessage());
    }
}
private void showLendingDialog() {
    javax.swing.JDialog dialog = new javax.swing.JDialog(this,
        "Lending Records", true);
    dialog.setSize(800, 500);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new java.awt.BorderLayout());

    // Table
    String[] cols = {"ID", "Item", "Qty", "Borrower",
                     "Reason", "Lent Date", "Return Date", "Returned"};
    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setRowHeight(28);
    table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    table.getTableHeader().setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));

    // Hide ID column
    table.getColumnModel().getColumn(0).setMinWidth(0);
    table.getColumnModel().getColumn(0).setMaxWidth(0);

    // Load lending records
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT id, item_name, quantity, borrower, reason, " +
            "lent_date, return_date, returned FROM lending " +
            "WHERE userId = ? ORDER BY lent_date DESC")) {
        ps.setInt(1, currentUserId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("item_name"),
                rs.getInt("quantity"),
                rs.getString("borrower"),
                rs.getString("reason"),
                rs.getString("lent_date"),
                rs.getString("return_date"),
                rs.getInt("returned") == 1 ? "Yes" : "No"
            });
        }
    } catch (SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Could not load lending records: " + e.getMessage());
    }

    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);

    // Mark as returned button
    javax.swing.JButton returnBtn = new javax.swing.JButton("Mark as Returned");
    styleReportButton(returnBtn, new java.awt.Color(40, 160, 80));

    returnBtn.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row < 0) {
        javax.swing.JOptionPane.showMessageDialog(dialog,
            "Select a lending record first.");
        return;
    }

    // Block if already returned
    String alreadyReturned = model.getValueAt(row, 7).toString();
    if (alreadyReturned.equals("Yes")) {
        javax.swing.JOptionPane.showMessageDialog(dialog,
            "This item has already been marked as returned.",
            "Already Returned",
            javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    int lendId  = Integer.parseInt(model.getValueAt(row, 0).toString());
    String item = model.getValueAt(row, 1).toString();
    int qty     = Integer.parseInt(model.getValueAt(row, 2).toString());

    int confirm = javax.swing.JOptionPane.showConfirmDialog(dialog,
        "Mark " + qty + "x \"" + item + "\" as returned?\n" +
        "This will restore the quantity to inventory.",
        "Confirm Return",
        javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

    try (Connection con = DatabaseConfig.getConnection()) {

        // Double-check in DB that it hasn't already been returned
        // (guards against the dialog being open twice)
        PreparedStatement checkPs = con.prepareStatement(
            "SELECT returned FROM lending WHERE id = ?");
        checkPs.setInt(1, lendId);
        ResultSet rs = checkPs.executeQuery();
        if (rs.next() && rs.getInt("returned") == 1) {
            javax.swing.JOptionPane.showMessageDialog(dialog,
                "This record was already marked as returned.",
                "Already Returned",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            model.setValueAt("Yes", row, 7); // sync the table display
            return;
        }

        // Mark as returned in DB
        PreparedStatement markPs = con.prepareStatement(
            "UPDATE lending SET returned = 1 WHERE id = ? AND returned = 0");
        markPs.setInt(1, lendId);
        int affected = markPs.executeUpdate();

        if (affected == 0) {
            // Another process already marked it — don't restore stock
            javax.swing.JOptionPane.showMessageDialog(dialog,
                "Could not update — may have already been returned.");
            return;
        }

        // Restore quantity to inventory only if update succeeded
        PreparedStatement restorePs = con.prepareStatement(
            "UPDATE inventory SET quantity = quantity + ? " +
            "WHERE name = ? AND userId = ?");
        restorePs.setInt(1, qty);
        restorePs.setString(2, item);
        restorePs.setInt(3, currentUserId);
        restorePs.executeUpdate();

        // Update table display
        model.setValueAt("Yes", row, 7);

        javax.swing.JOptionPane.showMessageDialog(dialog,
            qty + "x \"" + item + "\" marked as returned.\n" +
            "Inventory quantity restored.");
        loadTable();

    } catch (SQLException ex) {
        javax.swing.JOptionPane.showMessageDialog(dialog,
            "Error: " + ex.getMessage());
    }
});

    javax.swing.JPanel bottomPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
    bottomPanel.add(returnBtn);

    dialog.add(scroll,       java.awt.BorderLayout.CENTER);
    dialog.add(bottomPanel,  java.awt.BorderLayout.SOUTH);
    dialog.setVisible(true);
}
private javax.swing.JPanel buildDashboardCard() {
    javax.swing.JPanel inner = new javax.swing.JPanel();
    inner.setOpaque(false);
    inner.setLayout(new javax.swing.BoxLayout(
            inner, javax.swing.BoxLayout.Y_AXIS));
    inner.setBorder(javax.swing.BorderFactory.createEmptyBorder(
            12, 12, 12, 12));

    // Stat cards row — fixed height block
    javax.swing.JPanel statsRow = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
    statsRow.setOpaque(false);
    statsRow.setMaximumSize(new java.awt.Dimension(
            Integer.MAX_VALUE, 90));
    statsRow.setPreferredSize(new java.awt.Dimension(0, 90));
    rebuildStatCards(statsRow);
    inner.add(statsRow);
    inner.add(javax.swing.Box.createVerticalStrut(10));

    // Tables — each gets a fixed preferred height, never clips
    int tableH = 240;

    if (DashboardSettings.showLowStockTbl
            || DashboardSettings.showRecentTbl) {
        javax.swing.JPanel row = new javax.swing.JPanel(
                new java.awt.GridLayout(1, 0, 10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new java.awt.Dimension(
                Integer.MAX_VALUE, tableH));
        row.setPreferredSize(new java.awt.Dimension(0, tableH));
        if (DashboardSettings.showLowStockTbl)
            row.add(buildLowStockPanel());
        if (DashboardSettings.showRecentTbl)
            row.add(buildRecentActivityPanel());
        inner.add(row);
        inner.add(javax.swing.Box.createVerticalStrut(10));
    }

    if (DashboardSettings.showInventoryTbl
            || DashboardSettings.showLendingTbl
            || DashboardSettings.showRemovedTbl) {
        javax.swing.JPanel row = new javax.swing.JPanel(
                new java.awt.GridLayout(1, 0, 10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new java.awt.Dimension(
                Integer.MAX_VALUE, tableH));
        row.setPreferredSize(new java.awt.Dimension(0, tableH));
        if (DashboardSettings.showInventoryTbl)
            row.add(buildInventoryPanel());
        if (DashboardSettings.showLendingTbl)
            row.add(buildLendingHistoryPanel());
        if (DashboardSettings.showRemovedTbl && isAdmin())
            row.add(buildRemovedItemsPanel());
        inner.add(row);
        inner.add(javax.swing.Box.createVerticalStrut(10));
    }

    if (!DashboardSettings.showLowStockTbl
            && !DashboardSettings.showRecentTbl
            && !DashboardSettings.showInventoryTbl
            && !DashboardSettings.showLendingTbl
            && !DashboardSettings.showRemovedTbl) {
        javax.swing.JLabel none = new javax.swing.JLabel(
            "All tables hidden. Enable them in Settings.",
            javax.swing.SwingConstants.CENTER);
        none.setForeground(new java.awt.Color(180, 180, 200));
        none.setFont(new java.awt.Font(
                "Segoe UI", java.awt.Font.PLAIN, 13));
        inner.add(none);
    }

    return wrapInScrollCard(inner);
}


private javax.swing.JComponent buildTableGrid(
        java.util.List<custom_ui.RoundedPanel> panels) {

    // Give every panel a minimum size so split panes can't collapse them
    for (custom_ui.RoundedPanel p : panels) {
        p.setMinimumSize(new java.awt.Dimension(150, 80));
    }

    if (panels.size() == 1) return panels.get(0);

    if (panels.size() == 2) {
        return makeSplit(
            javax.swing.JSplitPane.HORIZONTAL_SPLIT, 0.5,
            panels.get(0), panels.get(1));
    }

    if (panels.size() == 3) {
        javax.swing.JSplitPane top = makeSplit(
            javax.swing.JSplitPane.HORIZONTAL_SPLIT, 0.5,
            panels.get(0), panels.get(1));
        return makeSplit(
            javax.swing.JSplitPane.VERTICAL_SPLIT, 0.55,
            top, panels.get(2));
    }

    // 4 panels — 2x2
    javax.swing.JSplitPane top = makeSplit(
        javax.swing.JSplitPane.HORIZONTAL_SPLIT, 0.5,
        panels.get(0), panels.get(1));
    javax.swing.JSplitPane bot = makeSplit(
        javax.swing.JSplitPane.HORIZONTAL_SPLIT, 0.5,
        panels.get(2), panels.get(3));
    return makeSplit(
        javax.swing.JSplitPane.VERTICAL_SPLIT, 0.5,
        top, bot);
}

private javax.swing.JSplitPane makeSplit(int orientation,
        double weight, java.awt.Component a, java.awt.Component b) {
    javax.swing.JSplitPane sp = new javax.swing.JSplitPane(orientation, a, b);
    sp.setResizeWeight(weight);
    sp.setDividerSize(5);
    sp.setBorder(null);
    sp.setOpaque(false);
    return sp;
}
private void rebuildStatCards(javax.swing.JPanel statsRow) {
    statsRow.removeAll();
    if (DashboardSettings.showTotalItems)
        statsRow.add(makeStatCard("Total Items", getTotalItems(),
                new java.awt.Color(103, 153, 255)));
    if (DashboardSettings.showLowStock)
        statsRow.add(makeStatCard("Low Stock", getLowStock(),
                new java.awt.Color(226, 75, 74)));
    if (DashboardSettings.showItemTypes)
        statsRow.add(makeStatCard("Item Types", getItemTypes(),
                new java.awt.Color(80, 180, 120)));
    if (DashboardSettings.showAddedToday)
        statsRow.add(makeStatCard("Added Today", getAddedToday(),
                new java.awt.Color(255, 180, 50)));
    statsRow.revalidate();
    statsRow.repaint();
}

// -------------------------------------------------------
//  Low stock table panel
// -------------------------------------------------------
private custom_ui.RoundedPanel buildInventoryPanel(){
    custom_ui.RoundedPanel panel = new custom_ui.RoundedPanel(16);
    panel.setBackground(java.awt.Color.WHITE);
    panel.setLayout(new java.awt.BorderLayout());
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // Header
    javax.swing.JLabel title = new javax.swing.JLabel("Items Storage");
    title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    title.setForeground(new java.awt.Color(226, 75, 74));
    title.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));

    // Table
    String[] cols = {"ID", "Item", "Qty", "Unit", "Type",
                 "Department", "Description", "Status"};
    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    table.setRowHeight(28);
    table.getTableHeader().setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
    table.getTableHeader().setBackground(new java.awt.Color(245, 245, 245));
    table.getTableHeader().setForeground(java.awt.Color.GRAY);
    table.setShowVerticalLines(false);
    table.setGridColor(new java.awt.Color(240, 240, 240));
    table.setSelectionBackground(new java.awt.Color(80, 150, 80));
    

    // Colour quantity column red when low
    table.getColumnModel().getColumn(2).setCellRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable t, Object val, boolean sel,
                boolean focus, int row, int col) {
            super.getTableCellRendererComponent(t, val, sel, focus, row, col);
            setHorizontalAlignment(CENTER);
            int qty = val != null ? Integer.parseInt(val.toString()) : 0;
            setForeground(qty <= 5
                    ? new java.awt.Color(226, 75, 74)   // red — critical
                    : new java.awt.Color(220, 140, 0));  // amber — low
            setFont(getFont().deriveFont(java.awt.Font.BOLD));
            return this;
        }
    });

    // Load data
    //loadInventoryTable(model);

    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);
    scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    scroll.getViewport().setBackground(java.awt.Color.WHITE);

    panel.add(title,  java.awt.BorderLayout.NORTH);
    panel.add(scroll, java.awt.BorderLayout.CENTER);
    return panel;
}
private custom_ui.RoundedPanel buildLowStockPanel() {
    custom_ui.RoundedPanel panel = new custom_ui.RoundedPanel(16);
    panel.setBackground(java.awt.Color.WHITE);
    panel.setLayout(new java.awt.BorderLayout());
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // Header
    javax.swing.JLabel title = new javax.swing.JLabel("⚠  Low Stock Items");
    title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    title.setForeground(new java.awt.Color(226, 75, 74));
    title.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));

    // Table
    String[] cols = {"Item", "Type", "Quantity"};
    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    table.setRowHeight(28);
    table.getTableHeader().setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
    table.getTableHeader().setBackground(new java.awt.Color(245, 245, 245));
    table.getTableHeader().setForeground(java.awt.Color.GRAY);
    table.setShowVerticalLines(false);
    table.setGridColor(new java.awt.Color(240, 240, 240));
    table.setSelectionBackground(new java.awt.Color(80, 150, 80));

    // Colour quantity column red when low
    table.getColumnModel().getColumn(2).setCellRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable t, Object val, boolean sel,
                boolean focus, int row, int col) {
            super.getTableCellRendererComponent(t, val, sel, focus, row, col);
            setHorizontalAlignment(CENTER);
            int qty = val != null ? Integer.parseInt(val.toString()) : 0;
            setForeground(qty <= 5
                    ? new java.awt.Color(226, 75, 74)   // red — critical
                    : new java.awt.Color(220, 140, 0));  // amber — low
            setFont(getFont().deriveFont(java.awt.Font.BOLD));
            return this;
        }
    });

    // Load data
    loadLowStockTable(model);

    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);
    scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    scroll.getViewport().setBackground(java.awt.Color.WHITE);

    panel.add(title,  java.awt.BorderLayout.NORTH);
    panel.add(scroll, java.awt.BorderLayout.CENTER);

    return panel;
}
private custom_ui.RoundedPanel buildRemovedItemsPanel() {
    custom_ui.RoundedPanel panel = new custom_ui.RoundedPanel(16);
    panel.setBackground(java.awt.Color.WHITE);
    panel.setLayout(new java.awt.BorderLayout());
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

    javax.swing.JLabel title = new javax.swing.JLabel("🗑  Removed Items");
    title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    title.setForeground(new java.awt.Color(160, 60, 60));
    title.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));

    String[] cols = {"Item", "Qty", "Removed By", "Removed At", "Reason"};
    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    table.setRowHeight(26);
    table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
    table.getTableHeader().setBackground(new java.awt.Color(245, 245, 245));
    table.setShowVerticalLines(false);
    table.setGridColor(new java.awt.Color(240, 240, 240));
    table.setSelectionBackground(new java.awt.Color(255, 230, 230));

    loadRemovedItemsTable(model);

    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);
    scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    scroll.getViewport().setBackground(java.awt.Color.WHITE);

    panel.add(title,  java.awt.BorderLayout.NORTH);
    panel.add(scroll, java.awt.BorderLayout.CENTER);

    return panel;
}

private void loadRemovedItemsTable(javax.swing.table.DefaultTableModel model) {
    model.setRowCount(0);
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT itemName, quantity, removedBy, removedAt, reason " +
            "FROM removed_items ORDER BY removedAt DESC LIMIT 50")) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("itemName"),
                rs.getInt("quantity"),
                rs.getString("removedBy"),
                rs.getString("removedAt"),
                rs.getString("reason")
            });
        }
    } catch (SQLException e) {
        System.err.println("Removed items load failed: " + e.getMessage());
    }
}

private void exportRemovedItems() {
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setSelectedFile(new java.io.File("RemovedItems.csv"));
    if (chooser.showSaveDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION) return;

    java.io.File file = chooser.getSelectedFile();
    try (java.io.PrintWriter pw = new java.io.PrintWriter(
            new java.io.FileWriter(file));
         Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM removed_items ORDER BY removedAt DESC")) {

        pw.println("\"Item Name\",\"Type\",\"Department\",\"Quantity\"," +
                   "\"Unit\",\"Description\",\"Removed By\"," +
                   "\"Removed At\",\"Reason\"");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            pw.printf("\"%s\",\"%s\",\"%s\",%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                rs.getString("itemName").replace("\"","\"\""),
                safeStr(rs.getString("itemType")),
                safeStr(rs.getString("department")),
                rs.getInt("quantity"),
                safeStr(rs.getString("unit")),
                safeStr(rs.getString("description")).replace("\"","\"\""),
                safeStr(rs.getString("removedBy")),
                safeStr(rs.getString("removedAt")),
                safeStr(rs.getString("reason")).replace("\"","\"\""));
        }
        javax.swing.JOptionPane.showMessageDialog(this,
            "Exported to: " + file.getAbsolutePath());
    } catch (Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Export failed: " + ex.getMessage());
    }
}

private void loadLowStockTable(javax.swing.table.DefaultTableModel model) {
    model.setRowCount(0);
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
                "SELECT name, type, quantity FROM inventory " +
                "WHERE userId = ? AND quantity < 10 " +
                "ORDER BY quantity ASC")) {
        ps.setInt(1, currentUserId);
        java.sql.ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("name"),
                rs.getString("type"),
                rs.getInt("quantity")
            });
        }
    } catch (Exception e) {
        System.err.println("Low stock load failed: " + e.getMessage());
    }
}
//---
//
//---
private custom_ui.RoundedPanel buildLendingHistoryPanel()
{
     custom_ui.RoundedPanel panel = new custom_ui.RoundedPanel(16);
    panel.setBackground(java.awt.Color.WHITE);
    panel.setLayout(new java.awt.BorderLayout());
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 4, 12));

    // Header
    javax.swing.JLabel title = new javax.swing.JLabel("🕐  Lending Activity");
    title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    title.setForeground(new java.awt.Color(80, 80, 80));
    title.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));
    String[] cols = {"ID", "Item", "Qty", "Borrower",
                     "Reason", "Lent Date", "Return Date", "Returned"};
    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setRowHeight(28);
    table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    table.getTableHeader().setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));

    // Hide ID column
    table.getColumnModel().getColumn(0).setMinWidth(0);
    table.getColumnModel().getColumn(0).setMaxWidth(0);

    // Load lending records
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT id, item_name, quantity, borrower, reason, " +
            "lent_date, return_date, returned FROM lending " +
            "WHERE userId = ? ORDER BY lent_date DESC")) {
        ps.setInt(1, currentUserId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("item_name"),
                rs.getInt("quantity"),
                rs.getString("borrower"),
                rs.getString("reason"),
                rs.getString("lent_date"),
                rs.getString("return_date"),
                rs.getInt("returned") == 1 ? "Yes" : "No"
            });
        }
    } catch (SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Could not load lending records: " + e.getMessage());
    }

    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);
    scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    scroll.getViewport().setBackground(java.awt.Color.WHITE);

    panel.add(title,  java.awt.BorderLayout.NORTH);
    panel.add(scroll, java.awt.BorderLayout.CENTER);
    return panel;
}
// -------------------------------------------------------
//  Recent activity panel
// -------------------------------------------------------
private custom_ui.RoundedPanel buildRecentActivityPanel() {
    custom_ui.RoundedPanel panel = new custom_ui.RoundedPanel(16);
    panel.setBackground(java.awt.Color.WHITE);
    panel.setLayout(new java.awt.BorderLayout());
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // Header
    javax.swing.JLabel title = new javax.swing.JLabel("🕐  Recently Added");
    title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    title.setForeground(new java.awt.Color(80, 80, 80));
    title.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));

    // Table
    String[] cols = {"Item", "Type", "Qty", "Updated"};
    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    table.setRowHeight(28);
    table.getTableHeader().setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
    table.getTableHeader().setBackground(new java.awt.Color(245, 245, 245));
    table.getTableHeader().setForeground(java.awt.Color.GRAY);
    table.setShowVerticalLines(false);
    table.setGridColor(new java.awt.Color(240, 240, 240));
    table.setSelectionBackground(new java.awt.Color(80, 150, 80));

    // Load last 10 added items
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
                "SELECT name, type, quantity, updated_at FROM inventory " +
                "WHERE userId = ? " +
                "ORDER BY updated_at DESC")) {
        ps.setInt(1, currentUserId);
        java.sql.ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            // Format the date to something readable
            java.sql.Timestamp ts = rs.getTimestamp("updated_at");
            String dateStr = ts != null
                    ? new java.text.SimpleDateFormat("dd MMM, HH:mm").format(ts)
                    : "—";
            model.addRow(new Object[]{
                rs.getString("name"),
                rs.getString("type"),
                rs.getInt("quantity"),
                dateStr
            });
        }
    } catch (Exception e) {
        System.err.println("Recent activity load failed: " + e.getMessage());
    }

    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);
    scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    scroll.getViewport().setBackground(java.awt.Color.WHITE);

    panel.add(title,  java.awt.BorderLayout.NORTH);
    panel.add(scroll, java.awt.BorderLayout.CENTER);

    return panel;
}

private javax.swing.JPanel buildPlaceholderCard(String message) {
    javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.BorderLayout());
    card.setOpaque(false);
    javax.swing.JLabel lbl = new javax.swing.JLabel(message,
            javax.swing.SwingConstants.CENTER);
    lbl.setForeground(java.awt.Color.WHITE);
    lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
    card.add(lbl, java.awt.BorderLayout.CENTER);
    return card;
}

private custom_ui.RoundedPanel makeStatCard(String label, String value,
                                             java.awt.Color color) {
    custom_ui.RoundedPanel card = new custom_ui.RoundedPanel(16);
    card.setBackground(java.awt.Color.WHITE);
    card.setLayout(new java.awt.BorderLayout());
    card.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));

    javax.swing.JLabel lbl = new javax.swing.JLabel(label,
            javax.swing.SwingConstants.CENTER);
    lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    lbl.setForeground(java.awt.Color.GRAY);

    javax.swing.JLabel val = new javax.swing.JLabel(value,
            javax.swing.SwingConstants.CENTER);
    val.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
    val.setForeground(color);

    card.add(lbl, java.awt.BorderLayout.NORTH);
    card.add(val, java.awt.BorderLayout.CENTER);
    return card;
}


private String getTotalItems() {
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM inventory WHERE userId = ?")) {
        ps.setInt(1, currentUserId);
        java.sql.ResultSet rs = ps.executeQuery();
        return rs.next() ? String.valueOf(rs.getInt(1)) : "0";
    } catch (Exception e) { return "—"; }
}

private String getLowStock() {
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM inventory WHERE userId = ? AND quantity < 10")) {
        ps.setInt(1, currentUserId);
        java.sql.ResultSet rs = ps.executeQuery();
        return rs.next() ? String.valueOf(rs.getInt(1)) : "0";
    } catch (Exception e) { return "—"; }
}

private String getItemTypes() {
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(DISTINCT type) FROM inventory WHERE userId = ?")) {
        ps.setInt(1, currentUserId);
        java.sql.ResultSet rs = ps.executeQuery();
        return rs.next() ? String.valueOf(rs.getInt(1)) : "0";
    } catch (Exception e) { return "—"; }
}

private String getAddedToday() {
    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM inventory WHERE userId = ? AND DATE(updated_at) = date('now')")) {
        ps.setInt(1, currentUserId);
        java.sql.ResultSet rs = ps.executeQuery();
        return rs.next() ? String.valueOf(rs.getInt(1)) : "0";
    } catch (Exception e) { return "—"; }
}
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Reports button
        cardLayout.show(contentArea, "REPORTS");
        setActiveButton(jButton4);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Settings button
        cardLayout.show(contentArea, "SETTINGS");
        setActiveButton(jButton7);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        cardLayout.show(contentArea, "LENDING");
        setActiveButton(jButton5);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
   
//--------------------------
    private javax.swing.JPanel buildReportsCard() {
    javax.swing.JPanel card = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 12));
    card.setOpaque(false);
    card.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));

    // --- Top: Company settings form ---
    custom_ui.RoundedPanel settingsPanel = new custom_ui.RoundedPanel(12);
    settingsPanel.setBackground(java.awt.Color.WHITE);
    settingsPanel.setLayout(new java.awt.GridLayout(5, 2, 8, 8));
    settingsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
    settingsPanel.setPreferredSize(new java.awt.Dimension(0, 180));

    javax.swing.JTextField companyField  = makeField(CompanySettings.getCompanyName());
    javax.swing.JTextField addressField  = makeField(CompanySettings.getAddress());
    javax.swing.JTextField phoneField    = makeField(CompanySettings.getContactPhone());
    javax.swing.JTextField emailField    = makeField(CompanySettings.getContactEmail());
    javax.swing.JTextField logoField     = makeField(CompanySettings.getLogoPath());

    settingsPanel.add(makeLabel("Company Name:"));  settingsPanel.add(companyField);
    settingsPanel.add(makeLabel("Address:"));        settingsPanel.add(addressField);
    settingsPanel.add(makeLabel("Phone:"));          settingsPanel.add(phoneField);
    settingsPanel.add(makeLabel("Email:"));          settingsPanel.add(emailField);
    settingsPanel.add(makeLabel("Logo path:"));      settingsPanel.add(logoField);

    // --- Middle: Item table (manual + auto) ---
    custom_ui.RoundedPanel tablePanel = new custom_ui.RoundedPanel(12);
    tablePanel.setBackground(java.awt.Color.WHITE);
    tablePanel.setLayout(new java.awt.BorderLayout());
    tablePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // Table header label
    javax.swing.JLabel tableTitle = new javax.swing.JLabel("Purchase Request Items");
    tableTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    tableTitle.setForeground(new java.awt.Color(33, 55, 98));
    tableTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));

    // Invoice items table
    String[] cols = {"Item Name", "Type", "Current Stock", "Qty Requested"};
    javax.swing.table.DefaultTableModel invoiceModel =
            new javax.swing.table.DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) {
            return c == 3; // only qty requested is editable
        }
    };

    javax.swing.JTable invoiceTable = new javax.swing.JTable(invoiceModel);
    invoiceTable.setRowHeight(28);
    invoiceTable.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    invoiceTable.getTableHeader().setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
    invoiceTable.setGridColor(new java.awt.Color(240, 240, 240));
    invoiceTable.setSelectionBackground(new java.awt.Color(80, 150, 80));

    javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(invoiceTable);
    tableScroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());

    // Buttons row
    javax.swing.JPanel tableBtns = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
    tableBtns.setOpaque(false);
    tableBtns.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

    javax.swing.JButton autoBtn = new javax.swing.JButton("Auto-fill Low Stock");
    javax.swing.JButton addBtn  = new javax.swing.JButton("+ Add Item Manually");
    javax.swing.JButton delBtn  = new javax.swing.JButton("Remove Selected");

    styleReportButton(autoBtn, new java.awt.Color(33, 55, 98));
    styleReportButton(addBtn,  new java.awt.Color(80, 160, 100));
    styleReportButton(delBtn,  new java.awt.Color(200, 60, 60));

    // Auto-fill from DB
    autoBtn.addActionListener(e -> {
        invoiceModel.setRowCount(0);
        java.util.List<InvoiceGenerator.InvoiceItem> lowItems =
            InvoiceGenerator.getLowStockItems(currentUserId);
        if (lowItems.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "No items with stock below 10 found.",
                "No Low Stock", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (InvoiceGenerator.InvoiceItem item : lowItems) {
                invoiceModel.addRow(new Object[]{
                    item.name, item.type,
                    item.currentQty, item.requestedQty
                });
            }
        }
    });

    // Manual add
    addBtn.addActionListener(e -> {
        javax.swing.JTextField nameF = new javax.swing.JTextField();
        javax.swing.JTextField typeF = new javax.swing.JTextField();
        javax.swing.JTextField qtyF  = new javax.swing.JTextField("1");
        Object[] fields = {"Item Name:", nameF, "Type:", typeF,
                           "Qty Requested:", qtyF};
        int result = javax.swing.JOptionPane.showConfirmDialog(
                null, fields, "Add Item", javax.swing.JOptionPane.OK_CANCEL_OPTION);
        if (result == javax.swing.JOptionPane.OK_OPTION &&
                !nameF.getText().isBlank()) {
            try {
                invoiceModel.addRow(new Object[]{
                    nameF.getText().toUpperCase(),
                    typeF.getText(),
                    "N/A",
                    Integer.parseInt(qtyF.getText().trim())
                });
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(null,
                    "Quantity must be a number.");
            }
        }
    });

    // Remove selected
    delBtn.addActionListener(e -> {
        int row = invoiceTable.getSelectedRow();
        if (row >= 0) invoiceModel.removeRow(row);
    });

    tableBtns.add(autoBtn);
    tableBtns.add(addBtn);
    tableBtns.add(delBtn);

    tablePanel.add(tableTitle,   java.awt.BorderLayout.NORTH);
    tablePanel.add(tableScroll,  java.awt.BorderLayout.CENTER);
    tablePanel.add(tableBtns,    java.awt.BorderLayout.SOUTH);

    // --- Bottom: Export buttons ---
    javax.swing.JPanel exportPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
    exportPanel.setOpaque(false);

    javax.swing.JButton pdfBtn  = new javax.swing.JButton("Export as PDF");
    javax.swing.JButton docxBtn = new javax.swing.JButton("Export as Word (.docx)");

    styleReportButton(pdfBtn,  new java.awt.Color(180, 40, 40));
    styleReportButton(docxBtn, new java.awt.Color(20, 100, 180));

    pdfBtn.addActionListener(e -> {
        saveSettings(companyField, addressField, phoneField,
                     emailField, logoField);
        if (invoiceModel.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "Add at least one item before exporting.");
            return;
        }
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setSelectedFile(new java.io.File("PurchaseRequest.pdf"));
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "PDF", "pdf"));
        if (chooser.showSaveDialog(null) ==
                javax.swing.JFileChooser.APPROVE_OPTION) {
            java.util.List<InvoiceGenerator.InvoiceItem> items =
                    buildItemsFromTable(invoiceModel);
            boolean ok = InvoiceGenerator.generatePDF(
                    items,
                    chooser.getSelectedFile().getAbsolutePath(),
                    CompanySettings.getLogoPath());
            javax.swing.JOptionPane.showMessageDialog(null,
                ok ? "PDF saved successfully!" : "PDF export failed.",
                ok ? "Success" : "Error",
                ok ? javax.swing.JOptionPane.INFORMATION_MESSAGE
                   : javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    });

    docxBtn.addActionListener(e -> {
        saveSettings(companyField, addressField, phoneField,
                     emailField, logoField);
        if (invoiceModel.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "Add at least one item before exporting.");
            return;
        }
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setSelectedFile(new java.io.File("PurchaseRequest.docx"));
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Word Document", "docx"));
        if (chooser.showSaveDialog(null) ==
                javax.swing.JFileChooser.APPROVE_OPTION) {
            java.util.List<InvoiceGenerator.InvoiceItem> items =
                    buildItemsFromTable(invoiceModel);
            boolean ok = InvoiceGenerator.generateDOCX(
                    items,
                    chooser.getSelectedFile().getAbsolutePath(),
                    CompanySettings.getLogoPath());
            javax.swing.JOptionPane.showMessageDialog(null,
                ok ? "Word document saved successfully!" : "Export failed.",
                ok ? "Success" : "Error",
                ok ? javax.swing.JOptionPane.INFORMATION_MESSAGE
                   : javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    });

    exportPanel.add(pdfBtn);
    exportPanel.add(docxBtn);

    card.add(settingsPanel, java.awt.BorderLayout.NORTH);
    card.add(tablePanel,    java.awt.BorderLayout.CENTER);
    card.add(exportPanel,   java.awt.BorderLayout.SOUTH);

            // At the end of buildReportsCard, replace the card assembly with:

    javax.swing.JPanel inner = new javax.swing.JPanel();
    inner.setOpaque(false);
    inner.setLayout(new javax.swing.BoxLayout(
            inner, javax.swing.BoxLayout.Y_AXIS));
    inner.setBorder(javax.swing.BorderFactory.createEmptyBorder(
            12, 12, 12, 12));

    settingsPanel.setMaximumSize(new java.awt.Dimension(
            Integer.MAX_VALUE, 200));
    tablePanel.setMaximumSize(new java.awt.Dimension(
            Integer.MAX_VALUE, 320));
    exportPanel.setMaximumSize(new java.awt.Dimension(
            Integer.MAX_VALUE, 48));

    inner.add(settingsPanel);
    inner.add(javax.swing.Box.createVerticalStrut(10));
    inner.add(tablePanel);
    inner.add(javax.swing.Box.createVerticalStrut(8));
    inner.add(exportPanel);

    return wrapInScrollCard(inner);

}
private javax.swing.JPanel buildLendingCard() {
    javax.swing.JPanel card = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 8));
    card.setOpaque(false);
    card.setBorder(javax.swing.BorderFactory.createEmptyBorder(
            12, 12, 12, 12));

    // -------------------------------------------------------
    //  Top bar with search and filter
    // -------------------------------------------------------
    javax.swing.JPanel topSection = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 6));
    topSection.setOpaque(false);
    topSection.setBorder(javax.swing.BorderFactory
            .createEmptyBorder(0, 0, 8, 0));

    // Title row
    javax.swing.JPanel topBar = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 0));
    topBar.setOpaque(false);

    javax.swing.JLabel title = new javax.swing.JLabel(
            "Outstanding Lending Records");
    title.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.BOLD, 15));
    title.setForeground(java.awt.Color.WHITE);

    javax.swing.JPanel topBtns = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 0));
    topBtns.setOpaque(false);
    javax.swing.JButton syncBtn    =
            new javax.swing.JButton("Sync from OneDrive");
    javax.swing.JButton historyBtn =
            new javax.swing.JButton("View Finalized");
    styleReportButton(syncBtn,    new java.awt.Color(0, 120, 212));
    styleReportButton(historyBtn, new java.awt.Color(80, 80, 80));
    topBtns.add(syncBtn);
    topBtns.add(historyBtn);
    topBar.add(title,   java.awt.BorderLayout.WEST);
    topBar.add(topBtns, java.awt.BorderLayout.EAST);

    // Search and filter bar
    custom_ui.RoundedPanel searchBar = new custom_ui.RoundedPanel(10);
    searchBar.setBackground(java.awt.Color.WHITE);
    searchBar.setLayout(new java.awt.FlowLayout(
            java.awt.FlowLayout.LEFT, 8, 6));
    searchBar.setPreferredSize(new java.awt.Dimension(0, 46));

    javax.swing.JTextField lendSearchField =
            new javax.swing.JTextField(18);
    lendSearchField.putClientProperty(
            "JTextField.placeholderText", "Search item or borrower...");
    lendSearchField.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 12));

    String[] statusOptions = {"All", "OUT", "PARTIAL", "OVERDUE"};
    javax.swing.JComboBox<String> statusFilter =
            new javax.swing.JComboBox<>(statusOptions);
    statusFilter.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 12));

    javax.swing.JButton clearLendSearch =
            new javax.swing.JButton("Clear");
    styleReportButton(clearLendSearch,
            new java.awt.Color(120, 120, 120));

    searchBar.add(new javax.swing.JLabel("Filter:"));
    searchBar.add(lendSearchField);
    searchBar.add(new javax.swing.JLabel("Status:"));
    searchBar.add(statusFilter);
    searchBar.add(clearLendSearch);

    topSection.add(topBar,    java.awt.BorderLayout.NORTH);
    topSection.add(searchBar, java.awt.BorderLayout.SOUTH);

    // -------------------------------------------------------
    //  Summary strip
    // -------------------------------------------------------
    custom_ui.RoundedPanel strip = new custom_ui.RoundedPanel(10);
    strip.setBackground(java.awt.Color.WHITE);
    strip.setLayout(new java.awt.GridLayout(1, 4, 1, 0));
    strip.setPreferredSize(new java.awt.Dimension(0, 60));
    strip.setBorder(javax.swing.BorderFactory
            .createEmptyBorder(0, 0, 4, 0));

    // Value labels stored so we can update them after load
    javax.swing.JLabel[] chips = {
        makeChipLabel("Total Out",       new java.awt.Color(33,  100, 200)),
        makeChipLabel("Overdue (>14d)",  new java.awt.Color(200, 50,  50)),
        makeChipLabel("Partial Returns", new java.awt.Color(180, 120, 0)),
        makeChipLabel("Items Affected",  new java.awt.Color(80,  80,  80))
    };
    for (javax.swing.JLabel c : chips) strip.add(c.getParent());

    // -------------------------------------------------------
    //  Table
    // -------------------------------------------------------
    String[] cols = {
        "ID", "Item", "Borrower", "Student ID",
        "Taken", "Returned", "Still Out",
        "Sign-Out Date", "Last Return", "Days Out", "Status"
    };

    javax.swing.table.DefaultTableModel model =
        new javax.swing.table.DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setRowHeight(30);
    table.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 12));
    table.getTableHeader().setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.BOLD, 11));
    table.setShowVerticalLines(false);
    table.setGridColor(new java.awt.Color(240, 240, 240));
    table.setSelectionBackground(new java.awt.Color(220, 235, 255));

    // Hide ID column
    table.getColumnModel().getColumn(0).setMinWidth(0);
    table.getColumnModel().getColumn(0).setMaxWidth(0);

    // Status column renderer
    table.getColumnModel().getColumn(10).setCellRenderer(
        new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object val, boolean sel,
                    boolean focus, int row, int col) {
                super.getTableCellRendererComponent(
                        t, val, sel, focus, row, col);
                setHorizontalAlignment(CENTER);
                String s = val == null ? "" : val.toString();
                setOpaque(true);
                switch (s) {
                    case "OVERDUE" -> {
                        setBackground(new java.awt.Color(255, 230, 230));
                        setForeground(new java.awt.Color(180, 30, 30));
                        setFont(getFont().deriveFont(java.awt.Font.BOLD));
                    }
                    case "PARTIAL" -> {
                        setBackground(new java.awt.Color(255, 248, 210));
                        setForeground(new java.awt.Color(160, 100, 0));
                        setFont(getFont().deriveFont(java.awt.Font.BOLD));
                    }
                    default -> {
                        setBackground(new java.awt.Color(232, 248, 235));
                        setForeground(new java.awt.Color(30, 130, 60));
                        setFont(getFont().deriveFont(java.awt.Font.PLAIN));
                    }
                }
                if (sel) setBackground(getBackground().darker());
                return this;
            }
        });

    // Days Out column renderer
    table.getColumnModel().getColumn(9).setCellRenderer(
        new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object val, boolean sel,
                    boolean focus, int row, int col) {
                super.getTableCellRendererComponent(
                        t, val, sel, focus, row, col);
                setHorizontalAlignment(CENTER);
                try {
                    long d = Long.parseLong(val.toString());
                    setForeground(d > 14
                        ? new java.awt.Color(200, 30,  30)
                        : d > 7
                            ? new java.awt.Color(180, 100, 0)
                            : new java.awt.Color(30,  130, 60));
                    setFont(getFont().deriveFont(java.awt.Font.BOLD));
                } catch (Exception ignored) {}
                return this;
            }
        });

    custom_ui.RoundedPanel tableCard = new custom_ui.RoundedPanel(12);
    tableCard.setBackground(java.awt.Color.WHITE);
    tableCard.setLayout(new java.awt.BorderLayout());
    tableCard.setBorder(javax.swing.BorderFactory
            .createEmptyBorder(8, 8, 8, 8));
    tableCard.add(new javax.swing.JScrollPane(table),
            java.awt.BorderLayout.CENTER);

    // -------------------------------------------------------
    //  Load records
    // -------------------------------------------------------
    // Filtered load — call this whenever search/filter changes
    java.util.function.BiConsumer<String, String> doLoad =
            (searchText, statusFilterVal) -> {
        model.setRowCount(0);
        java.util.List<LendingCalculator.OutstandingRecord> records =
                LendingCalculator.calculate(currentUserId);

        int totalOut  = 0;
        int overdue   = 0;
        int partial   = 0;
        java.util.Set<String> items = new java.util.HashSet<>();

        for (LendingCalculator.OutstandingRecord r : records) {
            String status = r.isOverdue    ? "OVERDUE"
                          : r.hasPartialReturn ? "PARTIAL"
                          : "OUT";

            // Apply search filter
            String search = searchText.toLowerCase();
            boolean matchesSearch = search.isBlank()
                || r.itemName.toLowerCase().contains(search)
                || r.borrower.toLowerCase().contains(search)
                || (r.studentId != null &&
                    r.studentId.toLowerCase().contains(search));

            // Apply status filter
            boolean matchesStatus = statusFilterVal.equals("All")
                    || status.equals(statusFilterVal);

            if (!matchesSearch || !matchesStatus) continue;

            totalOut += r.quantityStillOut;
            if (r.isOverdue)        overdue++;
            if (r.hasPartialReturn) partial++;
            items.add(r.itemName.toLowerCase());

            model.addRow(new Object[]{
                r.signoutId,
                r.itemName,
                r.borrower,
                r.studentId == null ? "—" : r.studentId,
                r.quantityOut,
                r.quantityReturned,
                r.quantityStillOut,
                r.signoutDate,
                r.lastReturnDate == null ? "—" : r.lastReturnDate,
                r.daysOut,
                status
            });
        }

        final int fTotal   = totalOut;
        final int fOverdue = overdue;
        final int fPartial = partial;
        final int fItems   = items.size();
        javax.swing.SwingUtilities.invokeLater(() -> {
            chips[0].setText(String.valueOf(fTotal));
            chips[1].setText(String.valueOf(fOverdue));
            chips[2].setText(String.valueOf(fPartial));
            chips[3].setText(String.valueOf(fItems));
        });
    };

    // Default load (no filter)
    Runnable loadRecords = () -> doLoad.accept(
            lendSearchField.getText().trim(),
            statusFilter.getSelectedItem().toString());

    loadRecords.run();

    // Wire search listeners
    lendSearchField.addKeyListener(
            new java.awt.event.KeyAdapter() {
        @Override public void keyReleased(java.awt.event.KeyEvent e) {
            doLoad.accept(
                lendSearchField.getText().trim(),
                statusFilter.getSelectedItem().toString());
        }
    });
    statusFilter.addActionListener(e ->
        doLoad.accept(
            lendSearchField.getText().trim(),
            statusFilter.getSelectedItem().toString()));
    clearLendSearch.addActionListener(e -> {
        lendSearchField.setText("");
        statusFilter.setSelectedIndex(0);
        loadRecords.run();
    });

    loadRecords.run();

    // -------------------------------------------------------
    //  Action buttons
    // -------------------------------------------------------
    javax.swing.JPanel btns = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 4));
    btns.setOpaque(false);

    javax.swing.JButton finalizeBtn =
            new javax.swing.JButton("Finalize Interaction");
    javax.swing.JButton refreshBtn  =
            new javax.swing.JButton("Refresh");
    styleReportButton(finalizeBtn, new java.awt.Color(40, 160, 80));
    styleReportButton(refreshBtn,  new java.awt.Color(80, 80, 80));
    btns.add(finalizeBtn);
    btns.add(refreshBtn);

    // Sync button
    syncBtn.addActionListener(e -> {
        syncBtn.setEnabled(false);
        syncBtn.setText("Syncing...");
        new Thread(() -> {
            boolean ok = OneDriveManager.syncAll(currentUserId);
            javax.swing.SwingUtilities.invokeLater(() -> {
                syncBtn.setEnabled(true);
                syncBtn.setText("Sync from OneDrive");
                if (ok) {
                    loadRecords.run();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this,
                        "Sync failed. Check OneDrive settings.",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        }).start();
    });

    // Refresh button
    refreshBtn.addActionListener(e -> loadRecords.run());

    // Finalize button
    finalizeBtn.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Select a record to finalize.");
            return;
        }

        int    signoutId  = Integer.parseInt(
                model.getValueAt(row, 0).toString());
        String itemName   = model.getValueAt(row, 1).toString();
        String borrower   = model.getValueAt(row, 2).toString();
        int    stillOut   = Integer.parseInt(
                model.getValueAt(row, 6).toString());

        // Build finalize dialog
        javax.swing.JLabel info = new javax.swing.JLabel(
            "<html><b>" + itemName + "</b> — borrowed by " +
            borrower + "<br>Still outstanding: <b>" +
            stillOut + " unit(s)</b></html>");

        javax.swing.JTextField retField =
                new javax.swing.JTextField(
                        String.valueOf(stillOut));
        javax.swing.JTextField lostField =
                new javax.swing.JTextField("0");

        String[] reasonOptions = {
            "All items returned",
            "Item damaged beyond use",
            "Item lost by student",
            "Item retained for further use",
            "Write-off approved by supervisor",
            "Other"
        };
        javax.swing.JComboBox<String> reasonBox =
                new javax.swing.JComboBox<>(reasonOptions);
        javax.swing.JTextField reasonExtra =
                new javax.swing.JTextField();

        Object[] fields = {
            info,
            " ",
            "Quantity being returned now:", retField,
            "Quantity lost/unaccounted:", lostField,
            "Reason for closing this interaction:", reasonBox,
            "Additional notes:", reasonExtra
        };

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this, fields,
            "Finalize Lending Interaction",
            javax.swing.JOptionPane.OK_CANCEL_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (confirm != javax.swing.JOptionPane.OK_OPTION) return;

        try {
            int retQty  = Integer.parseInt(
                    retField.getText().trim());
            int lostQty = Integer.parseInt(
                    lostField.getText().trim());

            if (retQty + lostQty > stillOut) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Returned + Lost cannot exceed " +
                    stillOut + " (still out).");
                return;
            }

            String fullReason = reasonBox.getSelectedItem().toString();
            if (!reasonExtra.getText().isBlank())
                fullReason += " — " + reasonExtra.getText().trim();

            boolean ok = LendingCalculator.finalize(
                signoutId, currentUserId,
                retQty, lostQty, fullReason);

            if (ok) {
                loadRecords.run();
                loadTable();
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Interaction finalized.\n" +
                    retQty + " unit(s) returned to inventory.\n" +
                    lostQty + " unit(s) marked as lost/unaccounted.");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Finalization failed. Check console.");
            }
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Enter valid numbers for returned and lost quantities.");
        }
    });

    // View finalized history
    historyBtn.addActionListener(e -> showFinalizedDialog());

    // -------------------------------------------------------
    //  Assemble
    // -------------------------------------------------------
    card.add(topSection,   java.awt.BorderLayout.NORTH);
    card.add(strip,        java.awt.BorderLayout.NORTH); // this conflicts

     javax.swing.JPanel northWrapper = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 8));
    northWrapper.setOpaque(false);
    northWrapper.add(topSection, java.awt.BorderLayout.NORTH);
    northWrapper.add(strip,      java.awt.BorderLayout.SOUTH);

    javax.swing.JPanel inner = new javax.swing.JPanel(
            new java.awt.BorderLayout(0, 8));
    inner.setOpaque(false);
    inner.setBorder(javax.swing.BorderFactory.createEmptyBorder(
            12, 12, 12, 12));
    inner.add(northWrapper, java.awt.BorderLayout.NORTH);
    inner.add(tableCard,    java.awt.BorderLayout.CENTER);
    inner.add(btns,         java.awt.BorderLayout.SOUTH);

    return wrapInScrollCard(inner);
}

// -------------------------------------------------------
//  Chip label helper — returns the value JLabel
//  its parent JPanel is what gets added to the strip
// -------------------------------------------------------
private javax.swing.JLabel makeChipLabel(String label,
        java.awt.Color color) {
    javax.swing.JPanel chip = new javax.swing.JPanel(
            new java.awt.BorderLayout());
    chip.setBackground(java.awt.Color.WHITE);
    chip.setBorder(javax.swing.BorderFactory.createMatteBorder(
            0, 0, 0, 1, new java.awt.Color(235, 235, 235)));

    javax.swing.JLabel val = new javax.swing.JLabel(
            "—", javax.swing.SwingConstants.CENTER);
    val.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.BOLD, 22));
    val.setForeground(color);

    javax.swing.JLabel lbl = new javax.swing.JLabel(
            label, javax.swing.SwingConstants.CENTER);
    lbl.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 10));
    lbl.setForeground(java.awt.Color.GRAY);

    chip.add(val, java.awt.BorderLayout.CENTER);
    chip.add(lbl, java.awt.BorderLayout.SOUTH);
    return val; // caller uses val.getParent() to add chip to panel
}

// -------------------------------------------------------
//  Finalized history dialog
// -------------------------------------------------------
private void showFinalizedDialog() {
    javax.swing.JDialog dialog = new javax.swing.JDialog(
            this, "Finalized Lending History", true);
    dialog.setSize(920, 480);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new java.awt.BorderLayout());

    String[] cols = {
        "Item", "Borrower", "Student ID",
        "Taken", "Returned", "Lost",
        "Sign-Out Date", "Finalized Date", "Reason"
    };

    javax.swing.table.DefaultTableModel model =
        new javax.swing.table.DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

    try (Connection con = DatabaseConfig.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM finalized_lending " +
            "WHERE userId = ? " +
            "ORDER BY finalizedDate DESC")) {
        ps.setInt(1, currentUserId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("itemName"),
                rs.getString("borrower"),
                rs.getString("studentId"),
                rs.getInt("quantityOut"),
                rs.getInt("quantityReturned"),
                rs.getInt("quantityLost"),
                rs.getString("signoutDate"),
                rs.getString("finalizedDate"),
                rs.getString("finalizationReason")
            });
        }
    } catch (SQLException e) {
        System.err.println("Finalized history load failed: "
                + e.getMessage());
    }

    javax.swing.JTable table = new javax.swing.JTable(model);
    table.setRowHeight(28);
    table.setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.PLAIN, 12));
    table.getTableHeader().setFont(new java.awt.Font(
            "Segoe UI", java.awt.Font.BOLD, 11));
    table.setShowVerticalLines(false);
    table.setGridColor(new java.awt.Color(240, 240, 240));
    table.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

    // Colour Lost column
    table.getColumnModel().getColumn(5).setCellRenderer(
        new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object val, boolean sel,
                    boolean focus, int row, int col) {
                super.getTableCellRendererComponent(
                        t, val, sel, focus, row, col);
                setHorizontalAlignment(CENTER);
                try {
                    int lost = Integer.parseInt(val.toString());
                    setForeground(lost > 0
                        ? new java.awt.Color(200, 30, 30)
                        : new java.awt.Color(30, 130, 60));
                    if (lost > 0)
                        setFont(getFont().deriveFont(
                                java.awt.Font.BOLD));
                } catch (Exception ignored) {}
                return this;
            }
        });

    dialog.add(new javax.swing.JScrollPane(table),
            java.awt.BorderLayout.CENTER);
    dialog.setVisible(true);
}
// -------------------------------------------------------
//  Helper methods — add these alongside buildReportsCard
// -------------------------------------------------------
private javax.swing.JLabel makeLabel(String text) {
    javax.swing.JLabel lbl = new javax.swing.JLabel(text);
    lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    return lbl;
}

private javax.swing.JTextField makeField(String value) {
    javax.swing.JTextField f = new javax.swing.JTextField(value);
    f.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    return f;
}

private void styleReportButton(javax.swing.JButton btn,
                                java.awt.Color color) {
    btn.setBackground(color);
    btn.setForeground(java.awt.Color.WHITE);
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 14, 8, 14));
}

private void saveSettings(javax.swing.JTextField company,
                           javax.swing.JTextField address,
                           javax.swing.JTextField phone,
                           javax.swing.JTextField email,
                           javax.swing.JTextField logo) {
    CompanySettings.setCompanyName(company.getText().trim());
    CompanySettings.setAddress(address.getText().trim());
    CompanySettings.setContactPhone(phone.getText().trim());
    CompanySettings.setContactEmail(email.getText().trim());
    CompanySettings.setLogoPath(logo.getText().trim());
}

private java.util.List<InvoiceGenerator.InvoiceItem>
        buildItemsFromTable(javax.swing.table.DefaultTableModel model) {
    java.util.List<InvoiceGenerator.InvoiceItem> items = new java.util.ArrayList<>();
    for (int i = 0; i < model.getRowCount(); i++) {
        String name  = model.getValueAt(i, 0).toString();
        String type  = model.getValueAt(i, 1).toString();
        int current  = 0;
        int requested = 0;
        try {
            Object curObj = model.getValueAt(i, 2);
            if (!curObj.toString().equals("N/A"))
                current = Integer.parseInt(curObj.toString());
            requested = Integer.parseInt(model.getValueAt(i, 3).toString());
        } catch (NumberFormatException ignored) {}
        items.add(new InvoiceGenerator.InvoiceItem(
                name, type, current, requested));
    }
    return items;
}
        
        // -------------------------------------------------------
//  CSV Export — writes current inventory to a CSV file
// -------------------------------------------------------
private void exportInventoryCSV() {
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setSelectedFile(new java.io.File("inventory_export.csv"));
    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "CSV Files", "csv"));
    if (chooser.showSaveDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION)
        return;

    java.io.File file = chooser.getSelectedFile();
    if (!file.getName().endsWith(".csv"))
        file = new java.io.File(file.getAbsolutePath() + ".csv");

    try (java.io.PrintWriter pw = new java.io.PrintWriter(
            new java.io.FileWriter(file))) {
        pw.println("Name,Type,Quantity,department,description");
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT name, type, quantity,department,description FROM inventory " +
                "WHERE userId = ? ORDER BY name")) {
            ps.setInt(1, currentUserId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pw.printf("\"%s\",\"%s\",\"%s\",\"%s\",%d%n",
                    rs.getString("name").replace("\"", "\"\""),
                    rs.getString("type").replace("\"", "\"\""),
                    rs.getInt("quantity"),
                    rs.getString("department").replace("\"", "\"\""),
                    rs.getString("description").replace("\"", "\"\"")
                    
                    );
            }
        }
        javax.swing.JOptionPane.showMessageDialog(this,
            "Exported to: " + file.getAbsolutePath());
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Export failed: " + e.getMessage(), "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
private void exportEquipmentList() {
    // Ask format
    String[] formats = {"Excel (.xlsx)", "CSV (.csv)"};
    int fmt = javax.swing.JOptionPane.showOptionDialog(this,
        "Choose export format:", "Export Inventory",
        javax.swing.JOptionPane.DEFAULT_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE,
        null, formats, formats[0]);
    if (fmt < 0) return;

    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    String ext = fmt == 0 ? ".xlsx" : ".csv";
    chooser.setSelectedFile(new java.io.File("InventoryExport" + ext));
    if (chooser.showSaveDialog(this) !=
            javax.swing.JFileChooser.APPROVE_OPTION) return;

    java.io.File file = chooser.getSelectedFile();
    if (!file.getName().endsWith(ext))
        file = new java.io.File(file.getAbsolutePath() + ext);

    final java.io.File finalFile = file;
    final int finalFmt = fmt;

    new Thread(() -> {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT name, abbreviation, description, quantity, " +
                "quantityUnit, department, type, remarks " +
                "FROM inventory WHERE userId = ? " +
                "ORDER BY department ASC, name ASC")) {
            ps.setInt(1, currentUserId);
            ResultSet rs = ps.executeQuery();

            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                String unit = rs.getString("quantityUnit");
                int qty = rs.getInt("quantity");
                // Combine quantity + unit into one cell like the original
                String qtyDisplay = qty + (unit == null || unit.isBlank()
                        ? "" : " " + unit);
                rows.add(new Object[]{
                    rs.getString("name"),
                    rs.getString("abbreviation"),
                    rs.getString("description"),
                    qtyDisplay,
                    rs.getString("department"),
                    rs.getString("type"),
                    rs.getString("remarks")
                });
            }

            String[] headers = {"Item", "Abbreviation", "Description",
                                 "Quantity", "Department", "Type", "Remarks"};

            if (finalFmt == 0) {
                // XLSX using Apache POI
                org.apache.poi.xssf.usermodel.XSSFWorkbook wb =
                    new org.apache.poi.xssf.usermodel.XSSFWorkbook();
                org.apache.poi.ss.usermodel.Sheet sheet =
                    wb.createSheet("Inventory");

                // Header row — bold
                org.apache.poi.ss.usermodel.Row headerRow =
                    sheet.createRow(0);
                org.apache.poi.ss.usermodel.CellStyle boldStyle =
                    wb.createCellStyle();
                org.apache.poi.ss.usermodel.Font boldFont =
                    wb.createFont();
                boldFont.setBold(true);
                boldStyle.setFont(boldFont);

                for (int c = 0; c < headers.length; c++) {
                    org.apache.poi.ss.usermodel.Cell cell =
                        headerRow.createCell(c);
                    cell.setCellValue(headers[c]);
                    cell.setCellStyle(boldStyle);
                }

                // Group by department — write a section header row
                String lastDept = null;
                int rowNum = 1;

                for (Object[] row : rows) {
                    String dept = row[4] == null ? "" :
                            row[4].toString();
                    if (!dept.equals(lastDept)) {
                        // Section header
                        org.apache.poi.ss.usermodel.Row deptRow =
                            sheet.createRow(rowNum++);
                        org.apache.poi.ss.usermodel.Cell dc =
                            deptRow.createCell(0);
                        dc.setCellValue(dept.toUpperCase());
                        dc.setCellStyle(boldStyle);
                        lastDept = dept;
                    }
                    org.apache.poi.ss.usermodel.Row dataRow =
                        sheet.createRow(rowNum++);
                    dataRow.createCell(0).setCellValue(
                            safeStr(row[0]));
                    dataRow.createCell(1).setCellValue(
                            safeStr(row[1]));
                    dataRow.createCell(2).setCellValue(
                            safeStr(row[2]));
                    dataRow.createCell(3).setCellValue(
                            safeStr(row[3]));
                    dataRow.createCell(4).setCellValue(dept);
                    dataRow.createCell(5).setCellValue(
                            safeStr(row[5]));
                    dataRow.createCell(6).setCellValue(
                            safeStr(row[6]));
                }

                // Auto-size columns
                for (int c = 0; c < headers.length; c++)
                    sheet.autoSizeColumn(c);

                try (java.io.FileOutputStream fos =
                        new java.io.FileOutputStream(finalFile)) {
                    wb.write(fos);
                }
                wb.close();

            } else {
                // CSV
                try (java.io.PrintWriter pw = new java.io.PrintWriter(
                        new java.io.FileWriter(finalFile))) {
                    pw.println(String.join(",",
                        java.util.Arrays.stream(headers)
                            .map(h -> "\"" + h + "\"")
                            .toArray(String[]::new)));
                    String lastDept = null;
                    for (Object[] row : rows) {
                        String dept = row[4] == null ? "" :
                                row[4].toString();
                        if (!dept.equals(lastDept)) {
                            pw.println("\"" + dept.toUpperCase() +
                                    "\",\"\",\"\",\"\",\"\",\"\",\"\"");
                            lastDept = dept;
                        }
                        pw.println(
                            "\"" + safeStr(row[0]).replace("\"","\"\"") + "\"," +
                            "\"" + safeStr(row[1]).replace("\"","\"\"") + "\"," +
                            "\"" + safeStr(row[2]).replace("\"","\"\"") + "\"," +
                            "\"" + safeStr(row[3]).replace("\"","\"\"") + "\"," +
                            "\"" + dept.replace("\"","\"\"") + "\"," +
                            "\"" + safeStr(row[5]).replace("\"","\"\"") + "\"," +
                            "\"" + safeStr(row[6]).replace("\"","\"\"") + "\""
                        );
                    }
                }
            }

            javax.swing.SwingUtilities.invokeLater(() ->
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Exported to: " + finalFile.getAbsolutePath()));

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.SwingUtilities.invokeLater(() ->
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Export failed: " + ex.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE));
        }
    }).start();
}

private String safeStr(Object o) {
    return o == null ? "" : o.toString().trim();
}
// -------------------------------------------------------
//  CSV Import — reads a CSV and upserts into inventory
// -------------------------------------------------------
private void importInventoryCSV() {
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "CSV Files", "csv"));
    if (chooser.showOpenDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION)
        return;

    java.io.File file = chooser.getSelectedFile();
    int imported = 0, updated = 0, skipped = 0;

    try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.FileReader(file));
         Connection con = DatabaseConfig.getConnection()) {

        con.setAutoCommit(false);
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            if (firstLine) { firstLine = false; continue; } // skip header
            if (line.isBlank()) continue;

            String[] parts = parseCSVLine(line);
            if (parts.length < 3) { skipped++; continue; }

            String name = parts[0].trim();
            String type = parts[1].trim();
            int qty;
            try {
                qty = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                skipped++;
                continue;
            }

            // Check if exists
            PreparedStatement check = con.prepareStatement(
                "SELECT itemId, quantity FROM inventory " +
                "WHERE LOWER(name) = LOWER(?) AND userId = ?");
            check.setString(1, name);
            check.setInt(2, currentUserId);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                // Update existing
                int newQty = rs.getInt("quantity") + qty;
                PreparedStatement upd = con.prepareStatement(
                    "UPDATE inventory SET quantity = ? WHERE itemId = ?");
                upd.setInt(1, newQty);
                upd.setInt(2, rs.getInt("itemId"));
                upd.executeUpdate();
                updated++;
            } else {
                // Insert new
                PreparedStatement ins = con.prepareStatement(
                    "INSERT INTO inventory (name, type, quantity, userId) " +
                    "VALUES (?,?,?,?)");
                ins.setString(1, name);
                ins.setString(2, type);
                ins.setInt(3, qty);
                ins.setInt(4, currentUserId);
                ins.executeUpdate();
                imported++;
            }
        }

        con.commit();
        javax.swing.JOptionPane.showMessageDialog(this,
            String.format("Import complete.\n" +
                "New items: %d\nUpdated: %d\nSkipped: %d",
                imported, updated, skipped));
        loadTable();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Import failed: " + e.getMessage(), "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void importEquipmentListCSV() {
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setDialogTitle("Select Equipment List");
    chooser.setFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter(
            "Excel / CSV", "xlsx", "csv"));
    if (chooser.showOpenDialog(this) !=
            javax.swing.JFileChooser.APPROVE_OPTION) return;

    java.io.File file = chooser.getSelectedFile();

    // Dept selection
    String[] depts = {
        "Electrical Power and Machines",
        "Electronics and Control",
        "Networking and Telecom",
        "Multimeter Allocation",
        "General"
    };
    String dept = (String) javax.swing.JOptionPane.showInputDialog(
        this, "Which department does this file belong to?",
        "Select Department",
        javax.swing.JOptionPane.QUESTION_MESSAGE,
        null, depts, depts[0]);
    if (dept == null) return;

    // Overwrite or add?
    String[] modes = {"Add to existing quantities", "Overwrite quantities"};
    int modeChoice = javax.swing.JOptionPane.showOptionDialog(this,
        "How should existing items be handled?",
        "Import Mode",
        javax.swing.JOptionPane.DEFAULT_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE,
        null, modes, modes[0]);
    if (modeChoice < 0) return;
    boolean overwrite = modeChoice == 1;

    // First confirmation
    int c1 = javax.swing.JOptionPane.showConfirmDialog(this,
        "Import from:\n" + file.getName() +
        "\nDepartment: " + dept +
        "\nMode: " + modes[modeChoice] + "\n\nProceed?",
        "Confirm Import",
        javax.swing.JOptionPane.YES_NO_OPTION);
    if (c1 != javax.swing.JOptionPane.YES_OPTION) return;

    // Second confirmation for overwrite mode
    if (overwrite) {
        int c2 = javax.swing.JOptionPane.showConfirmDialog(this,
            "OVERWRITE mode will replace existing quantities.\n" +
            "This cannot be undone. Are you sure?",
            "Final Confirmation — Overwrite",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);
        if (c2 != javax.swing.JOptionPane.YES_OPTION) return;
    }

    final String finalDept = dept;
    final boolean finalOverwrite = overwrite;

    new Thread(() -> {
        int[] counts = {0, 0, 0}; // inserted, updated, skipped
        try {
            java.util.List<String[]> rows = readEquipmentFile(file);
            if (rows.isEmpty()) {
                javax.swing.SwingUtilities.invokeLater(() ->
                    javax.swing.JOptionPane.showMessageDialog(this,
                        "No data found in file."));
                return;
            }

            String[] header = rows.get(0);
            int nameCol  = findHeaderCol(header,
                    "Item", "Equipment Name", "Name");
            int descCol  = findHeaderCol(header,
                    "Description", "Desc");
            int qtyCol   = findHeaderCol(header,
                    "Quantity", "Qty");
            int abbrCol  = findHeaderCol(header,
                    "Abbreviation", "Abbr");
            int remCol   = findHeaderCol(header,
                    "Remarks", "Remark", "Status");

            if (nameCol < 0) {
                javax.swing.SwingUtilities.invokeLater(() ->
                    javax.swing.JOptionPane.showMessageDialog(this,
                        "Could not find Item/Name column.\n" +
                        "Headers found: " +
                        java.util.Arrays.toString(header)));
                return;
            }

            try (Connection con = DatabaseConfig.getConnection()) {
                con.setAutoCommit(false);

                for (int i = 1; i < rows.size(); i++) {
                    String[] row = rows.get(i);
                    if (row.length <= nameCol) continue;

                    String name = safeCell(row, nameCol);
                    if (name.isBlank()) {
                        name = safeCell(row, descCol);
                        if (name.isBlank()) continue;
                    }

                    String desc  = safeCell(row, descCol);
                    String abbr  = safeCell(row, abbrCol);
                    String rem   = safeCell(row, remCol);
                    String rawQty = safeCell(row, qtyCol);

                    int qty = 0;
                    String unit = "pieces";
                    if (!rawQty.isBlank()) {
                        String numPart = rawQty.replaceAll(
                                "[^0-9.]", "").trim();
                        String unitPart = rawQty.replaceAll(
                                "[0-9.\\s]", "").trim().toLowerCase();
                        try { qty = (int) Double.parseDouble(numPart); }
                        catch (Exception ignored) {}
                        if (!unitPart.isBlank()) unit = unitPart;
                    }

                    PreparedStatement check = con.prepareStatement(
                        "SELECT itemId, quantity FROM inventory " +
                        "WHERE LOWER(name) = LOWER(?) AND userId = ?");
                    check.setString(1, name);
                    check.setInt(2, currentUserId);
                    ResultSet rs = check.executeQuery();

                    if (rs.next()) {
                        int existingId  = rs.getInt("itemId");
                        int existingQty = rs.getInt("quantity");
                        int newQty = finalOverwrite
                                ? qty
                                : existingQty + qty;

                        PreparedStatement upd = con.prepareStatement(
                            "UPDATE inventory SET " +
                            "quantity=?, description=?, department=?, " +
                            "quantityUnit=?, abbreviation=?, remarks=? " +
                            "WHERE itemId=?");
                        upd.setInt(1, newQty);
                        upd.setString(2, desc);
                        upd.setString(3, finalDept);
                        upd.setString(4, unit);
                        upd.setString(5, abbr);
                        upd.setString(6, rem);
                        upd.setInt(7, existingId);
                        upd.executeUpdate();
                        counts[1]++;
                    } else {
                        PreparedStatement ins = con.prepareStatement(
                            "INSERT INTO inventory " +
                            "(name, type, quantity, description, " +
                            " department, quantityUnit, abbreviation, " +
                            " remarks, userId) " +
                            "VALUES (?,?,?,?,?,?,?,?,?)");
                        ins.setString(1, name.toUpperCase());
                        ins.setString(2, "Equipment");
                        ins.setInt(3, qty);
                        ins.setString(4, desc);
                        ins.setString(5, finalDept);
                        ins.setString(6, unit);
                        ins.setString(7, abbr);
                        ins.setString(8, rem);
                        ins.setInt(9, currentUserId);
                        ins.executeUpdate();
                        counts[0]++;
                    }
                }
                con.commit();
            }

            javax.swing.SwingUtilities.invokeLater(() -> {
                loadTable();
                // Trigger inventory table refresh
                if (searchField != null)
                    searchField.getKeyListeners()[0].keyReleased(
                        new java.awt.event.KeyEvent(searchField,
                            java.awt.event.KeyEvent.KEY_RELEASED,
                            System.currentTimeMillis(), 0,
                            java.awt.event.KeyEvent.VK_UNDEFINED, ' '));
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Import complete:\n" +
                    counts[0] + " new items added\n" +
                    counts[1] + " items updated\n" +
                    counts[2] + " rows skipped");
                if (searchField != null)
                    searchField.requestFocusInWindow();
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.SwingUtilities.invokeLater(() ->
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Import failed: " + ex.getMessage(), "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE));
        }
    }).start();
}

// Read either xlsx or csv into a list of string arrays
private java.util.List<String[]> readEquipmentFile(
        java.io.File file) throws Exception {
    java.util.List<String[]> rows = new java.util.ArrayList<>();

    if (file.getName().toLowerCase().endsWith(".xlsx")) {
        // Use Apache POI — already in your project
        try (org.apache.poi.xssf.usermodel.XSSFWorkbook wb =
                new org.apache.poi.xssf.usermodel.XSSFWorkbook(
                        new java.io.FileInputStream(file))) {
            org.apache.poi.ss.usermodel.Sheet sheet =
                    wb.getSheetAt(0);
            for (org.apache.poi.ss.usermodel.Row row : sheet) {
                int lastCell = row.getLastCellNum();
                String[] cells = new String[lastCell];
                for (int c = 0; c < lastCell; c++) {
                    org.apache.poi.ss.usermodel.Cell cell =
                            row.getCell(c);
                    cells[c] = cell == null ? ""
                        : cell.toString().trim();
                }
                rows.add(cells);
            }
        }
    } else {
        // CSV fallback
        try (java.io.BufferedReader br =
                new java.io.BufferedReader(
                        new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(parseCSVLine(line));
            }
        }
    }
    return rows;
}

// Find column index by trying multiple header name variants
private int findHeaderCol(String[] headers, String... candidates) {
    for (String candidate : candidates) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i] != null &&
                    headers[i].trim()
                    .equalsIgnoreCase(candidate)) return i;
        }
    }
    return -1;
}

private String safeCell(String[] row, int col) {
    if (col < 0 || col >= row.length) return "";
    return row[col] == null ? "" : row[col].trim();
}
// Handles quoted CSV fields correctly
private String[] parseCSVLine(String line) {
    java.util.List<String> tokens = new java.util.ArrayList<>();
    StringBuilder sb = new StringBuilder();
    boolean inQuotes = false;
    for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (c == '"') {
            if (inQuotes && i + 1 < line.length()
                    && line.charAt(i + 1) == '"') {
                sb.append('"'); i++;
            } else {
                inQuotes = !inQuotes;
            }
        } else if (c == ',' && !inQuotes) {
            tokens.add(sb.toString()); sb.setLength(0);
        } else {
            sb.append(c);
        }
    }
    tokens.add(sb.toString());
    return tokens.toArray(new String[0]);
}
// -------------------------------------------------------
//  Universal card wrapper — every build card calls this
//  Content scrolls vertically, never overflows the window
// -------------------------------------------------------
private static class ScrollableHost extends javax.swing.JPanel
        implements javax.swing.Scrollable {
    ScrollableHost(javax.swing.JComponent content) {
        super(new java.awt.BorderLayout());
        setOpaque(false);
        add(content, java.awt.BorderLayout.CENTER);
    }
    @Override
    public java.awt.Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }
    @Override
    public int getScrollableUnitIncrement(
            java.awt.Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }
    @Override
    public int getScrollableBlockIncrement(
            java.awt.Rectangle visibleRect, int orientation, int direction) {
        return 100;
    }
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;   // never clip/scroll sideways — resize with the window
    }
    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;  // do scroll vertically when content is taller
    }
}
private javax.swing.JPanel wrapInScrollCard(
        javax.swing.JComponent content) {
    javax.swing.JPanel card = new javax.swing.JPanel(
            new java.awt.BorderLayout());
    card.setOpaque(false);
 
    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(
            new ScrollableHost(content),
            javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.getVerticalScrollBar().setUnitIncrement(16);
 
    card.add(scroll, java.awt.BorderLayout.CENTER);
    return card;
}
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
            java.util.logging.Logger.getLogger(staff_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(staff_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(staff_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(staff_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //int userId = -1;
                //new staff_dashboard(userId).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private custom_ui.RoundedPanel roundedPanel1;
    private custom_ui.RoundedPanel roundedPanel11;
    private custom_ui.RoundedPanel roundedPanel2;
    private custom_ui.RoundedPanel roundedPanel3;
    // End of variables declaration//GEN-END:variables
}
