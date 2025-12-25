import java.sql.*;
import java.util.*;
public class AttendanceManager {
    // Method to add a student to the database
    public static void addStudent(int id, String name, String phone) {
        String query = "INSERT INTO students (id, name, phone) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, phone);
            stmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to mark attendance for a student on a specific date
    public static void markAttendance(int studentId, java.util.Date date, String status) {
        String query = "INSERT INTO attendance (student_id, date, status) VALUES (?, ?, ?)";

        // Convert java.util.Date to java.sql.Date for SQL compatibility
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setDate(2, sqlDate);  // Set the java.sql.Date
            stmt.setString(3, status);
            stmt.executeUpdate();
            System.out.println("Attendance marked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to view attendance of a student, based on option
    public static void viewAttendance() {
        Scanner scanner = new Scanner(System.in);


            System.out.println("\n📅 Detailed Attendance:");
            // Fetch and display all attendance records for each student
            String query = "SELECT s.id, s.name, a.date, a.status FROM students s JOIN attendance a ON s.id = a.student_id";
            try (Connection conn = DatabaseConnector.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    int studentId = rs.getInt("id");
                    String name = rs.getString("name");
                    java.sql.Date date = rs.getDate("date");
                    String status = rs.getString("status");

                    System.out.println("\n🧑 Student ID: " + studentId + " | Name: " + name);
                    System.out.println("📌 " + date + " - " + status);
                }
            } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    // Method to delete a student from the database
    public static void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student with ID " + studentId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}