package net.javaguides.springboot.service;

import net.javaguides.springboot.Dto.EmployeeResponse;
import net.javaguides.springboot.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface iEmployeeService {
//    Page<Employee> findEmployeesByPageWithFilters(Pageable pageable, int id, String fname, String lname);

    Page<Employee> findEmployeesByPageWithFilters(String sortBy, String sortDir, Integer page, Integer size, Integer id, String fname,
                                                  String lname, Integer departmentId, Integer positionId);

    Employee findOne(Integer id);
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Integer id);
    void deleteMany(List<Integer> ids);
}
