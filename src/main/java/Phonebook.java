import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by raiden on 3/15/17.
 */
public class Phonebook {

    private static Scanner userInput = new Scanner(System.in);
    ArrayList<String> menuOptions = new ArrayList<String>();

    private String option1 = " 1 - Add a contact";
    private String option2 = " 2 - Delete a contact";
    private String option3 = " 3 - Search by name";
    private String option4 = " 4 - Search by number"
    private String option5 = " 5 - List all contacts";
    private String option6 = " 6 - Dial a contact";
    private String option7 = " 7 - Export contact list";
    private String option0 = " 0 - Exit";

    public Phonebook() {
        System.out.println("Welcome to your phonebook");
        menuOptions.add(option1);
        menuOptions.add(option2);
        menuOptions.add(option3);
        menuOptions.add(option4);
        menuOptions.add(option5);
        menuOptions.add(option6);
        menuOptions.add(option7);
        menuOptions.add(option0);
        mainMenu();
    }

    public void mainMenu() {
        for (String option : menuOptions) {
            System.out.println(option);
        }
        System.out.println();
        System.out.print("Select an option by number: ");

        try {
            int option = userInput.nextInt();
            userInput.nextLine();
            switch (option) {
                case 1: addContact(); break;
                case 2: deleteContact(); break;
                case 3: findByName(); break;
                case 4: findByNumber(); break;
                //case 5: listAll(); break;
                //case 6: dialContact(); break;
                //case 7: exportList(); break;
                case 0: System.exit(0); break;
                default: break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please use numbers to select an option");
            userInput.nextInt();
        }
        System.out.println("----------- Menu ------------");
        mainMenu();
    }

    public void findByName() {
        System.out.print("Search for: ");
        Contact contact = searchContacts();

        if (contact == null) {
            System.out.println("Sorry, no contacts found by that name");
        } else {
            System.out.println(contact.toString());
        }
    }

    public void addContact() {
        System.out.println("Creating new contact");
        String name = addName();
        int tel = addNumber();

        try {
            PhonebookEngine.insertContact(name, tel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact() {
        System.out.print("Enter contact name to delete: ");
        Contact toDelete = searchContacts();

        if (toDelete == null) {
            System.out.println("No contacts found by that name");
        } else {

        }
    }

    // private methods
    private String addName() {
        String name;

        while (true) {
            System.out.print("Name: ");
            name = userInput.nextLine();

            if (!(name.matches("(([A-Za-z][ ]?){2,30})"))) {
                System.out.println("Invalid name, cannot contain special characters or numbers");
                continue;
            }
            break;
        }
        return name;
    }

    private int addNumber() {
        int tel;

        while (true) {
            System.out.print("Phone number: ");
            try {
                tel = userInput.nextInt();
                int telLength = String.valueOf(tel).length();

                if ((telLength < 6) || (telLength > 15)) {
                    System.out.println("Incorrect phone number length. Try again");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid phone number format. Please do not use spaces or letters");
                userInput.next();
            }
        }
        return tel;
    }

    private Contact searchContacts() {
        String toSearch = userInput.nextLine().toLowerCase();
        Contact contact = null;

        try {
            contact = PhonebookEngine.getContact(toSearch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }

} // End of class
