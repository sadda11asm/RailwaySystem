package railwaysProject;

import railwaysProject.view.Cities;
import railwaysProject.view.HelloWorld;
import railwaysProject.view.ListItemsService;
import railwaysProject.view.Passengers;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/api")
public class RailwaysSystemApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();
    
    public RailwaysSystemApplication() {
        singletons.add(new HelloWorld());
        singletons.add(new ListItemsService());
        singletons.add(new Cities());
        singletons.add(new Passengers());
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
