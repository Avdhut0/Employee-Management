import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static EmployeeDAO employeeDAO;

    public static void main(String[] args) {
        try {
            employeeDAO = new EmployeeDAO();
            int choice;
            do {
                System.out.println("Employee Management System");
                System.out.println("1. Add Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } while (choice != 5);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    private static void addEmployee() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Position: ");
        String position = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        Employee employee = new Employee(name, position, salary);
        try {
            employeeDAO.addEmployee(employee);
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void viewAllEmployees() {
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                for (Employee employee : employees) {
                    System.out.println(employee);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employees: " + e.getMessage());
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Employee existingEmployee = employeeDAO.getEmployeeById(id);
            if (existingEmployee == null) {
                System.out.println("Employee not found!");
                return;
            }

            System.out.print("Enter new Name (current: " + existingEmployee.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter new Position (current: " + existingEmployee.getPosition() + "): ");
            String position = scanner.nextLine();
            System.out.print("Enter new Salary (current: " + existingEmployee.getSalary() + "): ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            existingEmployee.setName(name);
            existingEmployee.setPosition(position);
            existingEmployee.setSalary(salary);

            employeeDAO.updateEmployee(existingEmployee);
            System.out.println("Employee updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

    private static void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            employeeDAO.deleteEmployee(id);
            System.out.println("Employee deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }
}
