package dao;

import entity.EmployeeProject;

import java.util.List;

public interface EmployeeProjectDAO {

    void add(EmployeeProject employeeProject);

    void delete(EmployeeProject employeeProject);

    List<EmployeeProject> getAll();

}
