package com.jjla2425.da.practica;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
public class DataBaseManager {

    private static DataBaseManager instance;

    private static SessionFactory sessionFactory;

    // Constructor privado para evitar la instanciación desde fuera de la clase
    private DataBaseManager() {
        // Inicialización de la base de datos o de otros recursos
        try {
            // Configuración de Hibernate, carga el archivo de configuración
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml") // Lee la configuración de hibernate.cfg.xml
                    .addAnnotatedClass(SellersEntity.class) // Añade la clase que mapea la tabla 'Sellers'
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // Lógica para manejar el error de inicialización
        }
    }

    // Método público y estático para obtener la instancia única
    public static synchronized DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }
    public SellersEntity getSellerByCIF(String usernameFieldText)
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<SellersEntity> myQuery = session.createQuery("from SellersEntity where cif = :cif", SellersEntity.class);
        myQuery.setParameter("cif", usernameFieldText);
        System.out.println(myQuery.getSingleResult().getCif());
        return myQuery.getSingleResult();
    }
    //public String getPasswordByUsername(SellersEntity sellerdb)
    //{
        // Session session = sessionFactory.openSession();

        //Query<SellersEntity> myQuery = session.createQuery("from SellersEntity where cif = :cif", SellersEntity.class);
        //myQuery.setParameter("cif", sellerdb.getCif());
        //return myQuery.getQueryString();
        //}
}
