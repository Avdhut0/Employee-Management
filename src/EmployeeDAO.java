import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection connection;

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Avdhutubale261204@8956760668";

    public EmployeeDAO() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add Employee
    public void addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getPosition());
            stmt.setDouble(3, employee.getSalary());
            stmt.executeUpdate();
        }
    }

    // Update Employee
    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET name = ?, position = ?, salary = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getPosition());
            stmt.setDouble(3, employee.getSalary());
            stmt.setInt(4, employee.getId());
            stmt.executeUpdate();
        }
    }

    // Delete Employee
    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // List all Employees
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"));
                employee.setId(rs.getInt("id"));
                employees.add(employee);
            }
        }
        return employees;
    }

    // Find Employee by ID
    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getString("name"),
                            rs.getString("position"),
                            rs.getDouble("salary"));
                }
            }
        }
        return null;
    }
}
