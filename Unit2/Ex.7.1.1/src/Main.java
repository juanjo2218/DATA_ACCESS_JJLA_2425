import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args) throws IOException
    {
        Set<Contact> listnewscontact = new HashSet <Contact>();
        Scanner scanner = new Scanner(System.in);
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
        listnewscontact.add(new Contact(name,surname,e_mail,phone,description));
        ObjectOutputStream objectsFile = new ObjectOutputStream(
                new FileOutputStream( new File("contacts.obj")));
        objectsFile.writeInt(listnewscontact.size());
        for (Contact contact: listnewscontact)
        {
            objectsFile.writeObject(contact);
        }
        objectsFile.close();
    }
}