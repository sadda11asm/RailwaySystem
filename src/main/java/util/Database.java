package util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Database {
    private static DataSource ds = null;
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());
    private DataSource datasource = Database.getDataSource();
    static {
        LOGGER.log(Level.WARNING, "Inside Database() static method... ");

        Context context = null;
        try {
            context = new InitialContext();
            ds = (DataSource)context.lookup("java:comp/env/jdbc/tutorialdb");
        } catch (NamingException e) {
            LOGGER.log(Level.WARNING, "Unable to get Datasource...");
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}