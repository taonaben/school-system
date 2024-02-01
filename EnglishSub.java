import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EnglishSub {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/schoolsystem";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "!Projectbaby09";

    public static String studentId;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            searchAlgorithm.getDetails();
            searchAlgorithm.displayer();
            studentId = searchAlgorithm.getStudentId(searchAlgorithm.nameSearch);

            if (studentId != null && isStudentRegistered(studentId)) {
                System.out.println("\nStudent ID found: " + studentId);
                System.out.println("Proceeding to edit test scores.");
                editTestScores(studentId, scanner);
            } else {
                System.out.println("\nStudent ID not found. Registering new student.");
                StudentLogin.databaseAdd();
            }
        }
    }

    private static boolean isStudentRegistered(String studentId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT * FROM english WHERE stud_Id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, studentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String studentId1 = studentId;
    public static double test1;

    private static void editTestScores(String studentId1, Scanner scanner) {

        System.out.println(studentId1);
        try {
            System.out.print("Enter Test 1 score/50: ");
            double test1 = getValidatedScore(scanner);

            System.out.print("Enter Test 2 score/30: ");
            double test2 = getValidatedScore(scanner);

            System.out.print("Enter Test 3 score/50: ");
            double test3 = getValidatedScore(scanner);

            double test1Perc = (test1 / 50) * 100;
            double test2Perc = (test2 / 30) * 100;
            double test3Perc = (test3 / 50) * 100;
            double overall = ((test1Perc + test2Perc + test3Perc) / 300) * 100;

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                connection.setAutoCommit(false);

                String updateQuery = "UPDATE english SET test1 = ?, test2 = ?, test3 = ?, english = ? WHERE stud_Id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setDouble(1, test1);
                    preparedStatement.setDouble(2, test2);
                    preparedStatement.setDouble(3, test3);
                    preparedStatement.setDouble(4, overall);
                    preparedStatement.setString(5, studentId);
                    preparedStatement.executeUpdate();
                }

                String eng_sql = "UPDATE subjects_table SET english = ? WHERE stud_id = ?";
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(eng_sql)) {
                    preparedStatement2.setDouble(1, overall);
                    preparedStatement2.setString(2, studentId);
                    preparedStatement2.executeUpdate();
                }

                connection.commit();
                System.out.println("Test scores updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static double getValidatedScore(Scanner scanner) {
        while (true) {
            try {
                double score = Double.parseDouble(scanner.nextLine());
                if (score < 0 || score > 100) {
                    System.out.println("Invalid score. Please enter a value between 0 and 100.");
                } else {
                    return score;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric score.");
            }
        }
    }
}