package UserInterface;

import com.mashape.unirest.http.exceptions.UnirestException;
import logic.Logic;

import java.util.Scanner;

public class UserInterface {

    private Scanner reader;
    private Logic converter;

    public UserInterface(Scanner scanner) {
        this.reader = scanner;
        this.converter = new Logic();
    }

    public void ui() throws UnirestException {
        menu();
        String response = reader.nextLine();
        while (!response.equals("quit")) {
            if (response.equals("1")) {
                converter.printCodes();
                lineBreak();
            } else if (response.equals("2")) {
                System.out.println("Please enter your base currency: ");
                String base = reader.nextLine();
                converter.getAllRates(base);
                lineBreak();
            } else if (response.equals("3")) {
                System.out.println("Please enter your base currency: ");
                String base = reader.nextLine();
                System.out.println("Please enter your converted to currency: ");
                String other = reader.nextLine();
                converter.getSpecificRates(base, other);
                lineBreak();
            } else if (response.equals("4")) {
                System.out.println("Please enter your base currency: ");
                String base = reader.nextLine();
                System.out.println("Please enter your converted to currency: ");
                String other = reader.nextLine();
                System.out.println("Please enter the converstion amount: ");
                double amount = Double.parseDouble(reader.nextLine());
                converter.getAmount(base, other, amount);
                lineBreak();
            } else {
                System.out.println("Please enter a valid input");
                lineBreak();
            }
            menu();
            response = reader.nextLine();
        }
        System.out.println("Thank you for using the currency converter.");
    }

    public void menu() {
        System.out.println("Welcome to the Currency Converter");
        System.out.println("Press 1 to see all currency codes");
        System.out.println("Press 2 to see all currency rates");
        System.out.println("Press 3 to see specific currency rates");
        System.out.println("Press 4 to convert an amount to another currency");
        System.out.println("Enter quit to leave the program");
    }

    public void lineBreak() {
        System.out.println("-------------------");
        System.out.println("-------------------");
    }
}
