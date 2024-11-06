package com.jjla2425.da.practica;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
