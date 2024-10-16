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
            String sql =  "CREATE TABLE courses ("
                    + "code SERIAL PRIMARY KEY, "
                    + "name VARCHAR(90) NOT NULL)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            sql = "INSERT INTO courses (name) VALUES (?),(?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "Multiplatform app development");
            pstmt.setString(2, "Web development");
            System.out.println(pstmt.executeUpdate());
            pstmt.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}