package com.jjla2425.da.practica.DataBase;

import com.jjla2425.da.practica.Utils;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

public class SessionMnager {

    private static volatile SessionMnager instance;
    private final SessionFactory sessionFactory;

    private SessionMnager() {
        SessionFactory tempSessionFactory = null;
        try {
            // Configuración de Hibernate
            tempSessionFactory = new Configuration().configure().buildSessionFactory();
            try (Session testSession = tempSessionFactory.openSession()) {
                testSession.beginTransaction();
                testSession.getTransaction().commit();
            }
        } catch (HibernateException e) {
            Utils.showScreen("Error", "Could not connect to database", Alert.AlertType.ERROR);
            throw new RuntimeException("Database connection error: " + e.getMessage(), e); // Lanzar una excepción controlada
        }
        sessionFactory = tempSessionFactory; // Asignar la fábrica de sesiones si se crea correctamente
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

    public Session getSession() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Utils.showScreen("Error", "SessionFactory is not available or is closed.", Alert.AlertType.ERROR);
            return null;
        }
        try {
            return sessionFactory.openSession();
        } catch (HibernateException e) {
            Utils.showScreen("Error", "Could not open Hibernate session: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }
}
