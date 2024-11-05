package com.jjla2425.Unit4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class App
{
    Scanner scanner = new Scanner(System.in);
    SessionFactory sessionFactory =  new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    View view = new View();
    public void start()
    {
        if (session != null) {
            System.out.println("Session successfully opened!");
        } else {
            System.out.println("Error opening session!");
        }
        String table = view.displayMenu();
        while (!table.equals("O"))
        {
            switch (table)
            {
                case "E":tableEmployees();break;
                case "D":tableDept();break;
            }
            table = view.displayMenu();
        }
    }

    private void tableDept()
    {
        String CRUD = view.displayCRUDptions();
        while (!CRUD.equals("O"))
        {
            switch (CRUD)
            {
                case "S":selectDept();
                case "D":deleteDept();
                case "I":insertDept();
                case "U":updateDept();
            }
            CRUD = view.displayMenu();
        }
    }
    public void selectDept()
    {
        Query<DeptEntity> myQuery =
                session.createQuery("from com.jjla2425.Unit4.DeptEntity");
        List<DeptEntity> depts = myQuery.list();
        for ( DeptEntity dept : depts ) {
            System.out.printf("Number : %d %n Name: %s", dept.getDeptno(),
                    dept.getDname());
        }
    }

    private void updateDept() {

    }

    private void insertDept() {
    }

    private void deleteDept()
    {

    }
    private void tableEmployees()
    {
        String CRUD = view.displayCRUDptions();
        while (!CRUD.equals("O"))
        {
            switch (CRUD)
            {
                case "S":selectEmployee();
                case "D":deleteEmployee();
                case "I":insertEmployee();
                case "U":updateEmployee();
            }
            CRUD = view.displayMenu();
        }
    }

    private void updateEmployee() {
    }

    private void insertEmployee() {

    }

    private void deleteEmployee()
    {
        try ( Session session = openSession() )
        {
            session.beginTransaction();
            int employeeNumber = view.deletemenu();
            EmployeeEntity employee = session.get( EmployeeEntity.class,
                    employeeNumber );
            //Transaction transaction = session.getTransaction();
            if ( employee != null )
            {
                session.delete(employee);
                session.getTransaction().commit(); // End of transaction
                System.out.println("The employee has been deleted.");
            }
            else
            {
                session.getTransaction().rollback();
                System.out.println("Employee not found.");
            }
        }
        catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }

    public Session openSession() throws Exception {
        if ( sessionFactory == null )
            sessionFactory =
                    new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session == null) {
            throw new Exception("Error opening session!");
        }
        return session;
    }

    public void selectEmployee()
    {
        Query<EmployeeEntity> myQuery =
                session.createQuery("from com.jjla2425.Unit4.EmployeeEntity");
        List<EmployeeEntity> employees = myQuery.list();
        for ( EmployeeEntity employee : employees ) {
            System.out.printf("Number : %d %n Name: %s", employee.getEmpno(),
                    employee.getEname());
        }
    }
}
