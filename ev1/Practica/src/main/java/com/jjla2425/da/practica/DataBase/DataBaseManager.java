package com.jjla2425.da.practica.DataBase;

import com.jjla2425.da.practica.model.DataBaseEntities.CategoriesEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.ProductsEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.SellerProductsEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.SellersEntity;
import com.jjla2425.da.practica.Utils;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.*;

public class DataBaseManager {

    private static final Logger LOGGER = Logger.getLogger(DataBaseManager.class.getName()); // Logger configured
    private static DataBaseManager instance;

    private static SessionFactory sessionFactory;

    // Private constructor to prevent instantiation from outside the class
    private DataBaseManager() {
        try {
            // Configure the logger to write to a file
            FileHandler fileHandler = new FileHandler("app_logs.log", true); // true to append logs to file
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO); // Set the log level

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
        HttpURLConnection conn = null;
        Scanner scanner = null;

        try {
            URL url = new URL("http://localhost:8080/api-rest/Sellers/" + CIF);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();
                JSONObject jsonObject = new JSONObject(response);

                SellersEntity seller = new SellersEntity();
                seller.ToSellerEntity(jsonObject);
                return seller;
            } else {
                LOGGER.warning("La conexión falló. Código de respuesta: " + responseCode);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el vendedor con CIF: " + CIF, e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public boolean updateSeller(SellersEntity sellersEntity) {
        HttpURLConnection conn = null;
        String jsonInputString = null;
        jsonInputString = sellersEntity.toJSON();
        try {
            URL url = new URL("http://localhost:8080/api-rest/Sellers/" + sellersEntity.getCif());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return false;
    }

    public ArrayList<CategoriesEntity> getCategories() {
        ArrayList<CategoriesEntity> categoriesEntities = new ArrayList<>();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(
                    "http://localhost:8080/api-rest/Categories");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();

                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    categoriesEntities.add(CategoriesEntity.JSONToCategory((JSONObject) jsonArray.get(i)));
                }
            } else
                System.out.println("Connection failed.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return categoriesEntities;
    }

    public ArrayList<ProductsEntity> getProductsByIdCategory(int category_id) {
        Session session = SessionMnager.getInstance().getSession();
        LOGGER.info("Getting products for category ID: " + category_id);
        Query<ProductsEntity> myQuery = session.createQuery("from ProductsEntity where categoryId = :category_id", ProductsEntity.class);
        myQuery.setParameter("category_id", category_id);
        return (ArrayList<ProductsEntity>) myQuery.list();
    }
    public boolean addProductsSeller(SellerProductsEntity product) {
        HttpURLConnection conn = null;
        String jsonInputString = product.toJSON();
        try {
            URL url = new URL("http://localhost:8080/api-rest/SellerProducts");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return false;
    }
    public boolean addOfferProductsSeller(SellerProductsEntity sellerProduct) {
        HttpURLConnection conn = null;
        String jsonInputString = null;
        jsonInputString = sellerProduct.toJSON();
        try {
            URL url = new URL("http://localhost:8080/api-rest/SellerProducts/" + sellerProduct.getSellerProductId());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return false;
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

    public boolean getProductsSellerInThisDate(int sellerId, LocalDate fromDate, LocalDate toDate, int productId,boolean isPro) {
        LOGGER.info("Checking date overlaps for seller's products...");
        int offerproductsmax = isPro ? 3 : 1;
        int offerproductsquantity = 0;
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
            if (offerStartDate != null && offerEndDate == null)
            {
                if (!toDateAsDate.before(offerStartDate))
                {
                    offerproductsquantity++;

                }
            }
            if (offerStartDate == null && offerEndDate != null) {
                if (!fromDateAsDate.after(offerEndDate)) {
                    offerproductsquantity++;
                }
            }
            if (offerStartDate != null && offerEndDate != null) {
                boolean startOverlap = !toDateAsDate.before(offerStartDate);
                boolean endOverlap = !fromDateAsDate.after(offerEndDate);

                if (startOverlap && endOverlap)
                {
                    offerproductsquantity++;
                }
            }
        }
        LOGGER.info("No date overlaps found for the seller's products.");
        return offerproductsquantity >= offerproductsmax;
    }
}