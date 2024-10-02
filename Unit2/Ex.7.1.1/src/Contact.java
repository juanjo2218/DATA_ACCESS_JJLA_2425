import java.io.Serializable;

public class Contact implements Serializable
{
    private String name;
    private String surname;
    private String e_mail;
    private String phone;
    private String description;

    public Contact(String _name, String _surname, String _e_mail, String _phone, String _description)
    {
        name = _name;
        surname = _surname;
        e_mail = _e_mail;
        phone = _phone;
        description = _description;
    }
}
