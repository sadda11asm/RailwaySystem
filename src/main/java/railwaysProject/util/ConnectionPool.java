package railwaysProject.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class ConnectionPool {
    public static Connection conn = null;
    public static Connection getDatabaseConnection() {
        if (conn == null) {
            try {
                Context initCtx = new InitialContext();
                System.out.println(initCtx.getNameInNamespace());
                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
                conn = ds.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
