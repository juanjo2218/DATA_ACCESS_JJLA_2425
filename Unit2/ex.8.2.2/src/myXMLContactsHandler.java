import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.jar.Attributes;

public class myXMLContactsHandler extends DefaultHandler
{
    Contact contact;
    protected String tagContent;
    // Tag opening found
    //
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException
    {
        if ( qName.equals("contact"))
            contact = new Contact();
    }
    // Tag content, usually CDATA
    //
    public void characters( char ch[], int start, int length ) throws SAXException
    {
        tagContent = new String( ch, start, length );
    }
    // Tag ending
    //
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (!qName.isBlank())
        {
            if ( !qName.equals("name"))
                contact.setName(tagContent);
            if ( !qName.equals("surname"))
                contact.setSurname(tagContent);
            if ( !qName.equals("description"))
                contact.setDescription(tagContent);
            if ( !qName.equals("phone"))
                contact.setPhone(tagContent);
            if ( !qName.equals("email"))
                contact.setE_mail(tagContent);
        }
    }
}

