/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventorymanagementsystemv_01;

import custom_ui.CustomTitleBar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Ugochukwu Nwodo
 */
public class login extends javax.swing.JFrame {
    private CustomTitleBar titleBar;
    private int currentUserId = -1;
    private String currentUserRole = null;  
    //new login form
    public login() {
        setUndecorated(true);
    initComponents();

    javax.swing.JPanel wrapper = new javax.swing.JPanel(new java.awt.BorderLayout());
    wrapper.setOpaque(true);
    wrapper.setBackground(new java.awt.Color(0, 0, 0, 0)); 

    titleBar = new CustomTitleBar(this);
    titleBar.setCloseAction(CustomTitleBar.CloseAction.EXIT);
    titleBar.setShowMinimize(true);
    titleBar.setShowClose(true);
    titleBar.setCornerRadius(20);
    titleBar.setBarColor(new java.awt.Color(45,59,111));
    titleBar.setCloseNormalColor(new java.awt.Color(45,59,111));
    titleBar.setMinimizeNormalColor(new java.awt.Color(45,59,111));
    titleBar.setCloseHoverColor(new java.awt.Color(255, 0, 0));
    titleBar.setTitleText("Login");

    wrapper.add(titleBar, java.awt.BorderLayout.NORTH);
    wrapper.add(roundedPanel1, java.awt.BorderLayout.CENTER);
    getRootPane().setOpaque(false);
    getRootPane().setBackground(new java.awt.Color(0, 0, 0, 0));

    setContentPane(wrapper);
    setSize(1200, 750);
    setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new custom_ui.RoundedPanel();
        roundedPanel2 = new custom_ui.RoundedPanel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roundedPanel3 = new custom_ui.RoundedPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roundedPanel1.setBackground(new java.awt.Color(45, 59, 111));
        roundedPanel1.setForeground(new java.awt.Color(45, 59, 111));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(1200, 750));

        roundedPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundedPanel2.setForeground(new java.awt.Color(255, 255, 255));
        roundedPanel2.setMaximumSize(new java.awt.Dimension(561, 465));

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Register");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Email");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Password");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("or");

        roundedPanel3.setBackground(new java.awt.Color(45, 59, 111));
        roundedPanel3.setForeground(new java.awt.Color(255, 255, 255));
        roundedPanel3.setCornerRadius(5);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pan-Atlantic University");

        javax.swing.GroupLayout roundedPanel3Layout = new javax.swing.GroupLayout(roundedPanel3);
        roundedPanel3.setLayout(roundedPanel3Layout);
        roundedPanel3Layout.setHorizontalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(168, 168, 168))
        );
        roundedPanel3Layout.setVerticalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel2Layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jLabel3)))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(14, 14, 14)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(338, Short.MAX_VALUE)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(295, 295, 295))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private String hashPassword(String password)
    {
	    try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "Error hashing password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; 
        }
    }       
    private boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    } 
    
    private int authenticateUser(String email, String password){
            Connection con = null;
            PreparedStatement ps = null;
            java.sql.ResultSet rs = null;
            try{
                String hashedPassword = hashPassword(password);
                if (hashedPassword == null){
                        return -1;
                }
                //Class.forName("com.mysql.cj.jdbc.Driver");
                con = DatabaseConfig.getConnection();//DriverManager.getConnection("jdbc:mysql://localhost/sys", "root", "Vitamin101##");
                
                System.out.println("Connection established. Default autocommit status: " + con.getAutoCommit());
                con.setAutoCommit(false);
                System.out.println("Autocommit status AFTER setAutoCommit(false): " + con.getAutoCommit());
                String confirmUserCredentials = "SELECT userid, role FROM users WHERE emailAddress = ? AND password = ?";
                ps = con.prepareStatement(confirmUserCredentials);
                ps.setString(1, email.trim());
                ps.setString(2, hashedPassword); 
                rs = ps.executeQuery();

                if (rs.next())
                {
                    this.currentUserId = rs.getInt("userid");
                    this.currentUserRole = rs.getString("role");
                    MicrosoftGraphConfig.load(currentUserId);

                    // Auto-refresh token if one is saved
                    if (MicrosoftGraphConfig.isAuthenticated() &&
                            MicrosoftGraphConfig.accessToken.isBlank()) {
                        new Thread(() ->
                            GraphAuthManager.refreshAccessToken(currentUserId)
                        ).start();
                    }
                    
                    return rs.getInt("userid");
                }else
                {
                    return -1;
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
            }//catch (ClassNotFoundException ex) {
               // Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            //} 
            finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {

                }
            }
            return -1;
	}
	private void activateDashBoard(String role, int userId)
	{
            
        //try {
            
            switch (role) {
                case "Staff":
                    new staff_dashboard(userId).setVisible(true);
                    this.setVisible(false);
                    
                    break;
                //case "Supervisor":
                    //new supervisor_frame(userId).setVisible(true);
                  //  break;
                case "Admin":
                    new staff_dashboard(userId).setVisible(true);
                    this.setVisible(false);
                    //break;
                //case "Director":
                    //new dos_ithead_frame(userId).setVisible(true);
                  //  break;
                
                return;
            }
            this.setVisible(false);
        //} //catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Error opening dashboard: " + e.getMessage(),
                   // "Navigation Error", JOptionPane.ERROR_MESSAGE);
        //}
	}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String email = jTextField1.getText().trim();
        String password = new String(jPasswordField1.getPassword());
        
        if(email.isEmpty())
        {
            JOptionPane.showMessageDialog(this,"Email must not be empty","Invalid Email!",JOptionPane.ERROR_MESSAGE);
            jTextField1.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
		    JOptionPane.showMessageDialog(this,"Password Field must not be empty","Invalid Password!", JOptionPane.ERROR_MESSAGE);
		    jPasswordField1.requestFocus();
		    return;
        }
	    if(!isValidEmail(email))
	    {
		    JOptionPane.showMessageDialog(this,"Please enter a valid email address! ","Validation Error",JOptionPane.ERROR_MESSAGE);
		    jTextField1.requestFocus();
		    return;
	    }
		int userId = authenticateUser(email,password);
		if(userId != -1 & currentUserRole != null)
		{
			JOptionPane.showMessageDialog(this,"Login Successful","Login Successful!",JOptionPane.INFORMATION_MESSAGE );
			activateDashBoard(currentUserRole,userId);
			//TextField1.setText("");
			//jPasswordField1.setText("");
		}else
		{
			JOptionPane.showMessageDialog(this,"Failed to Login","Login Failed!", JOptionPane.ERROR_MESSAGE);
			jTextField1.setText("");
			jPasswordField1.setText("");
		}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new registration().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

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
                java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
        DatabaseConfig.initialise();
        boolean opencvReady = ItemCounter.loadOpenCV();
        
        GeminiConfig.load();
        
        if (!opencvReady) {
            //javax.swing.JOptionPane.showMessageDialog(null,
            //    "OpenCV not loaded. Camera counting will be unavailable.",
            //    "Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
            System.err.println("OpenCV not loaded. Camera counting will be unavailable.");
        }

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new login().setVisible(true);
                }
            });
            
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private custom_ui.RoundedPanel roundedPanel1;
    private custom_ui.RoundedPanel roundedPanel2;
    private custom_ui.RoundedPanel roundedPanel3;
    // End of variables declaration//GEN-END:variables
}
