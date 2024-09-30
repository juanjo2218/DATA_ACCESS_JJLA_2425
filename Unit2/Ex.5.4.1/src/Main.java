import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main( String[] args ) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Write where you want write your sentences");
        String path = scan.nextLine();
        if (path.isEmpty())
            throw new Exception("The path not can be empty");
        File fil = new File(path);
        boolean setting  = false;
        boolean willExit = false;
        long lines = 0;
        if (fil.exists())
        {
            System.out.println("This route already exists if you want overwrite write O , to append write A");
            if (scan.nextLine() == "A");
            {
                lines =  Files.lines(Path.of(path)).count();
                setting = true;
            }
        }

        PrintWriter printWriter = null;
        try
        {
            printWriter = new PrintWriter(new BufferedWriter(
                new FileWriter( path, setting)));
            while (!willExit)
            {

                System.out.println("Write your sentences or E to close");
                String sentence  = scan.nextLine();
                if (sentence.equals("E"))
                {
                    willExit = true;
                    continue;
                }
                printWriter.println(lines+ " "+ sentence);
                lines++;
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
}