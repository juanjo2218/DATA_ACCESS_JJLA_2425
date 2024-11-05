import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Museum
{
    private String name;
    private String direction;
    private String city;
    private String country;
    private Set<Hall> halls = new HashSet<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
    public void setHalls(HashSet<Hall> halls)
    {
        this.halls = halls;
    }
    public void addHall(Hall hall)
    {
        hall.SetUbication(this);
        halls.add(hall);
    }
    public void removeHall(Hall hall)
    {
        hall.SetUbication(null);
        halls.remove(hall);
    }
}
