import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class myXMLContactsHandler extends DefaultHandler {
    protected String tagContent;
    protected String fullName;
    protected String phoneNumber;
    protected boolean isPhone;
    protected Phonetype phonetype;
    // Tag opening found
    //
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
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
            if (qName.equals("cell"))
            {
                phoneNumber = tagContent;
                phonetype = Phonetype.CELL;
            }
            if (qName.equals("work") && isPhone && phonetype != Phonetype.CELL)
            {
                phoneNumber = tagContent;
                phonetype = Phonetype.WORK;
            }

            if (qName.equals("home") && isPhone && phonetype != Phonetype.CELL && phonetype != Phonetype.WORK)
            {
                phoneNumber = tagContent;
                phonetype = Phonetype.HOME;
            }
            if (qName.equals("phones"))
                System.out.println(phonetype + ": " + phoneNumber);
        }
    }
}
public class Main {
    public static void main( String[] args ) {
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();
            saxParser.parse("xml.xml", new
                    myXMLContactsHandler());
        }

        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
