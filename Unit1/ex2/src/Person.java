import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Person
{
    //
    private String name;
    private Date birthdayDate;

    public String getName()
    {
        return name;
    }

    public void setNombre(String newName)
    {
        name = newName;
    }

    public Date getBirthdayDate()
    {
        return birthdayDate;
    }

    public void setBirthdayDate(Date newBirthdayDate) throws Exception
    {
        if (newBirthdayDate.before(Date.from(Instant.now())))
            throw  new Exception("INVALID_DATE");
        birthdayDate = newBirthdayDate;
    }
    public int getAge()
    {
        return LocalDate.now().getYear() - birthdayDate.getYear();
    }
}
