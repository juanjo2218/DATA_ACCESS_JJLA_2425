import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ContactList
{
    Set<Contact> contacts = new HashSet<Contact>();
    private final Scanner scanner = new Scanner(System.in);


    public void Start() throws IOException, ClassNotFoundException {
        System.out.println("Write (A) to add new Contact,(S) to see the current list of contacts");
        String option = scanner.nextLine();
        if (option.equals("A"))
            addContact();
        else if (option.equals("S"))
            showContacts(readContacts());
        else
            searchContacts();
    }

    private void searchContacts() throws IOException, ClassNotFoundException
    {
        System.out.println("Write (N) to search for name,(P) to search for phone");
        String option = scanner.nextLine();
        if (option.equals("N"))
            searchWithName();
        else if (option.equals("P"))
            searchWithPhone();
    }

    private void searchWithPhone()
    {

    }

    private void searchWithName() throws IOException, ClassNotFoundException
    {
        System.out.println("Write the name");
        String name = scanner.nextLine();
        for (Contact contact: readContacts())
        {
            if (contact.getName().contains(name))
                contact.write();
        }
    }

    private Set<Contact> readContacts() throws IOException, ClassNotFoundException
    {
        Set<Contact> contactsFile = new HashSet<>();
        ObjectInputStream objectsFile = new ObjectInputStream(
                new FileInputStream( new File("contacts.obj")));
        int numObjects = objectsFile.readInt();
        for ( ; numObjects > 0 ; numObjects-- )
        {
            contactsFile.add((Contact) objectsFile.readObject());
        }
        return contactsFile;
    }
    private void showContacts(Set<Contact> contacts)
    {
        for (Contact contact: contacts)
        {
            contact.write();
        }
    }

    private void addContact() throws IOException, ClassNotFoundException
    {
        //change to other class/function
        System.out.println("Name");
        String name  = scanner.nextLine();
        System.out.println("Surname:");
        String surname  = scanner.nextLine();
        System.out.println("E-mail:");
        String e_mail  = scanner.nextLine();
        System.out.println("Phone:");
        String phone  = scanner.nextLine();
        System.out.println("Description");
        String description  = scanner.nextLine();
        System.out.println("Confirm add? (Y)es o (N)o");
        String answer  = answerValid(scanner.nextLine());
        if (answer.equals("Y"))
            contacts.add(new Contact(name,surname,e_mail,phone,description));
        System.out.println("Shave an other contact? (Y)es o (N)o");
        answer  = answerValid(scanner.nextLine());
        if (answer.equals("Y"))
            addContact();
        writeContact();
        Start();
    }

    private String answerValid(String answer)
    {
        while (!answer.equals("N") && !answer.equals("Y"))
        {
            System.out.println("Answer not valid plis write (Y)es or (N)o");
            answer = scanner.nextLine();
        }
        return answer;
    }
    private void writeContact() throws IOException
    {
        ObjectOutputStream objectsFile = new ObjectOutputStream(
                new FileOutputStream( new File("contacts.obj"),true));
        objectsFile.writeInt(contacts.size());
        for (Contact contact: contacts)
        {
            objectsFile.writeObject(contact);
        }
        contacts.clear();
        objectsFile.close();
    }
}
