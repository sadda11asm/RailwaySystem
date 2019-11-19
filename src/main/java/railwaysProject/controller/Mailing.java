package railwaysProject.controller;

import railwaysProject.util.ConnectionPool;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Mailing {
    public static Session getSession() {
        String username = "zhadorazavr99@gmail.com";
        String password = "xlgdspozmchptrqm";
        String host = "smtp.gmail.com";
        String port = "465";
        String sslFactory = "javax.net.ssl.SSLSocketFactory";
        String protocol = "smtp";

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class",sslFactory);
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
        return session;
    }

    private static String getRouteName(int routeId){
        String routeName = "UNDEFINED";
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select route_name from Route where route_id = " + routeId + ";";
            ResultSet route = myStatement.executeQuery(query);
            if(!route.next()) return route.getString("route_name");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return routeName;
    }

    private static List<String> getEmailsOfPassangers(){
        List<String> passengers = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select email from Passenger;";
            ResultSet rs = myStatement.executeQuery(query);
            while(!rs.next()) passengers.add(rs.getString("email"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengers;
    }
    public static void sendCreateRouteAdvisory( int routeId){
        String subject = "New route is available!";
        String textBody = "Hello, dear User!\n"
                + "Please, be informed that new route " + getRouteName(routeId) + " was created! Check our website for further information. :)";

        sendMessage(getEmailsOfPassangers(), subject, textBody);
    }

    private static void sendMessage(List<String> emails, String subject, String textBody){
        Session session = Mailing.getSession();
        for(String email : emails){
            try {
                Transport transport = session.getTransport();
                InternetAddress addressFrom = new InternetAddress(email);

                MimeMessage message = new MimeMessage(session);
                message.setSender(addressFrom);
                message.setSubject(subject);
                message.setContent(String.format(textBody), "text/plain");
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

                transport.connect();
                Transport.send(message);
                transport.close();
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
            System.out.println("Mail sent successfully");
        }
    }
    public static void sendDeleteRouteAdvisory(List<String> emails, int routeId, LocalDate startDate){
        String subject = "[URGENT!] Your route was cancelled";
        String textBody = "Hello, dear User!\n"
                + "I want to notify you about cancellation of route" + getRouteName(routeId) + " which would start on " + startDate;

        sendMessage(emails, subject, textBody);
    }
}
