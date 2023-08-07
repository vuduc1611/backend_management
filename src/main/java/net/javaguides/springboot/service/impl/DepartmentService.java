package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.DepartmentRepository;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.iDepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentService implements iDepartmentService {
    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        super();
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }


    @Override
    public Department create(Department department) {
        Department newDepartment = new Department();
        newDepartment.setName(department.getName());
        newDepartment.setDescription(department.getDescription());
        return departmentRepository.save(newDepartment);
    }

    @Override
    public Department update(Department department) {
        Department existingDept = departmentRepository.findById(department.getDepartmentId()).orElseThrow(
                () -> new ResourceNotFoundException("Department", "Id", department.getDepartmentId()));

        existingDept.setName(department.getName());
        existingDept.setDescription(department.getDescription());

//        Set<Role> roles= new HashSet<>();
//        String[] rolesIdStrParts= departmentDto.getRolesToStr().split(",");
//        List<String> rolesIdList = Arrays.asList(rolesIdStrParts);
//        for(String roleId : rolesIdList) roles.add(roleRepository.findById(Long.valueOf(roleId)).orElseThrow(()->
//                new ResourceNotFoundException("Role", "Id", roleId)));
//        existingDept.setRoles(roles);
        return departmentRepository.save(existingDept);
    }

    @Override
    public void delete(Integer id) {
        departmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Department", "Id", id));
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Employee> fillEmployeeByDept(Integer id) {
        return employeeRepository.findEmployeeByDept(id);
    }

    @Override
    public void deleteMany(List<Integer> ids) {
        departmentRepository.deleteByIds(ids);
    }
}
