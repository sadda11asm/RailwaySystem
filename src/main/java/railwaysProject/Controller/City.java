package railwaysProject.Controller;

public class City{
    private String name;
    private int localityId;

    public City(String name, int localityId){
        this.name = name;
        this.localityId = localityId;
    }

    public String getCity(){
        return this.name;
    }
    public int getLocalityId(){
        return this.localityId;
    }
}
