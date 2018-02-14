package service;

import dao.EmployeeProjectDAO;
import database.Util;
import entity.EmployeeProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeProjectService implements EmployeeProjectDAO {

    private final static String sqlInsert = "INSERT INTO employee_project(employee_id, project_id) VALUES(?,?)";
    private final static String sqlDelete = "DELETE employee_project WHERE employee_id = (?) AND project_id = (?)";
    private final static String sqlSelect = "SELECT employee_id, project_id FROM employee_project";

    private final Util util;

    public EmployeeProjectService(Util util) {
        this.util = util;
    }


    @Override
    public void add(EmployeeProject employeeProject) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlInsert)) {
            prSt.setLong(1, employeeProject.getEmployeeId());
            prSt.setLong(2, employeeProject.getProjectId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(EmployeeProject employeeProject) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlDelete)) {
            prSt.setLong(1, employeeProject.getEmployeeId());
            prSt.setLong(2, employeeProject.getProjectId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EmployeeProject> getAll() {
        List<EmployeeProject> employeeProjectCollection = new ArrayList<>();
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlSelect);
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                EmployeeProject employeeProject = new EmployeeProject();
                employeeProject.setEmployeeId(rs.getLong("employee_id"));
                employeeProject.setProjectId(rs.getLong("project_id"));
                employeeProjectCollection.add(employeeProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeProjectCollection;
    }
}
