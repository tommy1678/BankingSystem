import java.util.Scanner;
public class p1 {
    static int customerid = 100;
    static int accountid = 1000;
    public static void startMenu(){
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 3) {
        System.out.println(" ");
        System.out.println("Welcome to the Self Services Banking System! - Main Menu");
        System.out.println("1. New Customer");
        System.out.println("2. Customer Login");
        System.out.println("3. Exit");

        choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println(" ");
                System.out.println("Please enter name");
                String name = scanner.next();
                System.out.println("Please enter gender (M or F");
                String gender = scanner.next();
                System.out.println("Please enter age");
                int age = scanner.nextInt();
                String age1 = String.valueOf(age);
                System.out.println("Please enter pin");
                int pin = scanner.nextInt();
                String pin1 = String.valueOf(pin);
                BankingSystem.newCustomer(name, gender, age1, pin1);
                System.out.println("Your new id is " + customerid);
                customerid++;
                choice = 0;
            }

            else if (choice == 2) {
                System.out.println(" ");
                System.out.println("Please enter customer id");
                int customerId = scanner.nextInt();
                String customerId1 = String.valueOf(customerId);
                System.out.println("Please enter pin");
                int pin = scanner.nextInt();
                if (customerId == 0 && pin == 0){
                    administratorMenu();
                }
                else if (!BankingSystem.customerExists(customerId1)){
                    System.out.println("Customer does not exist");
                    choice = 0;
                }
                else {
                    customerMenu();
                }
            }

            else if (choice == 3) {
                System.out.println(" ");
                System.out.println("Have a good day!");
                break;
            }
            else {
                System.out.println("unknown command");
                break;

                }
            }
            scanner.close();
        } 

    public static void customerMenu(){
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            while (choice != 7){

            System.out.println("Customer Main Menu");
            System.out.println("1. Open Account");
            System.out.println("2. Close Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Account Summary");
            System.out.println("7. Exit");

            choice = scanner.nextInt();

            if (choice == 1){
                System.out.println("Please enter ID");
                int id1 = scanner.nextInt();
                String id = String.valueOf(id1);
                System.out.println("Please enter type(C for checking and S for saving");
                String type = scanner.next();
                System.out.println("Please enter balance");
                int balance1 = scanner.nextInt();
                String balance = String.valueOf(balance1);
                BankingSystem.openAccount(id, type, balance);
                System.out.println("your new number is " + accountid);
                accountid++;
                choice = 0;
            }

            if (choice == 2){
                System.out.println("Please enter account number");
                String accNum = scanner.next();
                BankingSystem.closeAccount(accNum);
                choice = 0;
            }


            if (choice == 3){
                System.out.println("Please enter account number");
                int accountNum = scanner.nextInt();
                String accNum = String.valueOf(accountNum);
                System.out.println("Please enter deposit amount");
                int amount1 = scanner.nextInt();
                String amount = String.valueOf(amount1);
                BankingSystem.deposit(accNum, amount);
                choice = 0;
            }
                
            if (choice == 4){
                System.out.println("Please enter account number");
                int accountNum = scanner.nextInt();
                String accNum = String.valueOf(accountNum);
                System.out.println("Please enter deposit amount");
                int amount1 = scanner.nextInt();
                String amount = String.valueOf(amount1);
                BankingSystem.withdraw(accNum, amount);
                choice = 0;
            }

            if (choice == 5){
                System.out.println("Please enter source account number");
                int source = scanner.nextInt();
                String srcAccNum = String.valueOf(source);
                System.out.println("Please enter destination account number");
                int destination = scanner.nextInt();
                String destAccNum = String.valueOf(destination);
                System.out.println("Please enter deposit amount");
                int amount1 = scanner.nextInt();
                String amount = String.valueOf(amount1);
                BankingSystem.transfer(srcAccNum, destAccNum, amount);
                choice = 0;
            }

            if (choice == 6){
                System.out.println("Please enter customer id");
                String id = scanner.next();
                BankingSystem.accountSummary(id);
                choice = 0;
            }

            if (choice == 7){
                System.out.println("");
                System.out.println("Goodbye");
                startMenu();
            }
        }
        scanner.close();
    }

        public static void administratorMenu(){
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            while (choice != 4)
            {
                System.out.println("Administrator Menu");
                System.out.println("1. Account Summary");
                System.out.println("2. Report A");
                System.out.println("3. Report B");
                System.out.println("4. Exit");
                choice = scanner.nextInt();

                if (choice == 1){
                    System.out.println("Please enter customer id");
                    String id = scanner.next();
                    BankingSystem.accountSummary(id);
                    choice = 0;
                }

                if (choice == 2){
                    BankingSystem.reportA();
                    choice = 0;
                }

                if (choice == 3){
                    System.out.println("Please select min age");
                    int min1 = scanner.nextInt();
                    String min = String.valueOf(min1);
                    System.out.println("Please select max age");
                    int max1 = scanner.nextInt();
                    String max = String.valueOf(max1);
                    BankingSystem.reportB(min, max);
                    choice = 0;
                }

                if (choice == 4){
                    System.out.println("");
                    System.out.println("Goodbye");
                    startMenu();
                }
            }
            scanner.close();

        }
}
