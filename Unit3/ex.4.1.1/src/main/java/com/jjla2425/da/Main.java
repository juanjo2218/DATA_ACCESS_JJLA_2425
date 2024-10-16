package com.jjla2425.da;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args)
    {
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "postgres";
        try(Connection con = DriverManager.getConnection(url, user, password))
        {
            Statement statement = con.createStatement();
            String SQLsentence = "SELECT * FROM courses ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);
            System.out.println("Code" + "\t" + "Name"+ "\t" + "Year");
            System.out.println("-----------------------------------------");
            while (rs.next())
            {
                System.out.println(rs.getString("code")+ " " +rs.getString("name"));
            }
            rs.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}