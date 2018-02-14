package service;

import dao.ProjectDAO;
import database.Util;
import entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectService implements ProjectDAO {

    private final static String sqlInsert = "INSERT INTO project(title) VALUES(?)";
    private final static String sqlUpdate = "UPDATE project SET title = (?) WHERE id = (?)";
    private final static String sqlDelete = "DELETE project WHERE id = (?)";

    private final static String sqlSelectById = "SELECT id, title FROM project WHERE id = (?)";
    private final static String sqlSelect = "SELECT id,title FROM project";

    private final Util util;

    public ProjectService(Util util) {
        this.util = util;
    }

    @Override
    public void add(Project project) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setString(1, project.getTitle());
            prSt.executeUpdate();
            try (ResultSet rs = prSt.getGeneratedKeys()) {
                if (rs.next()) {
                    long key = rs.getLong(1);
                    project.setId(key);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Project project) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlUpdate)) {
            prSt.setString(1, project.getTitle());
            prSt.setLong(2, project.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Project project) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlDelete)) {
            prSt.setLong(1, project.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> getAll() {
        List<Project> projectCollection = new ArrayList<>();
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlSelect)) {
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    Project project = new Project();
                    project.setId(rs.getLong("id"));
                    project.setTitle(rs.getString("title"));
                    projectCollection.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectCollection;
    }

    @Override
    public Project getById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlSelectById)) {
            prSt.setLong(1, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    Project project = new Project();
                    project.setId(rs.getLong("id"));
                    project.setTitle(rs.getString("title"));
                    return project;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
