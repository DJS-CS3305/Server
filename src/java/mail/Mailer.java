package mail;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import log.ErrorLogger;

/**
 * Class for emailing clients with news of updates and replies to messages
 * logged in the database by the web server.
 * 
 * @author Stephen Fahy
 */
public class Mailer {
    //regular newline escape characters do not work, need this
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String FROM = "djs.cs3305@gmail.com";
    private static final String PASS = "PrOjEcT4552";
    private static final String FOOTER = NEW_LINE + NEW_LINE + NEW_LINE +
            "DO NOT REPLY. This is from the UCC Summer Courses Office.";
    
    /**
     * Emails the given address with the given message. Code in part from the 
     * tutorial at:
     * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
     * 
     * @param to Outgoing email address.
     * @param subject The subject line of the email.
     * @param body Email body content.
     */
    public static void mail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM, PASS);
                    }
                });
        
        try {
            Message m = new MimeMessage(session);
            m.setFrom(new InternetAddress(FROM));
            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            m.setSubject(subject);
            m.setText(body + FOOTER);
            m.setSentDate(new Date());
            
            Transport.send(m);
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
        }
    }
}
