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
            BufferedReader file1 = new BufferedReader(new FileReader(path1));
            BufferedReader file2 = new BufferedReader(new FileReader(path2));
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(pathexit,false)));
            String linef1 = file1.readLine();
            String linef2 = file2.readLine();
            while(linef2 != null && linef1 != null)
            {
                if (linef1.compareTo(linef2) > 0)
                {
                    printWriter.println(linef2);
                    linef2 = file2.readLine();
                }
                else
                {
                    printWriter.println(linef1);
                    linef1 = file1.readLine();
                }
            }
            BufferedReader file1notempty = linef2 != null ? file2 : file1;
            String linenotnull = linef1 != null ? linef1 : linef2;
            while (linenotnull!= null)
            {
                printWriter.println(linenotnull);
                linenotnull = file1notempty.readLine();
            }
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
}