package railwaysProject.controller;


import railwaysProject.util.ConnectionPool;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmailService {

    private String username = "zhadorazavr99@gmail.com";

    private static String getRouteName(int routeId){
        String routeName = "UNDEFINED";
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select route_name from Route where route_id = " + routeId + ";";
            ResultSet route = myStatement.executeQuery(query);
            if(route.next()) return route.getString("route_name");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return routeName;
    }

    private  List<String> getEmailsOfPassengers(){
        List<String> passengers = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select email from Passenger;";
            ResultSet rs = myStatement.executeQuery(query);
            while(rs.next()) passengers.add(rs.getString("email"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengers;
    }

    public  void sendCreateRouteAdvisory( int routeId){
        String subject = "New route is available!";
        String textBody = "Hello, dear User!\n"
                + "Please, be informed that new route " + getRouteName(routeId) + " was created! Check our website for further information. :)";
        System.out.println("Mailing: 81");
        sendMessage(getEmailsOfPassengers(), subject, textBody);
    }


    public void sendDeleteRouteAdvisory(List<String> emails, int routeId, LocalDate startDate){
        String subject = "[URGENT!] Your route was cancelled";
        String textBody = "Hello, dear User!\n"
                + "I would like to notify you about the cancellation of route " + getRouteName(routeId) + " which would start on " + startDate;

        sendMessage(emails, subject, textBody);
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
        Session session = getSession();
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            InternetAddress to[] = new InternetAddress[1];
            to[0] = new InternetAddress(toEmail);
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject("subjectAgentCreated()");
            message.setContent("textAgentCreated(agentUsername, agentPassword)", "text/html;charset=UTF-8");
            Transport.send(message);
        } catch (Exception ex) {
            System.out.println( ex.getMessage());
        }
    }
    private void sendMessage(List<String> emails, String subject, String textBody){
        Session session = getSession();
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(username));
            for(String email: emails){
                try {
                    System.out.println("EMAILS: " + email + subject + textBody);
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                    message.setSubject(subject);
                    message.setContent(textBody, "text/html;charset=UTF-8");
                    Transport.send(message);
                }catch (Exception e){
                    continue;
                }
            }
        } catch (Exception ex) {
            System.out.println( ex.getMessage());
        }
    }

    public void sendCreationOfProfile(String email, String password){
        Session session = getSession();
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Your profile was created on treenatree.com!");
            message.setContent("Dear User! \n Please be aware of creation of your account on treenatree.com!\n"+
                    "Your login: "+ email  +
                    ".\nYour temporary password: " + password+
                    ".\nPlease, change your password as soon as possible.","text/html;charset=UTF-8" );
            Transport.send(message);
        } catch (Exception ex) {
            System.out.println( ex.getMessage());
        }
    }
}
