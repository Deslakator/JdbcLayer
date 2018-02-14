package service;

import dao.EmployeeDAO;
import database.Util;
import entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements EmployeeDAO {

    private final static String sqlInsert = "INSERT INTO employee(address_id, name) VALUES(?,?)";
    private final static String sqlUpdate = "UPDATE employee SET address_id = (?), name = (?) WHERE id = (?)";
    private final static String sqlDelete = "DELETE employee WHERE id = (?)";

    private final static String sqlSelectById = "SELECT id, address_id, name FROM employee WHERE id = (?)";
    private final static String sqlSelect = "SELECT id, address_id, name FROM employee";

    private final Util util;

    public EmployeeService(Util util) {
        this.util = util;
    }

    @Override
    public void add(Employee employee) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setLong(1, employee.getAddressId());
            prSt.setString(2, employee.getName());
            prSt.executeUpdate();
            try (ResultSet rs = prSt.getGeneratedKeys()) {
                if (rs.next()) {
                    long key = rs.getLong(1);
                    employee.setId(key);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlUpdate)) {
            prSt.setLong(1, employee.getAddressId());
            prSt.setString(2, employee.getName());
            prSt.setLong(3, employee.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Employee employee) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlDelete)) {
            prSt.setLong(1, employee.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeCollection = new ArrayList<>();
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlSelect)) {
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getLong("id"));
                    employee.setAddressId(rs.getLong("address_id"));
                    employee.setName(rs.getString("name"));
                    employeeCollection.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeCollection;
    }

    @Override
    public Employee getById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlSelectById)) {
            prSt.setLong(1, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getLong("id"));
                    employee.setAddressId(rs.getLong("address_id"));
                    employee.setName(rs.getString("name"));
                    return employee;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
