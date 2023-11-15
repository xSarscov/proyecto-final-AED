package service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.ModelMessage;

public class ServiceMail {

    public ModelMessage sendMain(String toEmail, String code) {
        ModelMessage ms = new ModelMessage(false, "");
        String from = "alexanderjosue998@gmail.com";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        String username = "alexanderjosue998@gmail.com";
        String password = "bvuh vffz elmd hovy";    //  Your email password here
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Verify Code");
            String htmlMessage = "<html>"
                    + "<body style='font-family: Arial, sans-serif;'>"
                    + "<div style='background-color: #f4f4f4; padding: 20px; text-align: center;'>"
                    + "<h2 style='color: #333;'>Código de Verificación</h2>"
                    + "<p style='font-size: 18px;'>Tu código de verificación es: <strong style='font-size: 24px; color: #007bff;'>" + code + "</strong></p>"
                    + "<p style='font-size: 16px;'>Utiliza este código para verificar tu cuenta.</p>"
                    + "<p style='font-size: 14px;'>Si no has solicitado la verificación, por favor ignora este mensaje.</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            message.setContent(htmlMessage, "text/html");
            Transport.send(message);
            ms.setSuccess(true);
        } catch (MessagingException e) {
            if (e.getMessage().equals("Invalid Addresses")) {
                ms.setMessage("Corre electrónico inválido");
            } else {
                ms.setMessage("Error");
            }
        }
        return ms;
    }
}
