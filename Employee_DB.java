package Mahesh;

import java.sql.*;
import java.util.Scanner;

public class Employee_DB {
    static final String URL = "jdbc:mysql://localhost:3306/company";
    static final String USER = "root";
    static final String PASS = ""; // change if your password is set

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            boolean running = true;

            while (running) {
                System.out.println("\n=== Employee Database ===");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Salary: ");
                        double salary = sc.nextDouble();

                        String insertSQL = "INSERT INTO employee VALUES (?, ?, ?)";
                        PreparedStatement insertStmt = con.prepareStatement(insertSQL);
                        insertStmt.setInt(1, id);
                        insertStmt.setString(2, name);
                        insertStmt.setDouble(3, salary);
                        insertStmt.executeUpdate();
                        System.out.println("Employee added.");
                        break;

                    case 2:
                        String selectSQL = "SELECT * FROM employee";
                        PreparedStatement selectStmt = con.prepareStatement(selectSQL);
                        ResultSet rs = selectStmt.executeQuery();

                        System.out.println("Employee Records:");
                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("id") +
                                               ", Name: " + rs.getString("name") +
                                               ", Salary: ₹" + rs.getDouble("salary"));
                        }
                        break;

                    case 3:
                        System.out.print("Enter ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter new salary: ");
                        double newSalary = sc.nextDouble();

                        String updateSQL = "UPDATE employee SET name = ?, salary = ? WHERE id = ?";
                        PreparedStatement updateStmt = con.prepareStatement(updateSQL);
                        updateStmt.setString(1, newName);
                        updateStmt.setDouble(2, newSalary);
                        updateStmt.setInt(3, updateId);
                        int updated = updateStmt.executeUpdate();
                        if (updated > 0) System.out.println("Employee updated.");
                        else System.out.println("Employee not found.");
                        break;

                    case 4:
                        System.out.print("Enter ID to delete: ");
                        int deleteId = sc.nextInt();

                        String deleteSQL = "DELETE FROM employee WHERE id = ?";
                        PreparedStatement deleteStmt = con.prepareStatement(deleteSQL);
                        deleteStmt.setInt(1, deleteId);
                        int deleted = deleteStmt.executeUpdate();
                        if (deleted > 0) System.out.println("Employee deleted.");
                        else System.out.println("Employee not found.");
                        break;

                    case 5:
                        running = false;
                        con.close();
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        sc.close();
    }
}


