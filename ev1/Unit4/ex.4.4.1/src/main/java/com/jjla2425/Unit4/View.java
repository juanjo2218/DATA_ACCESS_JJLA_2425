package com.jjla2425.Unit4;

import java.util.Scanner;

public class View {

    private Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public String displayMenu()
    {
        System.out.println("Write (E) for CRUD in table employees,(D) to department,(O) to exit");
        return scanner.nextLine();
    }

    public String displayCRUDptions() {
        System.out.println("Choose one:");
        System.out.println("(S) Select");
        System.out.println("(D) Delete");
        System.out.println("(U) Update");
        System.out.println("(I) Insert");
        System.out.println("(E) Exit");
        return scanner.nextLine();
    }

    public String getUserInput() {
        return scanner.nextLine().toUpperCase(); // Convertir a mayúsculas para simplificar la comparación
    }

    public void closeScanner() {
        scanner.close();
    }

    public int deletemenu()
    {
        System.out.println("Write code from employee to delete");
        return scanner.nextInt();
    }
}
