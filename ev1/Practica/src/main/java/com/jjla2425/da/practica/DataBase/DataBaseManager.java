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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseManager {

    private static final Logger LOGGER = Logger.getLogger(DataBaseManager.class.getName()); // Logger configured
    private static DataBaseManager instance;

    private static SessionFactory sessionFactory;

    // Private constructor to prevent instantiation from outside the class
    private DataBaseManager() {
        try {
            LOGGER.info("Initializing SessionFactory for Hibernate...");
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(SellersEntity.class)
                    .buildSessionFactory();
            LOGGER.info("SessionFactory initialized successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing SessionFactory", e);
        }
    }

    // Public static method to get the unique instance
    public static synchronized DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public SellersEntity getSellerByCIF(String CIF) {
        Session session = SessionMnager.getInstance().getSession();
        LOGGER.info("Getting seller by CIF: " + CIF);
        Query<SellersEntity> myQuery = session.createQuery("from SellersEntity where cif = :cif", SellersEntity.class);
        myQuery.setParameter("cif", CIF);
        try {
            return myQuery.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warning("No seller found with CIF: " + CIF);
            return null;
        }
    }

    public ArrayList<CategoriesEntity> getCategories() {
        Session session = SessionMnager.getInstance().getSession();
        LOGGER.info("Getting all categories...");
        Query<CategoriesEntity> myQuery = session.createQuery("from CategoriesEntity", CategoriesEntity.class);
        return (ArrayList<CategoriesEntity>) myQuery.list();
    }

    public ArrayList<ProductsEntity> getProductsByIdCategory(int category_id) {
        Session session = SessionMnager.getInstance().getSession();
        LOGGER.info("Getting products for category ID: " + category_id);
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where categoryId = :category_id", ProductsEntity.class);
        myQuery.setParameter("category_id", category_id);
        return (ArrayList<ProductsEntity>) myQuery.list();
    }

    public CategoriesEntity getCategorieEntityByName(String name) {
        Session session = SessionMnager.getInstance().getSession();
        LOGGER.info("Getting category by name: " + name);
        Query<CategoriesEntity> myQuery = session.createQuery("from CategoriesEntity where categoryName = :name", CategoriesEntity.class);
        myQuery.setParameter("name", name);
        try {
            return myQuery.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warning("No category found with name: " + name);
            return null;
        }
    }

    public ProductsEntity getProductByIdProduct(int product_id) {
        Session session = SessionMnager.getInstance().getSession();
        LOGGER.info("Getting product by ID: " + product_id);
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where productId = :product_id", ProductsEntity.class);
        myQuery.setParameter("product_id", product_id);
        try {
            return myQuery.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warning("No product found with ID: " + product_id);
            return null;
        }
    }

    public ArrayList<SellerProductsEntity> getProductsSellerByCategory(String CIF, int category_id) {
        LOGGER.info("Getting seller products by category...");
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

    public ArrayList<ProductsEntity> convertSellerProductsEntityToProductsEntity(ArrayList<SellerProductsEntity> sellerProducts) {
        LOGGER.info("Converting SellerProductsEntity to ProductsEntity...");
        ArrayList<Integer> productsSellerId = Utils.getSellersProductsId(sellerProducts);
        Session session = SessionMnager.getInstance().getSession();
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where productId in :product_id", ProductsEntity.class);
        myQuery.setParameter("product_id", productsSellerId);
        return (ArrayList<ProductsEntity>) myQuery.list();
    }

    public ArrayList<SellerProductsEntity> getProductsSellerActive(int idseller) {
        LOGGER.info("Getting active products for seller with ID: " + idseller);
        Session session = SessionMnager.getInstance().getSession();
        String sql = "SELECT * FROM jjla_get_sellerproductsactive(:idSeller)";
        Query<SellerProductsEntity> myQuery = session.createNativeQuery(sql, SellerProductsEntity.class);
        myQuery.setParameter("idSeller", idseller);
        return (ArrayList<SellerProductsEntity>) myQuery.list();
    }

    public ArrayList<ProductsEntity> getProductsRemainingSellerWithCategoryId(int idseller, int categoryId) {
        LOGGER.info("Getting remaining products for seller ID: " + idseller + " and category ID: " + categoryId);
        Session session = SessionMnager.getInstance().getSession();
        String sql = "SELECT * FROM jjla_GetProductsSellerRemaining(:idSeller,:idCategory)";
        Query<ProductsEntity> myQuery = session.createNativeQuery(sql, ProductsEntity.class);
        myQuery.setParameter("idSeller", idseller);
        myQuery.setParameter("idCategory", categoryId);
        return (ArrayList<ProductsEntity>) myQuery.list();
    }

    public void addProductsSeller(SellerProductsEntity product) {
        LOGGER.info("Adding new product to the seller...");
        try (Session session = SessionMnager.getInstance().getSession()) {
            session.beginTransaction();
            session.persist(product);
            session.getTransaction().commit();
            LOGGER.info("Product added successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding product to the seller.", e);
        }
    }

    public void addOfferProductsSeller(SellerProductsEntity sellerProduct) {
        LOGGER.info("Adding offer to the seller's product...");
        Session session = SessionMnager.getInstance().getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (sellerProduct != null) {
                session.merge(sellerProduct);
            }
            transaction.commit();
            LOGGER.info("Offer added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error adding offer to the seller's product.", e);
        } finally {
            session.close();
        }
    }

    public void updateSeller(SellersEntity sellersEntity) {
        LOGGER.info("Updating seller information...");
        Session session = SessionMnager.getInstance().getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (sellersEntity != null) {
                session.merge(sellersEntity);
            }
            transaction.commit();
            LOGGER.info("Seller information updated successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating seller information.", e);
        } finally {
            session.close();
        }
    }

    public ProductsEntity getProductsById(int idProduct) {
        LOGGER.info("Getting product by ID: " + idProduct);
        Session session = SessionMnager.getInstance().getSession();
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where productId = :idProduct", ProductsEntity.class);
        myQuery.setParameter("idProduct", idProduct);
        try {
            return myQuery.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warning("No product found with ID: " + idProduct);
            return null;
        }
    }

    public SellerProductsEntity getProductSeller(String CIF, int productId) {
        LOGGER.info("Getting seller's product...");
        SellersEntity seller = getSellerByCIF(CIF);
        Session session = SessionMnager.getInstance().getSession();
        Query<SellerProductsEntity> myQuery = session.createQuery("from SellerProductsEntity where sellerId = :idSeller " +
                "and productId = :productId", SellerProductsEntity.class);
        myQuery.setParameter("productId", productId);
        myQuery.setParameter("idSeller", seller.getSellerId());
        try {
            return myQuery.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warning("No seller's product found with ID: " + productId);
            return null;
        }
    }

    public boolean getProductsSellerInThisDate(int sellerId, LocalDate fromDate, LocalDate toDate, int productId) {
        LOGGER.info("Checking date overlaps for seller's products...");
        ArrayList<SellerProductsEntity> productsSeller = DataBaseManager.getInstance().getProductsSellerActive(sellerId);
        Date fromDateAsDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date toDateAsDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (SellerProductsEntity productEntity : productsSeller) {
            if (productEntity.getProductId() == productId) {
                continue;
            }
            Date offerStartDate = productEntity.getOfferStartDate();
            Date offerEndDate = productEntity.getOfferEndDate();

            if (offerStartDate == null && offerEndDate == null) {
                continue;
            }
            if (offerStartDate != null && offerEndDate == null) {
                if (!toDateAsDate.before(offerStartDate)) {
                    return true;
                }
            }
            if (offerStartDate == null && offerEndDate != null) {
                if (!fromDateAsDate.after(offerEndDate)) {
                    return true;
                }
            }
            if (offerStartDate != null && offerEndDate != null) {
                boolean startOverlap = !toDateAsDate.before(offerStartDate);
                boolean endOverlap = !fromDateAsDate.after(offerEndDate);

                if (startOverlap && endOverlap) {
                    return true;
                }
            }
        }
        LOGGER.info("No date overlaps found for the seller's products.");
        return false;
    }
}