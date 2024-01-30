
/**
 * MathSub
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ScienceSub {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/schoolsystem";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "!Projectbaby09";

    public static void main(String[] args) {
        databaseAdd();
    }

    private static void databaseAdd() {
        Scanner scanner = new Scanner(System.in);

        // Input student details

        System.out.print("Enter your Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Test 1 score/50: ");
        double test1 = scanner.nextDouble();
        double test1Perc = (test1/50)*100;

        System.out.print("Test 2 score/30: ");
        double test2 = scanner.nextDouble();
        double test2Perc = (test2/30)*100;

        System.out.print("Test 3 score/50: ");
        double test3 = scanner.nextDouble();
        double test3Perc = (test3/50)*100;
        scanner.nextLine(); // Consume the newline character
        
        double overall = ((test1Perc+test1Perc+test3Perc)/300)*100;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish the database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            connection.setAutoCommit(false); // Start the transaction

            String sql = "INSERT INTO science (stud_Id, test1, test2, test3, science) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, studentId);
            preparedStatement.setDouble(2, test1Perc);
            preparedStatement.setDouble(3, test2Perc);
            preparedStatement.setDouble(4, test3Perc);
            preparedStatement.setDouble(5, overall);
            preparedStatement.executeUpdate();

            String scie_sql= "INSERT INTO subjects_table(science) VALUES (?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(scie_sql);

            preparedStatement2.setDouble(1, overall);
            preparedStatement2.executeUpdate();

            connection.commit();
            System.out.println("Details recorded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
