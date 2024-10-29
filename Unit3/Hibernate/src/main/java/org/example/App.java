package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class App
{
    private SessionFactory sessionFactory;
    private Session session;
    public void start()
    {
        sessionFactory =  new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        if (session != null) {
            System.out.println("Session successfully opened!");
        } else {
            System.out.println("Error opening session!");
        }
    }
    public void selectEmployees()
    {

        Query<EmployeeEntity> myQuery = session.createQuery("from com.jrgs2122.Unit5.EmployeeEntity");
        List<EmployeeEntity> employees = myQuery.list();
        for ( EmployeeEntity employee : employees ) {
            System.out.printf("Number : %d Name: %s", employee.getEmpno(),
                    employee.getEname());
        }

    }
}
