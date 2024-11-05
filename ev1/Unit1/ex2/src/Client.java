import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String patronisation = "^((\\+|00)\\d{2,3})?\\d{9}$";  // Nota: los backslashes en Java se escapan con '\\'
        Pattern pattern = Pattern.compile(patronisation);
        Matcher matcher = pattern.matcher(newPhone);
        if (matcher.matches())
            phone = newPhone;
        throw new Exception("INVALID_PHONE");
    }

}
