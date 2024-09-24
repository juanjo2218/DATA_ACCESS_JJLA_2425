import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        FileInputStream file = new FileInputStream(path);
        byte[] data = new byte[26];
        if (file.read(data) != -1)
        {
            if(IsBMP(data))
            {
                System.out.println("adfsad");
                long dad = readSize(data,22,4);
            }

        }
    }
    public static boolean IsBMP(byte[] file)
    {
        return file[0] == 0x42 && file[1] == 0x4d ;
    }
    public static long readSize(byte[] file,int offset,int quantity)
    {
        long total = 0;
        for (int i = 0 ; i < quantity; i++)
        {

        }
        return total;
    }
}