//Lee Seng Wai
//2000401
//DCS2 G1
import java.time.LocalDate;
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
        Customer[] custArr = {new Customer("C0001", "Gordon Ramsay", "06-09-1969", "Gordon69", "gordonramsay69@hotmail.com"),
            new Customer("C0002", "Mohammad Ali", "27-11-1987", "Alibaba", "mohdali@hotmail.com"),
            new Customer("C0003", "Ranjeev Singh", "08-10-2000", "RJ2000", "ranjeevsingh@hotmail.com"),
            new Customer("C0004", "Leong Kah Jun", "05-05-2005", "LEONGKJ", "kahjunleong@hotmail.com"),
            new Customer("C0005", "Jonathan Wong Chou Jin", "12-12-1975", "jonwong1975", "jonathanwong@hotmail.com")
        };
        return custArr;
    }

    public void register() {
        String registerName;
        String registerDateOfBirth;
        String registerPassword;
        String registerEmail;
        boolean validRegister = true;
        Scanner register = new Scanner(System.in);
        do {
            System.out.print("Please enter you ID : ");
            customerID = customerLogin.nextLine();
            for (Customer customerArr1 : customerArr) {
                if (customerID.equals(customerArr1.getCustomerID()) == true) {
                    validLogin = true;
                    System.out.print("Please enter your name : ");
                    customerName = customerLogin.nextLine();
                    while (customerName.equals(customerArr1.getCustomerName()) == false) {
                        System.out.print("\nInvalid name! Please enter you name again! : ");
                        customerName = customerLogin.nextLine();
                    }
                    if(customerName.equals(customerArr1.getCustomerName()) == true) {
                        System.out.print("Please enter your date of birth : ");
                        dateOfBirth = customerLogin.nextLine();
                        while (dateOfBirth.equals(customerArr1.getDateOfBirth()) == false) {
                            System.out.print("\nInvalid date of birth! Please enter your date of birth again! : ");
                            dateOfBirth = customerLogin.nextLine();
                        }
                        if (dateOfBirth.equals(customerArr1.getDateOfBirth()) == true) {
                            System.out.print("Please enter your password : ");
                            customerPassword = customerLogin.nextLine();
                            while (customerPassword.equals(customerArr1.getCustomerPassword()) == false) {
                                System.out.print("\nInvalid password! Please enter your password again! : ");
                                customerPassword = customerLogin.nextLine();
                            }
                            if (customerPassword.equals(customerArr1.getCustomerPassword()) == true) {
                                System.out.print("Please enter your email : ");
                                customerEmail = customerLogin.nextLine();
                                while (customerEmail.equals(customerArr1.getCustomerEmail()) == false) {
                                    System.out.print("\nInvalid email! Please enter your email! : ");
                                    customerEmail = customerLogin.nextLine();
                                }
                            }
                        }
                        System.out.print("Login successfully!!!\n");
                    }
                }
            }
            if (validRegister == false) {
                System.out.println("\nInvalid input! Please enter again!");
            }
        } while (validRegister == false);
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
}

