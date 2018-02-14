package dao;

import entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    void add(Employee employee);

    void update(Employee employee);

    void delete(Employee employee);

    List<Employee> getAll();

    Employee getById(long id);
}
