import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main( String[] args ) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Write where you want write your sentences");
        String path = scan.nextLine();
        if (path.isEmpty())
            throw new Exception("The path not can be empty");
        File fil = new File(path);
        boolean append  = false;
        boolean willExit = false;
        long lines = 0;
        if (fil.exists())
        {
            System.out.println("This route already exists,you want append (Y)es or (N)o");
            String answer = scan.nextLine();
            while(!Objects.equals(answer, "Y") && !Objects.equals(answer, "N"))
            {
                System.out.println("This answer is not Y or N plis write Y or  N");
                answer = scan.nextLine();
            }
            if (Objects.equals(answer, "Y"))
            {
                lines = Files.lines(Path.of(path)).count();
                append = true;
            }

        }


        PrintWriter printWriter = null;
        try
        {
            printWriter = new PrintWriter(new BufferedWriter(
                new FileWriter( path, append)));
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