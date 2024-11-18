package com.jjla2425.da.practica.DataBase;

import com.jjla2425.da.practica.DataBaseEntities.CategoriesEntity;
import com.jjla2425.da.practica.DataBaseEntities.ProductsEntity;
import com.jjla2425.da.practica.DataBaseEntities.SellerProductsEntity;
import com.jjla2425.da.practica.DataBaseEntities.SellersEntity;
import com.jjla2425.da.practica.Utils;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.time.ZoneId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
        } catch (NoResultException e)
        {
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
//    public ArrayList<ProductsEntity> getProductsRemainingSellerWithCategoryId(String CIF, int categoryId)
//    {
//        ArrayList<ProductsEntity> result = getProductsByIdCategory(categoryId);
//        result.removeAll( convertSellerProductsEntityToProductsEntity(getProductsSellerByCategory(CIF,categoryId)));
//        return result;
//    }
    public ArrayList<ProductsEntity> getProductsRemainingSellerWithCategoryId(int idseller, int categoryId)
    {
        Session session =  SessionMnager.getInstance().getSession();
        String sql = "SELECT * FROM jjla_GetProductsSellerRemaining(:idSeller,:idCategory)";
        Query<ProductsEntity> myQuery = session.createNativeQuery(sql, ProductsEntity.class);
        myQuery.setParameter("idSeller", idseller);
        myQuery.setParameter("idCategory", categoryId);
        return (ArrayList<ProductsEntity>) myQuery.list();

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

        // Convertimos las fechas de entrada a Date para compararlas con las fechas de las ofertas
        Date fromDateAsDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date toDateAsDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (SellerProductsEntity productEntity : productsSeller) {
            Date offerStartDate = productEntity.getOfferStartDate();
            Date offerEndDate = productEntity.getOfferEndDate();

            // Ignorar productos sin oferta (ambas fechas a null)
            if (offerStartDate == null && offerEndDate == null) {
                continue;
            }

            // Caso: solo fecha de inicio válida (la oferta dura indefinidamente desde offerStartDate)
            if (offerStartDate != null && offerEndDate == null) {
                if (!toDateAsDate.before(offerStartDate)) {
                    return true; // Hay solapamiento porque el rango de la oferta no tiene fin
                }
            }

            // Caso: solo fecha de fin válida (la oferta es válida hasta offerEndDate)
            if (offerStartDate == null && offerEndDate != null) {
                if (!fromDateAsDate.after(offerEndDate)) {
                    return true; // Hay solapamiento porque la oferta aún es válida hasta offerEndDate
                }
            }

            // Caso: ambas fechas válidas (la oferta tiene un rango definido)
            if (offerStartDate != null && offerEndDate != null) {
                boolean startOverlap = !toDateAsDate.before(offerStartDate);
                boolean endOverlap = !fromDateAsDate.after(offerEndDate);

                if (startOverlap && endOverlap) {
                    return true; // Hay solapamiento dentro del rango específico de la oferta
                }
            }
        }

        return false; // No hay solapamiento
    }
}
