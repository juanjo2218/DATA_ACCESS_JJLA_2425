//
// ContactBookXMLHandler.java
// Ex-822
// Created by Fernando Pastor on 8/10/2024
//

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContactBookXMLHandler extends DefaultHandler {

    protected ContactBook contactBook = new ContactBook();
    protected Contact contact;
    protected String tagContent;

    // Method to get the parsed output...
    public ContactBook getContactBook() {
        return contactBook;
    }

    // Tag opening found...
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("contact"))
            contact = new Contact();
    }

    // Tag content, usually CDATA...
    public void characters(char[] ch, int start, int length) throws SAXException {
        tagContent = new String(ch, start, length);
    }

    // Tag ending...
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!qName.isBlank()) {
            switch (qName) {
                case "name" -> contact.setName(tagContent);
                case "surname" -> contact.setSurname(tagContent);
                case "phone" -> contact.setPhone(tagContent);
                case "email" -> contact.setEmail(tagContent);
                case "description" -> contact.setDescription(tagContent);
                case "contact" -> contactBook.addContact(contact);
            }
        }
    }
}
