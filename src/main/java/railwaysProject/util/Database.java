package railwaysProject.util;


import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Database {
    private static MysqlDataSource ds = null;
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());
    static {
        LOGGER.log(Level.INFO, "Inside Database() static method... ");
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        try {
            FileInputStream fis = new FileInputStream("../../db.properties");
            Properties props = new Properties();
            props.load(fis);
            ds = new MysqlDataSource();
            ds.setURL(props.getProperty("MYSQL_DB_URL"));
            ds.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            ds.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to get Datasource...");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
