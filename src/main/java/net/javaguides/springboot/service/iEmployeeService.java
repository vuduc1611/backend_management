package net.javaguides.springboot.service;
import net.javaguides.springboot.model.Employee;
import org.springframework.data.domain.Page;
import java.util.List;

public interface iEmployeeService {

    List<Employee> findAllData();

    Page<Employee> findEmployeesByPageWithFilters(String sortBy, String sortDir, Integer page, Integer size, Integer id, String fname,
                                                  String lname, List<Integer> departmentIds, List<Integer> positionIds);

    Employee findOne(Integer id);

    Employee create(Employee employee);

    List<Employee> findMany (String listStr);

    void createMany(List<Employee> employees);

    Employee update(Employee employee);

    List<Employee> findByDeptAndPos(Integer idDept, Integer idPos);

    void delete(Integer id);

    void deleteMany(List<Integer> ids);
}
