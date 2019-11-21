package railwaysProject.view;


import railwaysProject.model.LoggerInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/logs")
public class Logs {
    LoggerInfo loggerInfo = ServiceLocator.getLoggerInfo();
    @GET
    public Response getLogs() {
        return Response.ok(loggerInfo.getLogs()).build();
    }
}
