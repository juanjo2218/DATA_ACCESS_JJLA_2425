import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main
{
    public static void main(String[] args)
    {

    }
    public  static void detectFormat()
    {

        try (InputStream is = new FileInputStream("descarga.gif"))
        {
            byte[] data = new byte[128];
            int bytesRead = is.read(data);
            while( bytesRead != -1 )
            {

            }
        }
        catch( IOException e )
        {
            System.out.println( e.getMessage() );
        }
    }
}