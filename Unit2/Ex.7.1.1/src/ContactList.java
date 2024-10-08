import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ContactList
{
    Set<Contact> contacts = new HashSet<Contact>();
    private final Scanner scanner = new Scanner(System.in);


    public void start() throws IOException, ClassNotFoundException
    {
        contacts = readContacts();
        System.out.println("Write (A) to add new Contact,(S) to see the current list of contacts," +
                "(F) to search a contact for name or phone,(E) to exit");
        String option = scanner.nextLine();
        while (!option.equals("E"))
        {
            switch (option)
            {
                case "A" -> addContact();
                case "S" -> showContacts(contacts);
                case "F" -> searchContacts();
            }
            System.out.println("Write (A) to add new Contact,(S) to see the current list of contacts," +
                    "(F) to search a contact for name or phone,other to exit");
            option = scanner.nextLine();
        }
        writeContact();
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

    private void searchWithPhone() throws IOException, ClassNotFoundException
    {
        System.out.println("Write the phone");
        String phone = scanner.nextLine();
        for (Contact contact: contacts)
        {
            if (contact.getPhone().contains(phone))
                contact.write();
        }
    }

    private void searchWithName() throws IOException, ClassNotFoundException
    {
        System.out.println("Write the name");
        String name = scanner.nextLine();
        for (Contact contact: contacts)
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
    private void showContacts(Set<Contact> contacts) throws IOException, ClassNotFoundException {
        for (Contact contact: contacts)
            contact.write();
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
                new FileOutputStream( new File("contacts.obj"),false));
        objectsFile.writeInt(contacts.size());
        for (Contact contact: contacts)
        {
            objectsFile.writeObject(contact);
        }
        objectsFile.close();
    }
}
