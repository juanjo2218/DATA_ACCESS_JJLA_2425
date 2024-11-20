package com.jjla2425.da.practica.DataBase;

import com.jjla2425.da.practica.Utils;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionMnager {

    private static volatile SessionMnager instance;
    private final SessionFactory sessionFactory;

    private SessionMnager() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session testSession = sessionFactory.openSession()) {
            testSession.beginTransaction();
            testSession.getTransaction().commit();
        } catch (Exception e) {
            Utils.showScreen("Error", "Could not connect to database", Alert.AlertType.ERROR);
        }
    }

    public static SessionMnager getInstance() {
        if (instance == null) {
            synchronized (SessionMnager.class) {
                if (instance == null) {
                    instance = new SessionMnager();
                }
            }
        }
        return instance;
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    public Session getSession()
    {
        return getSessionFactory().openSession();
    }
}