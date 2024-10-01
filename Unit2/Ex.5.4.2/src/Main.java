import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Write the path of first file");
        String path1 = scan.nextLine();
        path1 = ExistFile(path1);
        System.out.println("Write the path of second file");
        String path2 = scan.nextLine();
        path2 = ExistFile(path2);
        String pathexit = "sorted.txt";

        PrintWriter printWriter = null;
        try
        {
            printWriter = new PrintWriter(new BufferedWriter(
                    new FileWriter( pathexit, true)));
            String[] array1 = FileToArray(path1);
            String[] array2 =  FileToArray(path2);
            int countMayor = Math.max(array2.length, array1.length);
            int pos1 = 0;
            int pos2 = 0;
            while(pos1 < countMayor && pos2 < countMayor)
            {
                if (array1[pos1].compareTo(array2[pos2]) > 0)
                {
                    //añadir
                    pos1++;
                }
                else
                {
                    //añadir
                    pos2++;
                }
            }
            //añadir lo que queda del mas pequño
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( printWriter != null )
            {
                printWriter.close();
            }
        }
    }
    public static String ExistFile(String path)
    {
        while (!new File(path).exists())
        {
            System.out.println("This path not exist try again");
            Scanner scan = new Scanner(System.in);
            path = scan.nextLine();
        }
        return  path;
    }
    public static String[] FileToArray(String path)
    {
        String[] result  = new String[5];
        try
        {
            FileReader fileReader = new FileReader(path);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return  result;
    }
}