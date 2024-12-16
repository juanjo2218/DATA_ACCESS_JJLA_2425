package com.jjla2425.da.practica;

import com.jjla2425.da.practica.model.DataBaseEntities.ProductsEntity;
import com.jjla2425.da.practica.model.DataBaseEntities.SellerProductsEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Utils {
    public static String getHash(String passwordFieldText)
    {
        try {
            // Obtener una instancia de MessageDigest para MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convertir el String a bytes y pasar al digest
            byte[] messageDigest = md.digest(passwordFieldText.getBytes());

            // Convertir el array de bytes a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                // Convertir cada byte a un valor hexadecimal de dos dígitos
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Retornar el valor hexadecimal
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // En caso de error, lanzar una excepción
            throw new RuntimeException("Error al generar el hash MD5", e);
        }
    }

    public static ArrayList<Integer> getProductsId(ArrayList<ProductsEntity> products)
    {
        ArrayList<Integer> ids = new ArrayList<>();

        for (ProductsEntity product : products) {
            ids.add(product.getProductId());
        }
        return ids;
    }

    public static ArrayList<Integer> getSellersProductsId(ArrayList<SellerProductsEntity> sellerProducts)
    {
        ArrayList<Integer> ids = new ArrayList<>();

        for (SellerProductsEntity sellerProductsEntity: sellerProducts) {
            ids.add(sellerProductsEntity.getProductId());
        }
        return ids;
    }

    public static int getMaxDiscount(int daysDiff,boolean isPro)
    {
        if (daysDiff >= 30 && !isPro)
          return 10;
        else if (daysDiff >= 15 && !isPro)
            return 15;
        else if (daysDiff >= 7)
            return 20;
        else if (daysDiff >= 3)
            return 30;
        return 50;
    }
    public static int getMaxDiscountPro(int daysDiff)
    {
        if (daysDiff >= 7)
            return 20;
        else if (daysDiff >= 3)
            return 30;
        return 50;
    }
    public static void showScreen(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static BigDecimal getPriceAsBigDecimal(String string) {
        try {
            return new BigDecimal(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    @FXML
    public static void changeView(String nameFXML, TextField field) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(nameFXML));
            Stage currentStage = (Stage) field.getScene().getWindow();
            Scene nextScene = new Scene(loader.load());
            currentStage.setScene(nextScene); // Cambiar la escena actual
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumberValid(String phone)
    {
        return phone != null && phone.matches("^\\+?[0-9]{1,3}?[\\s-]?[(]?[0-9]{1,4}[)]?[\\s-]?[0-9]{1,4}[\\s-]?[0-9]{1,4}$");
    }
    public static boolean isEmailValid(String email) {
        return email.isEmpty() || email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    public static boolean isURLValid(String url) {
        return url.isEmpty() || url.matches("^https?:\\/\\/(www\\.)?[\\w-]+(\\.[\\w-]+)+(\\.[a-z]{2,6})(\\/[\\w-]*)*\\/?$");
    }
    public static boolean  isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false; // Si es null o está vacía, no es un número
        }

        try {
            Double.parseDouble(str); // Intenta convertir el string a un número
            return true; // Si no hay excepción, es un número
        } catch (NumberFormatException e) {
            return false; // Si lanza NumberFormatException, no es un número
        }
    }

    public static void saveCIF(String cif)
    {
        PrintWriter printWriter = null;
        try
        {
            printWriter = new PrintWriter(new BufferedWriter(
                    new FileWriter( "lastCIF.txt", false)));
                printWriter.println(cif);
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( printWriter != null )
            {
                printWriter.close();
            }
        }
    }

    public static String getSaveCIF() throws IOException {
        BufferedReader file1 = new BufferedReader(new FileReader("lastCIF.txt"));
        return  file1.readLine();

    }
}
