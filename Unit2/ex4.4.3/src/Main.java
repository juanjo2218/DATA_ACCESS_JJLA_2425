import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        byte[] byteArray = {0x10, 0x20, 0x30, 0x40, 0x50};
        Ex.adas();

    }
}
class Ex
{

    public static void  adas() throws IOException {
        Scanner myScanner = new Scanner(System.in);
        String path = myScanner.nextLine();
        try {
            FileInputStream file = new FileInputStream(path);
            byte[] data = new byte[30];
            if (file.read(data) != -1)
            {
                if(IsBMP(data))
                {

                    int INITBYTESIZE = 2;
                    int BYTEQUANITYSIZE = 4;
                    int INITBYTEWIDTH = 18;
                    int BYTEQUANITYWIDTH= 4;
                    int INITBYTEHEIGHT = 22;
                    int BYTEQUANITYHEIGHT = 4;
                    int INITBYTEBPP = 28;
                    int BYTEQUANITYBPP= 2;
                    System.out.println("Size: " + toInt(data,INITBYTESIZE,BYTEQUANITYSIZE) + " bytes");
                    System.out.println("Width : " + toInt(data,INITBYTEWIDTH,BYTEQUANITYWIDTH) + " pixels");
                    System.out.println("Height : " + toInt(data,INITBYTEHEIGHT,BYTEQUANITYHEIGHT) + " pixels");
                    System.out.println("Bits for píxel: " + toInt(data,INITBYTEBPP,BYTEQUANITYBPP) + " bits");
                }
                else
                    System.out.println("NOT BMP");
            }
            else
            {
                System.out.println("FILE_EMPTY");
            }
        }
        catch (Exception ignored)
        {
            System.out.println("PATH_INCORRECT");
        }

    }
    public static boolean IsBMP(byte[] file)
    {
        return file[0] == 0x42 && file[1] == 0x4d ;
    }

    public static long toInt(byte[] file,int offset,int quantity)
    {
        long total = 0;
        for (int i = 0 ; i < quantity; i++)
        {
            total += (long) ((file[offset + i] & 0xFF) * Math.pow(256, i));
        }
        return total;
    }
}