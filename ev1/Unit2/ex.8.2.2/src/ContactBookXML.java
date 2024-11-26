//
// ContactBookXML.java
// Ex-822


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class ContactBookXML extends ContactBook {

    private static String getContactXML(Contact contact) {
        return "\t<contact>\n" +
                "\t\t<name>" + contact.getName() + "</name>\n" +
                "\t\t<surname>" + contact.getSurname() + "</surname>\n" +
                "\t\t<email>" + contact.getEmail() + "</email>\n" +
                "\t\t<phone>" + contact.getPhone() + "</phone>\n" +
                "\t\t<description>" + contact.getDescription() + "</description>\n" +
                "\t</contact>\n";
    }

    private static String getContactBookXML(ContactBook contactBook) {
        var contactCount = contactBook.getContactCount();
        StringBuilder contactBookXML = new StringBuilder();

        contactBookXML.append("<contactbook>\n");
        for (int i = 0; i < contactCount; i++)
            contactBookXML.append(getContactXML(contactBook.getContactAt(i)));
        contactBookXML.append("</contactbook>\n");

        return contactBookXML.toString();
    }

    public static void saveXML(ContactBook contactBook, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, false)));
            writer.print(getContactBookXML(contactBook));
            writer.close();
        } catch (IOException e) {
            System.out.println(Utils.MESSAGE_ERROR + e.getMessage());
        }
    }

    public static ContactBook loadXML(String filename) {
        ContactBookXMLHandler handler = new ContactBookXMLHandler();
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse("contactbook.xml", handler);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println(Utils.MESSAGE_ERROR + e.getMessage());
        }
        return handler.getContactBook();
    }
}
