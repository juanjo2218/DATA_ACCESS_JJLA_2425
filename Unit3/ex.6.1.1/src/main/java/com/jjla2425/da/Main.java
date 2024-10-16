package com.jjla2425.da;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "postgres";
        try(Connection con = DriverManager.getConnection(url, user, password))
        {
            System.out.println("Write (A) to add new subject,(E) to exit");
            String subject;
            String option = scanner.nextLine();
            while (!option.equals("E"))
            {
                switch (option)
                {
                    case "A":
                    {
                        System.out.println("Write name of the subject");
                        subject = scanner.nextLine();
                        String sql = "INSERT INTO subjects (name) VALUES (?)";
                        PreparedStatement pstmt = con.prepareStatement(sql);

                        pstmt.setString(1, subject);
                        System.out.println("Number of subjects add " + pstmt.executeUpdate());
                        pstmt.close();
                    };
                }
                System.out.println("Write (A) to add new subject,(E) to exit");
                option = scanner.nextLine();
            }
        }
        catch (SQLException e)
        {
        }

    }
}