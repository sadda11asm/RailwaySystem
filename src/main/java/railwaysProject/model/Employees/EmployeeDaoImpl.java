package railwaysProject.model.Employees;

import railwaysProject.controller.RoutesController;
import railwaysProject.util.ConnectionPool;
import railwaysProject.view.ServiceLocator;

import javax.mail.*;
import javax.mail.internet.AddressException;
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

public class EmployeeDaoImpl {
    RoutesController routesController = ServiceLocator.getRoutesController();
    public Employee getEmployeeByEmailAndPassword(String email, String password) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Employee where email = '" + email + "' and password = '" + password + "'";
            ResultSet lists = myStatement.executeQuery(query);

            while(lists.next()){
                employees.add(new Employee(
                        lists.getInt("employee_id"),
                        lists.getInt ("salary"),
                        lists.getInt("Station_station_id"),
                        lists.getString("email"),
                        lists.getString("password")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employees.size() > 0 ? employees.get(0) : null;
    }

    public boolean deleteTicket(int ticketId,int trainId, int routeId){
        boolean ticketExists = false;
        boolean ticketDeleted = false;
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Ticket where ticket_id = " + ticketId + " and train_id = " + trainId +
                    " and route_id = " + routeId + " and route_start_date > curdate();";
            ResultSet ticket = myStatement.executeQuery(query);

            if(ticket.next()){
                ticketExists = true;
            }
            if(!ticketExists) return false;
            query = "Delete from Ticket where ticket_id = " + ticketId + " and train_id = " + trainId +
                    " and route_id = " + routeId;
            myStatement.executeUpdate(query);
            ticketDeleted = true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ticketDeleted;
    }

    private void sendDeleteRouteAdvisory(List<String> emails, int routeId,LocalDate startDate){
        String subject = "[URGENT!] Your route was cancelled";
        String textBody = "Hello, dear User!\n"
                        + "I want to notify you about cancellation of route which would start on " + startDate
                        + " and with route id " + routeId;

        final String fromEmail = "horpus.nostrum@gmail.com";
        final String password = "12344321m";
        String host = "localhost";

        // Port of SMTP
        String port = "25";
        Properties properties = System.getProperties();

        // Setting up the mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        for(String email : emails){
            try
            {
                // Create MimeMessage object
                MimeMessage message = new MimeMessage(session);

                // Set the Senders mail to From
                message.setFrom(new InternetAddress(fromEmail));

                // Set the recipients email address
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

                // Subject of the email
                message.setSubject(subject);

                // Body of the email
                message.setText(textBody);

                // Send email
                Transport.send(message);
                System.out.println("Mail sent successfully");
            }  catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
    public void notifyAboutDeleteRoute(int routeId, LocalDate startDate){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String getEmails = "Select P.email as email"
                             + "from Passenger as P,"
                             + "Ticket as T where P.passenger_id = T.Passenger_passenger_id "
                             + " and T.route_start_date = " + startDate
                             + " and T.route_id = " + routeId + ";";
            List<String> emails = new ArrayList<>();
            ResultSet rs = statement.executeQuery(getEmails);
            while(rs.next()){
                emails.add(rs.getString("email"));
            }
            String deleteTicket = "Delete from Ticket where T.route_start_date = " + startDate
                    + " and T.route_id = " + routeId + ";";
            statement.executeUpdate(deleteTicket);

            sendDeleteRouteAdvisory(emails, routeId, startDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean cancelRoute(int routeId, String startDate){
        boolean isCanceled = false;
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            LocalDate date = routesController.strToLocalDate(startDate);
            String query = "Select * from Route_Instance where start_date = " + date
                         + " and start_date > curdate() and route_id = " + routeId + ";";
            ResultSet rs = statement.executeQuery(query);
            if(!rs.next()) return false;
            notifyAboutDeleteRoute(routeId,date);
            String routeInstance = "DELETE FROM Route_Instance where start_date = " + date
                    + " and route_id = " + routeId + ";";
            String arrivals = "DELETE FROM  Arrival where route_start_date = " + date
                    + " and route_id = " + routeId + ";";
            String departures = "DELETE FROM  Departure where route_start_date = " + date
                    + " and route_id = " + routeId + ";";
            statement.executeUpdate(departures);
            statement.executeUpdate(arrivals);
            statement.executeUpdate(routeInstance);
            isCanceled = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCanceled;
    }

}
