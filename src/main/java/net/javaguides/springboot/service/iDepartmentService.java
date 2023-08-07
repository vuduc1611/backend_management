package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;

import java.util.List;

public interface iDepartmentService {
    List<Department> findAll();
    Department create(Department department);
    Department update(Department department);
    void delete(Integer id);
    List<Employee> fillEmployeeByDept(Integer id);
    void deleteMany(List<Integer> ids);
}
