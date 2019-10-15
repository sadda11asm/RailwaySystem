package railwaysProject.util;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
    private static DataSource ds = null;

    static {
//        Log.info("Inside Database() static method... ");
        Context context = null;
        try {
            context = new InitialContext();
            ds = (DataSource)context.lookup("java:comp/env/jdbc/tutorialdb");
        } catch (NamingException e) {
//            Log.error("Unable to get Datasource...");
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
