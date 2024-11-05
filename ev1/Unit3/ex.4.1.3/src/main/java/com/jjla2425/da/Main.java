package com.jjla2425.da;

import java.sql.*;

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
            Statement stmt = con.createStatement();
            String sql = "ALTER TABLE subjects ADD hours INTEGER;";
            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch (SQLException e)
        {
            System.out.println("Error to insert.");
        }
    }
}