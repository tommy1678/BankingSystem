import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	// JDBC Objects
	private static Connection con;
	private static PreparedStatement stmt;
	private static ResultSet rs;

	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties();						// Create a new Properties object
			FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
			props.load(input);										// Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver");				// Load the driver
			url = props.getProperty("jdbc.url");						// Load the url
			username = props.getProperty("jdbc.username");			// Load the username
			password = props.getProperty("jdbc.password");			// Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin)
	{
    	try {
    		con = DriverManager.getConnection(url,username,password);
			System.out.println(":: CREATE NEW CUSTOMER - RUNNING");
			int age1 = Integer.parseInt(age);
        	int pin1 = Integer.parseInt(pin);

        	stmt = con.prepareStatement("insert into p1.customer values (DEFAULT,?,?,?,?)");
        	stmt.setString(1, name);
        	stmt.setString(2, gender);
       	 	stmt.setInt(3, age1);
       	 	stmt.setInt(4, pin1);

        	stmt.executeUpdate();
        	con.close();
        	stmt.close();

		System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
		}
  		catch (Exception e) {
    		System.out.println("Error occurred");
  		}
  	}

	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount)
	{
    try {
      con = DriverManager.getConnection(url, username, password);
      System.out.println(":: OPEN ACCOUNT - RUNNING");

      int id1 = Integer.parseInt(id);
      int amount1 = Integer.parseInt(amount);

      stmt = con.prepareStatement("insert into p1.account values (DEFAULT,?,?,?,?)");
      stmt.setInt(1, id1);
      stmt.setInt(2, amount1);
      stmt.setString(3, type);
      stmt.setString(4, String.valueOf('A'));
      
	  stmt.executeUpdate();
      con.close();
      stmt.close();

  	System.out.println(":: OPEN ACCOUNT - SUCCESS");
	    }
     catch (Exception e) {
      System.out.println("Error occurred");
     }
  }

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum)
	{
  try {
    con = DriverManager.getConnection(url, username, password);
	System.out.println(":: CLOSE ACCOUNT - RUNNING");
    int accNum1 = Integer.parseInt(accNum);

    stmt = con.prepareStatement("update p1.account set balance = ? where number = ?");
    stmt.setInt(1, 0);
    stmt.setInt(2, accNum1);
    stmt.executeUpdate();

    stmt = con.prepareStatement("update p1.account set status = ? where number = ?");
    stmt.setString(1, String.valueOf('I'));
    stmt.setInt(2, accNum1);
    stmt.executeUpdate();

    con.close();
    stmt.close();

		System.out.println(":: CLOSE ACCOUNT - SUCCESS");
	}
  catch (Exception e) {
   System.out.println("Error occurred");
  }
  }

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount)
	{
    try {
    con = DriverManager.getConnection(url, username, password);
	System.out.println(":: DEPOSIT - RUNNING");
    int accNum1 = Integer.parseInt(accNum);
    int amount1 = Integer.parseInt(amount);
    int balance = 0;

    stmt = con.prepareStatement("select balance from p1.account where number = ?");
    stmt.setInt(1, accNum1);

    rs = stmt.executeQuery();
    while (rs.next()) {
      balance = rs.getInt(1);
    }

    amount1 += balance;

    stmt = con.prepareStatement("update p1.account set balance = ? where number = ?");
    stmt.setInt(1,amount1);
    stmt.setInt(2,accNum1);
    stmt.executeUpdate();

    con.close();
    stmt.close();
    rs.close();
	System.out.println(":: DEPOSIT - SUCCESS");
	}
  	catch (Exception e) {
   		System.out.println("Error occurred");
 	 	}
  }

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount)
	{
    try {
    con = DriverManager.getConnection(url, username, password);
    System.out.println(":: WITHDRAW - RUNNING");
    int accNum1 = Integer.parseInt(accNum);
    int amount1 = Integer.parseInt(amount);
    int balance = 0;

    stmt = con.prepareStatement("select balance from p1.account where number = ?");
    stmt.setInt(1, accNum1);

    rs = stmt.executeQuery();
    while (rs.next()) {
      balance = rs.getInt(1);
    }
	
    if (amount1 > balance) {
      System.out.println("NOT ENOUGH MONEY");
    }

    amount1 = balance - amount1;

    stmt = con.prepareStatement("update p1.account set balance = ? where number = ?");
    stmt.setInt(1,amount1);
    stmt.setInt(2,accNum1);
    stmt.executeUpdate();

    con.close();
    stmt.close();
    rs.close();
    System.out.println(":: WITHDRAW - SUCCESS");
  }
  	catch (Exception e) {
   	System.out.println("Error occurred");
  	}
  }


	/**
	 * Transfer amount from source account to destination account.
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount)
	{
  try {
    	con = DriverManager.getConnection(url, username, password);
		System.out.println(":: TRANSFER - RUNNING");
    	int srcAccNum1 = Integer.parseInt(srcAccNum);
   	 	int destAccNum1 = Integer.parseInt(destAccNum);
    	int amount1 = Integer.parseInt(amount);
    	int balance = 0;

    	stmt = con.prepareStatement("select balance from p1.account where number = ?");
   		stmt.setInt(1, srcAccNum1);

    	rs = stmt.executeQuery();
   		while (rs.next()) {
      		balance = rs.getInt(1);
    		}
   		if (amount1 > balance) {
      		System.out.println("NOT ENOUGH MONEY");
    	}

    	balance -= amount1;

    	stmt = con.prepareStatement("update p1.account set balance = ? where number = ?");
   	 	stmt.setInt(1, balance);
    	stmt.setInt(2, srcAccNum1);
    	stmt.executeUpdate();

    	stmt = con.prepareStatement("select balance from p1.account where number = ?");
    	stmt.setInt(1, destAccNum1);

    	rs = stmt.executeQuery();
    	while (rs.next()) {
      		balance = rs.getInt(1);
    	}

    	balance += amount1;
    	stmt = con.prepareStatement("update p1.account set balance = ? where number = ?");
    	stmt.setInt(1,balance);
    	stmt.setInt(2,destAccNum1);
    	stmt.executeUpdate();

    	con.close();
    	stmt.close();
    	rs.close();
		System.out.println(":: TRANSFER - SUCCESS");
	}
  		catch (Exception e) {
   		System.out.println("Error occurred");
  	}
  }


	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID)
	{
  	try {
    con = DriverManager.getConnection(url, username, password);
	System.out.println(":: ACCOUNT SUMMARY - RUNNING");
    int cusID1 = Integer.parseInt(cusID);
	int total = 0;

    System.out.println("NUMBER  BALANCE");
    System.out.println("---------------");

    stmt = con.prepareStatement("select number, balance from p1.account where id = ?");
    stmt.setInt(1, cusID1);
    rs = stmt.executeQuery();

    while (rs.next()) {
		int accNumber = rs.getInt(1);
		int accBalance = rs.getInt(2);

      total += accBalance;
      System.out.println(accNumber + "  " + accBalance);
    }
    System.out.println("---------------");
    System.out.println("TOTAL     " + total);

    con.close();
    stmt.close();
    rs.close();

	System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
	}
  catch (Exception e) {
   System.out.println("Error occurred");
  }
  }

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static void reportA()
	{
  try {
		System.out.println(":: REPORT A - RUNNING");
		con = DriverManager.getConnection(url, username, password);
    System.out.println("ID    NAME     GENDER AGE     TOTAL");
    System.out.println("----------- --------------- ------ ----------- -----------");
    stmt = con.prepareStatement("select p1.customer.id, name, gender, age, sum(balance) as total from p1.account, p1.customer where p1.account.id = p1.customer.id group by p1.customer.id, name, gender, age order by total desc");
    rs = stmt.executeQuery();

    while (rs.next()) {
    int id = rs.getInt(1);
    String name = rs.getString(2);
    String gender = rs.getString(3);
    int age = rs.getInt(4);
    int total = rs.getInt(5);
    System.out.println(id + "   " + name + "    " + gender + "    " + age + "     " + total);
    }
    con.close();
    stmt.close();
    rs.close();

		System.out.println(":: REPORT A - SUCCESS");
	}
  catch (Exception e) {
   System.out.println("Error occurred");
  }
  }


	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max)
	{
  try {
		System.out.println(":: REPORT B - RUNNING");
		con = DriverManager.getConnection(url, username, password);
    System.out.println("AVERAGE");
    System.out.println("-----------");

    int sum = 0;
    int count = 0;
    int min1 = Integer.parseInt(min);
    int max1 = Integer.parseInt(max);

    stmt = con.prepareStatement("select sum(balance) from p1.account, p1.customer where age <= ? and age >= ? and p1.account.id = p1.customer.id");
    stmt.setInt(1, max1);
    stmt.setInt(2, min1);
    rs = stmt.executeQuery();

    while (rs.next()) {
      int balance = rs.getInt(1);
      sum += balance;
    }

    stmt = con.prepareStatement("select count(id) from p1.customer where age <= ? and age >= ?");
    stmt.setInt(1, max1);
    stmt.setInt(2, min1);
    rs = stmt.executeQuery();

    while (rs.next()) {
      count = rs.getInt(1);
    }

    int average = sum/count;
    System.out.println(average);

    con.close();
    stmt.close();
    rs.close();

		System.out.println(":: REPORT B - SUCCESS");
	}
  catch (Exception e) {
   System.out.println("Error occurred");
  }
  }
}
