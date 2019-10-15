package railwaysProject.view;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/items")
public class ListItemsService {

    private List<String> list = new CopyOnWriteArrayList<String>();

    public ListItemsService() {
        for (int i = 0; i < 20; i++) {
            list.add("Entry " + String.valueOf(i));
        }

        Collections.reverse(list);
    }

    @GET
    public Response getList() {
        Gson gson = new Gson();

        return Response.ok(gson.toJson(list)).build();
    }

    @GET
    @Path("{id: [0-9]+}")
    public Response getListItem(@PathParam("id") String id) {
        int i = Integer.parseInt(id);

        return Response.ok(list.get(i)).build();
    }

    @POST
    public Response postListItem(@FormParam("newEntry") String entry) {
        list.add(0, entry);

        return Response.ok().build();
    }
}