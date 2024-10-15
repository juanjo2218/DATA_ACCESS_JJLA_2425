import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class myXMLContactsHandler extends DefaultHandler {
    protected String tagContent;
    protected String fullName;
    protected boolean isPhone;
    // Tag opening found
    //
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        if ( qName.equals("contact") )
            System.out.println( "ID: " + attributes.getValue("id"));

        if ( qName.equals("phones") )
            isPhone = true;
    }
    // Tag content, usually CDATA
    //
    public void characters( char ch[], int start, int length )
            throws SAXException {
        tagContent = new String( ch, start, length );
    }
    // Tag ending
    //
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ( !qName.isBlank() )
        {
            if (qName.equals("name"))
                fullName = tagContent;
            if (qName.equals("surname"))
                System.out.println(fullName + " " + tagContent);
        }
    }
}
public class Main {
    public static void main( String[] args ) {
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();
            saxParser.parse("contacts.xml", new
                    myXMLContactsHandler());
        }

        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
