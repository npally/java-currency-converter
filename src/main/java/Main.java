import UserInterface.UserInterface;
import com.mashape.unirest.http.exceptions.UnirestException;
import logic.Logic;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnirestException {
        Scanner reader = new Scanner(System.in);
        UserInterface view = new UserInterface(reader);
        view.ui();

    }
}
