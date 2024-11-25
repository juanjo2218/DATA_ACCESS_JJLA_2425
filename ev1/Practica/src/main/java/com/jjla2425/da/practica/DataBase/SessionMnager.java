package com.jjla2425.da.practica.DataBase;

import com.jjla2425.da.practica.Utils;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class SessionMnager {
    private static final Logger logger = LogManager.getLogger(SessionMnager.class);

    private static volatile SessionMnager instance;
    private final SessionFactory sessionFactory;

    private SessionMnager() {
        logger.info("Initializing SessionManager...");
        sessionFactory = new Configuration().configure().buildSessionFactory();
        try (Session testSession = sessionFactory.openSession()) {
            testSession.beginTransaction();
            testSession.getTransaction().commit();
            logger.info("Successfully connected to the database.");
        } catch (Exception e) {
            logger.error("Failed to connect to the database: {}", e.getMessage());
            Utils.showScreen("Error", "Could not connect to database", Alert.AlertType.ERROR);
        }
    }

    public static SessionMnager getInstance() {
        if (instance == null) {
            synchronized (SessionMnager.class) {
                if (instance == null) {
                    logger.info("Creating a new instance of SessionMnager.");
                    instance = new SessionMnager();
                }
            }
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getSession() {
        try {
            return getSessionFactory().openSession();
        } catch (Exception e) {
            logger.error("Error while opening session: {}", e.getMessage());
            return null;
        }
    }

    public void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            logger.info("SessionFactory closed.");
        }
    }
}
