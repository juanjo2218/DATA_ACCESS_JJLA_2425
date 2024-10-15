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
            String sql = "INSERT INTO subjects (name,year) VALUES (?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "FOL");
            pstmt.setInt(2, 1);
            System.out.println(pstmt.executeUpdate());
            pstmt.close();
        }
        catch (SQLException e)
        {
            System.out.println("Error to insert.");
        }

    }
}