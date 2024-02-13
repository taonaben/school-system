import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class StudentLogin {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/schoolsystem";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "!Projectbaby09";

    public static void main(String[] args) {
        databaseAdd();
    }

    public static String Fname;
    public static String Lname;
    public static String studentId;
    public static int age;
    public static String gender;

    public static void getDetails() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your first name: ");
            Fname = scanner.nextLine();

            System.out.print("Enter your last name: ");
            Lname = scanner.nextLine();

            System.out.print("Enter your Student ID: ");
            studentId = scanner.nextLine();

            System.out.print("Enter your age: ");
            age = Integer.parseInt(scanner.nextLine());

            System.out.print("What is your gender?: ");
            gender = scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Input student details and insert into the database
    public static void databaseAdd() {

        getDetails();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            connection.setAutoCommit(false); // Start the transaction

            // Insert into students table
            String sql = "INSERT INTO students_table (stud_Fname, stud_Lname, stud_Id, stud_age, stud_gender) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Fname);
                preparedStatement.setString(2, Lname);
                preparedStatement.setString(3, studentId);
                preparedStatement.setInt(4, age);
                preparedStatement.setString(5, gender);
                preparedStatement.executeUpdate();
            }

            // Insert into subjects table
            String sub_sql = "INSERT INTO subjects_table (stud_Fname, stud_Lname, stud_Id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(sub_sql)) {
                preparedStatement2.setString(1, Fname);
                preparedStatement2.setString(2, Lname);
                preparedStatement2.setString(3, studentId);
                preparedStatement2.executeUpdate();
            }

            // insert into english table
            String eng_sql = "INSERT INTO english (stud_Id) VALUES (?)";
            try (PreparedStatement preparedStatement3 = connection.prepareStatement(eng_sql)) {
                preparedStatement3.setString(1, studentId);
                preparedStatement3.executeUpdate();
            }

            // insert into math table
            String math_sql = "INSERT INTO math (stud_Id) VALUES (?)";
            try (PreparedStatement preparedStatement4 = connection.prepareStatement(math_sql)) {
                preparedStatement4.setString(1, studentId);
                preparedStatement4.executeUpdate();
            }

            // insert into geo table
            String geo_sql = "INSERT INTO geo (stud_Id) VALUES (?)";
            try (PreparedStatement preparedStatement5 = connection.prepareStatement(geo_sql)) {
                preparedStatement5.setString(1, studentId);
                preparedStatement5.executeUpdate();
            }

            // insert into science table
            String scie_sql = "INSERT INTO science (stud_Id) VALUES (?)";
            try (PreparedStatement preparedStatement6 = connection.prepareStatement(scie_sql)) {
                preparedStatement6.setString(1, studentId);
                preparedStatement6.executeUpdate();
            }

            connection.commit();
            System.out.println("Details recorded");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}