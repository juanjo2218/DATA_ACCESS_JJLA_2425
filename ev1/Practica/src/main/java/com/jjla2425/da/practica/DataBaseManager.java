package com.jjla2425.da.practica;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.time.ZoneId;

import java.time.LocalDate;
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
            return null;
        }    }
    public ArrayList<CategoriesEntity> getCategories()
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<CategoriesEntity> myQuery = session.createQuery("from CategoriesEntity ", CategoriesEntity.class);

        return (ArrayList<CategoriesEntity>) myQuery.list();
    }
    public ArrayList<ProductsEntity> getProductsByIdCategory(int category_id)
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
    public ArrayList<SellerProductsEntity> getProductsSellerByCategory(String CIF, int category_id)
    {
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
    public ArrayList<ProductsEntity> getProductsRemainingSellerWithCategoryId(String CIF, int categoryId)
    {
        ArrayList<ProductsEntity> result = getProductsByIdCategory(categoryId);
        result.removeAll( convertSellerProductsEntityToProductsEntity(getProductsSellerByCategory(CIF,categoryId)));
        return result;
    }
    public void addProductsSeller(SellerProductsEntity product)
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
    public void addOfferProductsSeller(SellerProductsEntity sellerProduct)
    {
        Session session = SessionMnager.getInstance().getSession();
        Transaction transaction = null;
        try {
            // Iniciar transacción
            transaction = session.beginTransaction();

            if (sellerProduct != null) {
                // Hacer la actualización
                session.merge(sellerProduct);
            }

            // Confirmar transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Revertir en caso de error
            }
            e.printStackTrace();
        } finally {
            session.close();  // Cerrar la sesión al final
        }
    }
    public void updateSeller(SellersEntity sellersEntity)
    {
        Session session = SessionMnager.getInstance().getSession();
        Transaction transaction = null;
        try {
            // Iniciar transacción
            transaction = session.beginTransaction();

            if (sellersEntity != null) {
                // Hacer la actualización
                session.merge(sellersEntity);
            }

            // Confirmar transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Revertir en caso de error
            }
            e.printStackTrace();
        } finally {
            session.close();  // Cerrar la sesión al final
        }
    }

    public ProductsEntity getProductsById(int idProduct)
    {
        Session session =  SessionMnager.getInstance().getSession();
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where productId = :idProduct", ProductsEntity.class);
        myQuery.setParameter("idProduct", idProduct);
        try {
            return myQuery.getSingleResult();
        }
        catch (NoResultException e) {
            return null; // Devolver null si no se encuentra ningún resultado
        }
    }

    public SellerProductsEntity getProductSeller(String CIF,int productId)
    {
        SellersEntity seller = getSellerByCIF(CIF);
        Session session = SessionMnager.getInstance().getSession();
        Query<SellerProductsEntity> myQuery = session.createQuery("from SellerProductsEntity where sellerId = :idSeller " +
                "and productId = :productId", SellerProductsEntity.class);
        myQuery.setParameter("productId", productId);
        myQuery.setParameter("idSeller", seller.getSellerId());
        try {
            return myQuery.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public ArrayList<SellerProductsEntity> getProductsSellerNotOfferYet(String cif)
    {
        ArrayList<SellerProductsEntity> productsSeller = DataBaseManager.getInstance().getProductsSeller(cif);
        productsSeller.removeIf(sellerProductsEntity -> sellerProductsEntity.getOfferStartDate() != null || sellerProductsEntity.getOfferEndDate() != null);
        return  productsSeller;
    }
    public boolean getProductsSellerInThisDate(String cif, LocalDate fromDate, LocalDate toDate) {
        ArrayList<SellerProductsEntity> productsSeller = DataBaseManager.getInstance().getProductsSeller(cif);

        for (SellerProductsEntity productEntity : productsSeller) {
            // Convertir las fechas de tipo Date a LocalDate
            LocalDate offerStartDate = productEntity.getOfferStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate offerEndDate = productEntity.getOfferEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Verifica si la nueva oferta se solapa con alguna oferta existente
            if ((fromDate.isBefore(offerEndDate) && toDate.isAfter(offerStartDate)) ||
                    fromDate.isEqual(offerStartDate) || toDate.isEqual(offerEndDate)) {
                return true; // Las fechas se solapan
            }
        }
        return false; // No hay solapamiento
    }
}
