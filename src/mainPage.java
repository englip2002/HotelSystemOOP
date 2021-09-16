//Lee Seng Wai
//2000401
//DCS2 G1
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import javax.print.event.PrintJobListener;

public final class mainPage {

    private final Customer[] customerArr;
    Scanner mainPage = new Scanner(System.in);

    public static void main(String[] args) {
        new mainPage();
    }

    public mainPage() {
        customerArr = initializeCustomer();
        int option = 0;
        int login;
        Scanner mainPage = new Scanner(System.in);

        do {
            System.out.println("\t\t\t\t\t\t __    __  _____ _______ __ __           ____    ___ _________ __     ____ ");
            System.out.println("\t\t\t\t\t\t|  |  |  |/      \\    __|__|  |         /  __\\  /  /  __\\   __|__|\\  /    |");
            System.out.println("\t\t\t\t\t\t|  |__|  |   /\\   |  |  |__   |        /  /__ \\/  /  /__   |  |__  \\/     |");
            System.out.println("\t\t\t\t\t\t|   __   |  |  |  |  |   __|  |        \\ __  \\   /\\ __  \\  |   __|    /|  |");
            System.out.println("\t\t\t\t\t\t|  |  |  |   \\/   |  |  |__   |__      ___/  /  | ___/  /  |  |__ \\  / |  |");
            System.out.println("\t\t\t\t\t\t|__|  |__|\\______/|__|_____|_____|     \\____/|__| \\____/|__|_____| \\/  |__|");
            do {
                System.out.println();
                System.out.println("Main Menu");
                System.out.println("=========");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Logout");
                System.out.println("4. View and edit Customer profile");
                System.out.println("5. Reservation History");
                drawLine();
                System.out.print("Enter your option (1-4) : ");
                option = mainPage.nextInt();
                mainPage.nextLine();
                if (option < 1 || option > 4) {
                    System.out.println("Invalid input. Please Enter Again!\n");
                }
                if (option == 1) {
                    login();
                    drawLine();
                }
                else if (option == 2) {
                    register();
                    drawLine();
                }        
                else if (option == 3) {
                    logout();
                }
                else if (option == 4) {
                    editProfile();
                }
                else {
                    reservationHistory();
                }        
            } while (option == 1);
        } while (option < 1 || option > 4);
    }

    public void login() {
        String customerID, customerName, customerPassword, customerEmail, dateOfBirth;
        boolean validLogin = false;
        Scanner customerLogin = new Scanner(System.in);
        do {
            System.out.print("Please enter your email : ");
        } while (validLogin == false);
    }

    private Customer[] initializeCustomer() {
        Customer[] custArr = {new Customer("Gordon Ramsay", "1969-09-06", "Gordon69", "gordonramsay69@hotmail.com"),
            new Customer("Mohammad Ali", "1987-11-27", "Alibaba", "mohdali@hotmail.com"),
            new Customer("Ranjeev Singh", "2000-10-08", "RJ2000", "ranjeevsingh@hotmail.com"),
            new Customer("Leong Kah Jun", "2005-05-05", "LEONGKJ", "kahjunleong@hotmail.com"),
            new Customer("Jonathan Wong Chou Jin", "12-12-1975", "jonwong1975", "jonathanwong@hotmail.com")
        };
        return custArr;
    }

    public static Customer register() {
        String registerName;
        String registerDateOfBirth;
        String registerPassword;
        String registerEmail;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter you name : ");
        registerName=scanner.nextLine();
        
        registerDateOfBirth=getDateInput(scanner, "Please enter your Date Of Birth (YYYY-MM-DD): ").toString();
        System.out.print("Please enter you password : ");
        registerPassword=scanner.nextLine();
        System.out.print("Please enter you Email : ");
        registerEmail=scanner.nextLine();
        while(validateEmail(registerEmail)==false){
            System.out.println("Invalid Email! Please Try Again");
            System.out.print("Please enter you Email : ");
            registerEmail=scanner.nextLine();
        }

        Customer customer=new Customer(customerID, customerName, dateOfBirth, customerPassword, customerEmail)
    }

    public static boolean validateEmail(String email){
        boolean validateAt=false;
        boolean validateDot=false;
        for(int i=0;i<email.length();i++){
            if(email.charAt(i)=='@')
                validateAt=true;
            if(email.charAt(i)=='.')
                validateDot=true;
        }

        if(validateAt&&validateDot)
            return true;
        else
            return false;
    }

    public void logout() {
        System.out.println("Thank you for your service!");
    }

    public void reservationHistory() {
        System.out.print("Hello mdfk");
    }

    public void editProfile() {

    }

    public static void drawLine() {
        for (int i = 0; i < 106; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static LocalDate getDateInput(Scanner scanner, String question) {
        LocalDate d = null;
        boolean invalidDate = true;
        do {
            try {
                System.out.print(question);
                String inputDate = scanner.nextLine();
                d = LocalDate.parse(inputDate);
                invalidDate = false;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, please re-enter. ");
            }
        } while (invalidDate);
        return d;
    }
}

