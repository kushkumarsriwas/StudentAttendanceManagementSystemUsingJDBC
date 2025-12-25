import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("\n🎓 Student Attendance System");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View Attendance");
            System.out.println("4. Exit");
            System.out.println("5. Delete Student");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Add Student
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student phone: ");
                    String phone = scanner.nextLine();
                    AttendanceManager.addStudent(id, name, phone);
                    break;
                case 2:
                    // Mark Attendance
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter attendance date (YYYY-MM-DD): ");
                    String dateString = scanner.nextLine();
                    // Parse the date string to java.util.Date
                    java.util.Date date = java.sql.Date.valueOf(dateString); // Convert to java.util.Date
                    System.out.print("Enter attendance status (Present/Absent): ");
                    String status = scanner.nextLine();
                    AttendanceManager.markAttendance(studentId, date, status); // Pass all three arguments
                    break;
                case 3:
                    // View Attendance
                    AttendanceManager.viewAttendance();
                    break;
                case 4:
                    // Exit
                    System.out.println("Exiting system...");
                    scanner.close();
                    System.exit(0);
                    break;
                case 5:
                    // Delete Student
                    System.out.print("Enter student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    AttendanceManager.deleteStudent(deleteId);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
