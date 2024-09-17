import java.util.HashSet;
import java.util.Set;

public class Company
{
    private String name;
    private Set<Employee> template = new HashSet<Employee>();
    private Set<Client> client_Portfolio = new HashSet<Client>();

    public String getName()
    {
        return name;
    }

    public void setName(String newName)
    {
        name = newName;
    }
    public int totalClients()
    {
        return client_Portfolio.size();
    }
    public int EmpleadosTotales ()
    {
        return template.size();
    }
}
