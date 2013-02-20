package test;

import mail.Mailer;

/**
 * For testing the ability of the backend to send email.
 * 
 * @author Stephen Fahy
 */
public class MailTester {
    public static void test() {
        Mailer.mail("110011331@umail.ucc.ie", "Test mail", "This is a test.");
    }
}
