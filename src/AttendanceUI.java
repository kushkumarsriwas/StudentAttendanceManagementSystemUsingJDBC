import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AttendanceUI extends JFrame {

    public AttendanceUI() {
        setTitle("📚 Student Attendance System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel
        JLabel title = new JLabel("Student Attendance Manager", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(52, 152, 219));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));

        JButton addBtn = new JButton("Add Student");
        JButton markBtn = new JButton("Mark Attendance");
        JButton viewBtn = new JButton("View Attendance");
        JButton deleteBtn = new JButton("Delete Student");
        JButton exitBtn = new JButton("Exit");

        Color btnColor = new Color(46, 204, 113);
        addBtn.setBackground(btnColor);
        markBtn.setBackground(btnColor);
        viewBtn.setBackground(btnColor);
        deleteBtn.setBackground(btnColor);
        exitBtn.setBackground(new Color(231, 76, 60));

        buttonPanel.add(addBtn);
        buttonPanel.add(markBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Table Panel
        JTable table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Actions
        addBtn.addActionListener(e -> showAddStudent());
        markBtn.addActionListener(e -> showMarkAttendance());
        deleteBtn.addActionListener(e -> showDeleteStudent());
        exitBtn.addActionListener(e -> System.exit(0));

        viewBtn.addActionListener(e -> {
            try (Connection conn = DatabaseConnector.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT s.id, s.name, a.date, a.status " +
                                 "FROM students s LEFT JOIN attendance a ON s.id = a.student_id"
                 )) {

                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"ID", "Name", "Date", "Status"}, 0
                );

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getString("status")
                    });
                }

                table.setModel(model);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading attendance");
            }
        });

        setVisible(true);
    }

    // Dialogs:
    private void showAddStudent() {
        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField phone = new JTextField();

        Object[] message = {"ID:", id, "Name:", name, "Phone:", phone};

        if (JOptionPane.showConfirmDialog(this, message,
                "Add Student", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            AttendanceManager.addStudent(
                    Integer.parseInt(id.getText()),
                    name.getText(),
                    phone.getText()
            );

            JOptionPane.showMessageDialog(this, "Student Added Successfully");
        }
    }

    private void showMarkAttendance() {
        JTextField id = new JTextField();
        JTextField date = new JTextField();
        JTextField status = new JTextField();

        Object[] message = {"Student ID:", id, "Date (YYYY-MM-DD):", date, "Status:", status};

        if (JOptionPane.showConfirmDialog(this, message,
                "Mark Attendance", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            java.util.Date d = java.sql.Date.valueOf(date.getText());

            AttendanceManager.markAttendance(
                    Integer.parseInt(id.getText()), d, status.getText()
            );

            JOptionPane.showMessageDialog(this, "Attendance Marked");
        }
    }

    private void showDeleteStudent() {
        JTextField id = new JTextField();
        Object[] message = {"Student ID:", id};

        if (JOptionPane.showConfirmDialog(this, message,
                "Delete Student", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            AttendanceManager.deleteStudent(Integer.parseInt(id.getText()));
            JOptionPane.showMessageDialog(this, "Deleted Successfully");
        }
    }

    public static void main(String[] args) {
        new AttendanceUI();
    }
}
