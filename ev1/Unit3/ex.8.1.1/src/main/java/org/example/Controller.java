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
        System.out.println("Write (A) to add a employee for job,(E) to exit");
        String answer = scanner.nextLine();
        while (!answer.equals("E"))
        {
            switch (answer)
            {
                case "A" : addEmployee();break;
            }
            System.out.println("Write (A) to add a employee for job,(E) to exit");
            answer = scanner.nextLine();
        }
    }

    private void addEmployee()
    {
        try(Connection con = DriverManager.getConnection(url, user, password))
        {
            con.setAutoCommit(false);
            try
            {
                System.out.println("Write the name");
                String name  = scanner.nextLine();
                System.out.println("Write the job");
                String job  = scanner.nextLine();
                System.out.println("Write the department");
                String department  = scanner.nextLine();

                PreparedStatement st = con.prepareStatement("SELECT deptno FROM dept WHERE dname = ?");
                st.setString(1, department);
                ResultSet rs = st.executeQuery();
                int deptcode = 0;
                if (rs.next())
                    deptcode = rs.getInt("deptno");
                else
                {
                    PreparedStatement st2 = con.prepareStatement( "INSERT INTO dept (dname) VALUES (?)"
                            , Statement.RETURN_GENERATED_KEYS);
                    st2.executeUpdate();
                    ResultSet keys = st2.getGeneratedKeys();
                    keys.next();
                    deptcode = keys.getInt(1);
                }

                PreparedStatement st3 = con.prepareStatement(
                        "INSERT INTO employee (ename,job,deptno)\n" +
                              "VALUES(?,?,?)"
                               ,Statement.RETURN_GENERATED_KEYS);
                st3.setString(1, name);
                st3.setString(2, job);
                st3.setInt(3, deptcode);
                st3.executeUpdate();
                System.out.println("Transaction finished successfully!!");
                con.commit();
            }
            catch( SQLException ex )
            {
                System.err.println(ex.getMessage());
                con.rollback();
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
