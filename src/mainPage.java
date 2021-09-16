//Lee Seng Wai
//2000401
//DCS2 G1
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class mainPage {

    public static void main(String[] args) {
        Scanner mainPage = new Scanner(System.in);
        Customer[] customerArr = new Customer[100];
        customerArr=initializeCustomer();
        mainBanner();

        int option = 0;
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

            if (option < 1 || option > 4) {
                System.out.println("Invalid input. Please Enter Again!\n");
            }
            if (option == 1) {
                login();
                drawLine();
            } else if (option == 2) {
                customerArr[Customer.getCustomerCount()+1] = register();
                drawLine();
            } else if (option == 3) {
                logout();
            } else if (option == 4) {
                editProfile();
            } else if (option == 5) {
                reservationHistory();
            } else {
                System.out.println("Invalid input. Please Enter Again!\n");
            }
        } while (option < 1 || option > 4);
    }

    public static void mainBanner() {
        System.out.println("\t\t\t\t\t\t __    __  _____ _______ __ __           ____    ___ _________ __     ____ ");
        System.out
                .println("\t\t\t\t\t\t|  |  |  |/      \\    __|__|  |         /  __\\  /  /  __\\   __|__|\\  /    |");
        System.out
                .println("\t\t\t\t\t\t|  |__|  |   /\\   |  |  |__   |        /  /__ \\/  /  /__   |  |__  \\/     |");
        System.out
                .println("\t\t\t\t\t\t|   __   |  |  |  |  |   __|  |        \\ __  \\   /\\ __  \\  |   __|    /|  |");
        System.out.println("\t\t\t\t\t\t|  |  |  |   \\/   |  |  |__   |__      ___/  /  | ___/  /  |  |__ \\  / |  |");
        System.out
                .println("\t\t\t\t\t\t|__|  |__|\\______/|__|_____|_____|     \\____/|__| \\____/|__|_____| \\/  |__|");
    }

    public static void login() {
        String customerID, customerName, customerPassword, customerEmail, dateOfBirth;
        boolean validLogin = false;
        Scanner customerLogin = new Scanner(System.in);
        do {
            System.out.print("Please enter your email : ");
        } while (validLogin == false);
    }

    private static Customer[] initializeCustomer() {
        Customer[] custArr = new Customer[100];
        custArr[0]=new Customer("Gordon Ramsay", LocalDate.of(1996, 9, 6), "Gordon69", "gordonramsay69@hotmail.com");
        custArr[1]=new Customer("Mohammad Ali", LocalDate.of(1987, 12, 14), "Alibaba", "mohdali@hotmail.com");
        custArr[2]=new Customer("Ranjeev Singh", LocalDate.of(2000, 10, 18), "RJ2000", "ranjeevsingh@hotmail.com");
        custArr[3]=new Customer("Leong Kah Jun",LocalDate.of(2005, 5, 05) , "LEONGKJ", "kahjunleong@hotmail.com");
        custArr[4]=new Customer("Jonathan Wong Chou Jin", LocalDate.of(2001, 5, 30), "jonwong1975", "jonathanwong@hotmail.com");

        return custArr;
    }

    public static Customer register() {
        String registerName;
        LocalDate registerDateOfBirth;
        String registerPassword;
        String registerEmail;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter you name : ");
        registerName = scanner.nextLine();

        registerDateOfBirth = getDateInput(scanner, "Please enter your Date Of Birth (YYYY-MM-DD): ");
        
        while(registerDateOfBirth.isAfter(LocalDate.now())){
            System.out.println("Invalid Date! Please Re-enter.");
            registerDateOfBirth = getDateInput(scanner, "Please enter your Date Of Birth (YYYY-MM-DD): ");
        }
        System.out.print("Please enter you password : ");
        registerPassword = scanner.nextLine();
        System.out.print("Please enter you Email : ");
        registerEmail = scanner.nextLine();
        while (validateEmail(registerEmail) == false) {
            System.out.println("Invalid Email! Please Try Again");
            System.out.print("Please enter you Email : ");
            registerEmail = scanner.nextLine();
        }

        Customer customer = new Customer(registerName, registerDateOfBirth, registerPassword, registerEmail);
        return customer;
    }

    public static boolean validateEmail(String email) {
        boolean validateAt = false;
        boolean validateDot = false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                validateAt = true;
            if (email.charAt(i) == '.')
                validateDot = true;
        }

        if (validateAt && validateDot)
            return true;
        else
            return false;
    }

    public static void logout() {
        System.out.println("Thank you for your service!");
    }

    public static void reservationHistory() {
        System.out.print("Hello mdfk");
    }

    public static void editProfile() {

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
