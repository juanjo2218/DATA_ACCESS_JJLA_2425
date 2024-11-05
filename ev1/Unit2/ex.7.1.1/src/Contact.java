import java.io.Serializable;

public class Contact implements Serializable
{
    protected String name;
    protected String surname;
    protected String e_mail;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected String phone;
    protected String description;

    public Contact(String _name, String _surname, String _e_mail, String _phone, String _description)
    {
        name = _name;
        surname = _surname;
        e_mail = _e_mail;
        phone = _phone;
        description = _description;
    }
    public Contact()
    {

    }

    public String getName()
    {
        return name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void write()
    {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("E-mail: " + e_mail);
        System.out.println("Phone: " + phone);
        System.out.println("Description: " + description);
        System.out.println();
    }
}
