package com.company;

import com.company.services.Service;

import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("utilDate:" + utilDate);
        System.out.println("sqlDate:" + sqlDate);
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("0 - For CSV Menu");
            System.out.println("1 - For Database Menu");
            System.out.println("2 - Exit");
            int option;
            while(true){
                String line = scanner.nextLine();
                try {
                    option = Integer.parseInt(line);
                    if (option >= 0 && option <= 2) {
                        break;
                    } else {
                        System.out.println("Enter a number between 0 and 4");
                    }
                } catch (Exception e){
                    System.out.println("Enter a number between 0 and 2");
                }
            }
            if(option == 0){
                Service service = Service.getInstance();
                service.menu();
            } else if(option == 1) {
                Service service = Service.getInstance();
                service.databaseMenu();
            } else{
                break;
            }
        }
    }
}
