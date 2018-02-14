package dao;

import entity.Project;

import java.util.List;

public interface ProjectDAO {

    void add(Project project);

    void update(Project project);

    void delete(Project project);

    List<Project> getAll();

    Project getById(long id);
}
