package railwaysProject.view;

import com.google.gson.Gson;
import railwaysProject.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    public Response getClichedMessage() {

        Connection conn;
        try {
            conn = ConnectionPool.getDatabaseConnection();
            System.out.println(conn.createStatement().executeQuery("select * from Locality"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok("result").build();
    }

}