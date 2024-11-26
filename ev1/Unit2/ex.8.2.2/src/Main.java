//
// Main.java
// Ex-822

public class Main {

    public static void main(String[] args) {

        ContactBook contactBook = new ContactBook();
        Demo.addDemoData(contactBook);
        System.out.println(contactBook);

        // Save contact book to disk as XML file...
        ContactBookXML.saveXML(contactBook, "contactbook.xml");

        // Load and parse XML and print to screen...
        System.out.println(ContactBookXML.loadXML("contactbook.xml"));

    }
}
