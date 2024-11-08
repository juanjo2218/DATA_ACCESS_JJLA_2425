package com.jjla2425.da.practica;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;

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
        return myQuery.getSingleResult();
    }
    public ArrayList<CategoriesEntity> getCategories()
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<CategoriesEntity> myQuery = session.createQuery("from CategoriesEntity ", CategoriesEntity.class);

        return (ArrayList<CategoriesEntity>) myQuery.list();
    }
    public ArrayList<ProductsEntity> getProductsByIdCategory(String category_id)
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where categoryId = :category_id", ProductsEntity.class);
        myQuery.setParameter("category_id", category_id);
        return (ArrayList<ProductsEntity>) myQuery.list();
    }
    public ArrayList<SellerProductsEntity> getProductsSeller(String CIF,String category_id)
    {
        SellersEntity seller = getSellerByCIF(CIF);
        ArrayList<ProductsEntity> products = getProductsByIdCategory(category_id);
        ArrayList<Integer> productsId = Utils.getProductsId(products);
        Session session =  SessionMnager.getInstance().getSession();
        Query<SellerProductsEntity> myQuery = session.createQuery("from SellerProductsEntity where productId in :productsId " +
                "and sellerId = :idSeller", SellerProductsEntity.class);
        myQuery.setParameter("productsId", productsId);
        myQuery.setParameter("idSeller", seller.getSellerId());
        return (ArrayList<SellerProductsEntity>) myQuery.list();
    }
    public ArrayList<ProductsEntity> getProductsSeller(ArrayList<SellerProductsEntity> sellerProducts)
    {
        ArrayList<Integer> sellersProductsId = Utils.getSellersProductsId(sellerProducts);
        Session session =  SessionMnager.getInstance().getSession();
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where productId not in : sellersProductsId", ProductsEntity.class);
        myQuery.setParameter("sellersProductsId", sellersProductsId);
        return (ArrayList<ProductsEntity>) myQuery.list();
    }
    public void addProductsSeller(ProductsEntity product)
    {
        try ( Session session = SessionMnager.getInstance().getSession() ) {
            session.beginTransaction();
            session.persist( product );
            session.getTransaction().commit();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }
    public void addOfferProductsSeller(ProductsEntity product)
    {
        try ( Session session = SessionMnager.getInstance().getSession() ) {
            session.beginTransaction();
            session.persist( product );
            session.getTransaction().commit();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }
    public void updateSeller(ProductsEntity product)
    {
        try ( Session session = SessionMnager.getInstance().getSession() ) {
            session.beginTransaction();
            session.persist( product );
            session.getTransaction().commit();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }
    //public String getPasswordByUsername(SellersEntity sellerdb)
    //{
        // Session session = sessionFactory.openSession();

        //Query<SellersEntity> myQuery = session.createQuery("from SellersEntity where cif = :cif", SellersEntity.class);
        //myQuery.setParameter("cif", sellerdb.getCif());
        //return myQuery.getQueryString();
        //}
}
