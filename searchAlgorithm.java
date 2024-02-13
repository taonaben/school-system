import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class searchAlgorithm {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/schoolsystem";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "!Projectbaby09";

    public static void main(String[] args) {
    }

    // Search algorith start!!
    public String nameSearch;
    public boolean loginApproval = false;
    public String get_studId;

    public String getDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        nameSearch = scanner.nextLine();
        scanner.close();
        return nameSearch;
    }

    public void displayer() {

        try (Connection connection1 = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            String selectQuery = "SELECT * FROM students_table WHERE stud_Lname = ? OR stud_Fname = ?";

            PreparedStatement preparedStatement = connection1.prepareStatement(selectQuery); // Use connection1 instead
                                                                                             // of connection
            preparedStatement.setString(1, nameSearch);
            preparedStatement.setString(2, nameSearch);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Searching...\n\n");
            int i = 1;
            while (resultSet.next()) {

                System.out.print("[" + i + "]->");
                System.out.print(resultSet.getString("stud_Fname") + " ");
                System.out.print(resultSet.getString("stud_Lname") + " ");
                get_studId = resultSet.getString("stud_id"); // Retrieve stud_id from the result set
                System.out.print(resultSet.getString("stud_id") + " ");
                System.out.print(resultSet.getInt("stud_age") + " ");
                System.out.print(resultSet.getString("stud_gender") + " ");
                i++;

                loginApproval = true;
            }
        } catch (SQLException e) {
            System.out.println("not found!");
            throw new RuntimeException(e);
        }
    }

    public String getStudentId(String nameSearch) {
        String studentId = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT stud_id FROM students_table WHERE stud_Lname = ? OR stud_Fname = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, nameSearch);
                preparedStatement.setString(2, nameSearch);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        studentId = resultSet.getString("stud_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentId;
    }
    // Search algorith end!!
}
