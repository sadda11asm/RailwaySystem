package railwaysProject.controller;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.List;
import java.util.Properties;

public class EmailService {

    private String username = "zhadorazavr99@gmail.com";
    private String password = "xlgdspozmchptrqm";
    private String host = "smtp.gmail.com";
    private String port = "465";
    private String sslFactory = "javax.net.ssl.SSLSocketFactory";
    private String protocol = "smtp";

    private String subjectAgentCreated() {
        return "An account with your email has been created on railways.kz. Please change your password.";
    }

    private String textAgentCreated(String username, String password) {
        return String.format("Your account username: %s \n temporary password: %s", username, password);
    }

    private String subjectTicketCanceled() {
        return "PLEASE BE INFORMED: Your ticket has been canceled!";
    }

    private String textTicketCanceled() {
        return String.format("One of your tickets has been canceled due to route cancellation. Please visit your account to change the ticket!");
    }


    private InternetAddress[] convertToInternetAddress(List<String> toEmail) {
        InternetAddress[] toEmailAdress = new InternetAddress[toEmail.size()];
        try {
            for(int i = 0; i < toEmail.size(); i++) {
                toEmailAdress[i] = new InternetAddress(toEmail.get(i));
            }
        } catch (AddressException ex) {
            System.out.println(ex.getMessage() + " -> " + ex.getPos());
        }
        return toEmailAdress;
    }

    private Session getSession() {
        Session session = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            session = (Session) envCtx.lookup("mail/NomDeLaRessource");

        } catch (Exception ex) {
            System.out.println("lookup error");
            System.out.println( ex.getMessage());
        }
        return session;
    }

    public void sendAgentCreated(String toEmail) {

//        Session session = getSession();
//
//        try {
//            Transport transport = session.getTransport();
//            InternetAddress addressFrom = new InternetAddress(username);
//
//            MimeMessage message = new MimeMessage(session);
//            message.setSender(addressFrom);
//            message.setSubject(subjectAgentCreated());
//            message.setContent("textAgentCreated(agentUsername, agentPassword)", "text/plain");
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//
//            transport.connect();
//            Transport.send(message);
//            transport.close();
//        } catch (MessagingException ex) {
//            ex.printStackTrace();
//        }

        Session session = getSession();

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            InternetAddress to[] = new InternetAddress[1];
            to[0] = new InternetAddress(toEmail);
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subjectAgentCreated());
            message.setContent("textAgentCreated(agentUsername, agentPassword)", "text/html;charset=UTF-8");
            Transport.send(message);
        } catch (AddressException ex) {
            System.out.println( ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println( ex.getMessage());
        }
    }

    public void sendTicketCanceled(List<String> toEmailList) {

        InternetAddress[] toEmail = convertToInternetAddress(toEmailList);

        try {
            Session session = getSession();
            Transport transport = session.getTransport();
            InternetAddress addressFrom = new InternetAddress(username);

            MimeMessage message = new MimeMessage(session);
            message.setSender(addressFrom);
            message.setSubject(subjectTicketCanceled());
            message.setContent(textTicketCanceled(), "text/plain");
            message.addRecipients(Message.RecipientType.TO, toEmail);

            transport.connect();
            Transport.send(message);
            transport.close();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

}