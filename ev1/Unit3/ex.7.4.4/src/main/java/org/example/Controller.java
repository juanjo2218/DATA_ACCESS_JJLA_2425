package org.example;
import java.sql.*;
import java.util.Scanner;
public class Controller
{
    Scanner scanner  = new Scanner(System.in);
    String url = "jdbc:postgresql://localhost:5432/Employees";
    String user = "postgres";
    String password = "postgres";

    public void start()
    {
        System.out.println("Write (J) to search a employee for job,(N) for name,(D) for department,(E) to exit");
        String answer = scanner.nextLine();
        while (!answer.equals("E"))
        {
            switch (answer)
            {
                case "J" : getEmployeeByJob();break;
                case "D" : getEmployeeByDep();break;
                case "N" : getEmployeeByName();break;
            }
            System.out.println("Write (J) to search a employee for job,(N) for name,(D) for department,(E) to exit");
            answer = scanner.nextLine();
        }
    }

    private void getEmployeeByName()
    {
        try(Connection con = DriverManager.getConnection(url, user, password))
        {
            System.out.println("Write the name");
            String answer  = scanner.nextLine();
            CallableStatement statement = con.prepareCall( "{call get_employees_by_name(?)}" );
            statement.setString(1, answer);
            ResultSet rs = statement.executeQuery();
            System.out.println("Code" + "\t" + "Name" + "\t" + "Job" + "\t" + "CodDept");
            System.out.println("----------------------------------------------------------------------------------");
            while (rs.next())
            {
                System.out.println(rs.getInt(1) + "\t " + rs.getString(2)+ "\t " +
                        rs.getString(3)+ "\t " + rs.getInt(4));
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void getEmployeeByDep()
    {
        try(Connection con = DriverManager.getConnection(url, user, password))
        {
            System.out.println("Write the department");
            String answer  = scanner.nextLine();
            CallableStatement statement = con.prepareCall( "{call get_employees_by_departament(?)}" );
            statement.setString(1, answer);
            ResultSet rs = statement.executeQuery();
            System.out.println("Code" + "\t" + "Name" + "\t" + "Job" + "\t" + "CodDept");
            System.out.println("----------------------------------------------------------------------------------");
            while (rs.next())
            {
                System.out.println(rs.getInt(1) + "\t " + rs.getString(2)+ "\t " +
                        rs.getString(3)+ "\t " + rs.getInt(4));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void getEmployeeByJob()
    {
        try(Connection con = DriverManager.getConnection(url, user, password))
        {
            System.out.println("Write the job");
            String answer = scanner.nextLine();
            CallableStatement statement = con.prepareCall("{call get_employees_by_job(?)}");
            statement.setString(1, answer);
            ResultSet rs = statement.executeQuery();
            System.out.println("Code" + "\t" + "Name" + "\t" + "Job" + "\t" + "CodDept");
            System.out.println("----------------------------------------------------------------------------------");
            while (rs.next())
            {
                System.out.println(rs.getInt(1) + "\t " + rs.getString(2)+ "\t " +
                        rs.getString(3)+ "\t " + rs.getInt(4));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
