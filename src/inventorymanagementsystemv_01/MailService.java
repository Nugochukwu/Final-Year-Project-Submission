package inventorymanagementsystemv_01;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;




/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ugochukwu Nwodo
 */
public class MailService {
    private String senderEmail;
    private String senderPassword; // In a real app, manage this securely!

    public MailService() {
    }

   public static String getEmail(int staffId){
       String foundEmail = null;
       try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bioss_system", "root", "Vitamin101##");
            // Use PreparedStatement to prevent SQL injection
            String getCurrentUser = "SELECT emailAddress FROM users WHERE staffId = ?"; // Assuming your table is named 'users'
            PreparedStatement ps = con.prepareStatement(getCurrentUser);
            ps.setInt(1, staffId); // Set the email parameter
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                foundEmail = rs.getString("emailAddress");
                System.out.println("Staff ID: " + staffId + ", Email Found"); // For debugging
            } else {
                System.out.println("User with emstaffIdail " + staffId + " Email not found.");
                
            }

            rs.close();
            ps.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            e.printStackTrace();
        }
        return foundEmail;
    }
       
   
    public static boolean sendEmail(String receiver, String username, String pass,String messageBody,String subject) {
         
        String senderEmail = "alfrednwodo2@gmail.com";
        String senderPassword = "oxmq ecoq cplc ncyk";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            System.out.println("Email sent!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
