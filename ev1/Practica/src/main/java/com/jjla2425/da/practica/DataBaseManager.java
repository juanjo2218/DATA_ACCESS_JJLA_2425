package com.jjla2425.da.practica;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    public SellersEntity getSellerByCIF(String CIF)
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<SellersEntity> myQuery = session.createQuery("from SellersEntity where cif = :cif", SellersEntity.class);
        myQuery.setParameter("cif", CIF);
        try {
            return myQuery.getSingleResult();
        } catch (NoResultException e) {
            return null; // Devolver null si no se encuentra ningún resultado
        }    }
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
    public CategoriesEntity getCategorieEntityByName(String name)
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<CategoriesEntity> myQuery = session.createQuery("from CategoriesEntity where categoryName = :name", CategoriesEntity.class);
        myQuery.setParameter("name", name);
        try {
            return myQuery.getSingleResult();
        }
        catch (NoResultException e) {
            return null; // Devolver null si no se encuentra ningún resultado
        }
    }
    public ProductsEntity getProductByIdProduct(int product_id)
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where productId = :product_id", ProductsEntity.class);
        myQuery.setParameter("product_id", product_id);
        try {
            return myQuery.getSingleResult();
        } catch (NoResultException e) {
            return null; // Devolver null si no se encuentra ningún resultado
        }
    }
    public ArrayList<SellerProductsEntity> getProductsSellerByCategory(String CIF, String category_id)
    {
        ArrayList<SellerProductsEntity> sellerProducts = getProductsSeller(CIF);
        SellersEntity seller = getSellerByCIF(CIF);
        ArrayList<ProductsEntity> productsCategory = getProductsByIdCategory(category_id);
        ArrayList<Integer> productsId = Utils.getProductsId(productsCategory);
        Session session = SessionMnager.getInstance().getSession();
        Query<SellerProductsEntity> myQuery = session.createQuery("from SellerProductsEntity where productId in (:productsId) " +
                "and sellerId = :idSeller", SellerProductsEntity.class);
        myQuery.setParameter("productsId", productsId);
        myQuery.setParameter("idSeller", seller.getSellerId());
        return (ArrayList<SellerProductsEntity>) myQuery.list();
    }
    public ArrayList<ProductsEntity> convertSellerProductsEntityToProductsEntity(ArrayList<SellerProductsEntity> sellerProducts)
    {
        ArrayList<Integer> productsSellerId = Utils.getSellersProductsId(sellerProducts);
        Session session =  SessionMnager.getInstance().getSession();
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where productId in :product_id", ProductsEntity.class);
        myQuery.setParameter("product_id",productsSellerId);
        return (ArrayList<ProductsEntity>) myQuery.list();

    }

    public ArrayList<SellerProductsEntity> getProductsSeller(String CIF)
    {
        SellersEntity seller = getSellerByCIF(CIF);
        Session session =  SessionMnager.getInstance().getSession();
        Query<SellerProductsEntity> myQuery = session.createQuery("from SellerProductsEntity where sellerId =:idSeller", SellerProductsEntity.class);
        myQuery.setParameter("idSeller", seller.getSellerId());
        return (ArrayList<SellerProductsEntity>) myQuery.list();
    }
    public ArrayList<ProductsEntity> getProductsRemainingSellerWithCategory(String CIF, String category)
    {
        ArrayList<ProductsEntity> result = getProductsByIdCategory(category);
        result.removeAll( convertSellerProductsEntityToProductsEntity(getProductsSellerByCategory(CIF,category)));
        return result;
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

}
