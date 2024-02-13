

import java.util.*;

public class Students {

    public static Scanner sc = new Scanner(System.in); 
    public static String studentId;
    public static void main(String[] args) {
        searchAlgorithm search = new searchAlgorithm();
        search.getDetails();
        studentId = search.getStudentId(search.nameSearch);
        studentId = studentId.toString();
        getMarks();
    }

    public static double test1, test2, test3;

    public static double getMarks() {

        System.out.println(studentId);
        
        System.out.print("Enter Test 1 score/50: ");
        test1 = Double.parseDouble(sc.nextLine());
        System.out.print("Enter Test 2 score/30: ");
        test2 = Double.parseDouble(sc.nextLine());
        System.out.print("Enter Test 3 score/50: ");
        test3 = Double.parseDouble(sc.nextLine());

        System.out.println(test1+","+test2+","+test3);

        return test1+test2+test3;
    }
}