import java.util.HashSet;
import java.util.Set;

public class Client extends Person
{
    private String phone;
    private Set<Company> clientOf = new HashSet<Company>();

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String newPhone) throws Exception
    {
        String patronisation = "^((\+|00)\d{2,3})?\d{9}$";
        if (Regex.IsMatch(value, patronisation))
            phone = newPhone;
        phone = newPhone;
        throw new Exception("INVALID_PHONE");
    }

}
