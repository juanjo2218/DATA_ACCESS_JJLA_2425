import java.util.HashSet;
import java.util.Set;

public class Executive extends Employee
{
    private String category;
    private Set<Employee> supervises = new HashSet<Employee>();

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String newCategory)
    {
        category = newCategory;
    }
    public int subordinates()
    {
        return  supervises.size();
    }
}
