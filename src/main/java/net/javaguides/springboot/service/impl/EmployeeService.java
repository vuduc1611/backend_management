package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.DepartmentRepository;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.PositionRepository;
import net.javaguides.springboot.service.iEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService implements iEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    private PositionRepository positionRepository;
    private DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           PositionRepository positionRepository,
                           DepartmentRepository departmentRepository) {

        super();
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Employee> findAllData() {
        return employeeRepository.findAll();
    }

    //    @Override
//    public Page<Employee> findEmployees(int page, int size) {
////        Pageable pageable = PageRequest.of(page , size, sort);
//        Pageable pageable = PageRequest.of(page , size);
////        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
////                : Sort.by(sortBy).descending();
////        Pageable pageable = PageRequest.of(page, size, sort);
////        Pageable pageable = PageRequest.of(page, size);
//        return employeeRepository.findAllEmployees(pageable, "male");
//    }


//    @Override
//    public Page<Employee> findEmployeesByPageAndFilter(int page, int size, Map<String, String> filters) {
//        Specification<Employee> specification = (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            filters.forEach((field, value) -> {
//                if (value != null && !value.isEmpty()) {
//                    predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
//                }
//            });
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        };
//
//        Pageable pageable = PageRequest.of(page, size);
//        return employeeRepository.findAll(specification, pageable);
//    }
//    }

//    @Override
//    public Page<Employee> findEmployeesByPageAndFilterAndSort(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        String s1 = "";
//        for (Map.Entry<String, String> entry : filters.entrySet()) {
//            if (!entry.getValue().isEmpty() && entry.getKey() != null) {
//                s1 += " AND " + "(:" + entry.getValue() + " IS NULL OR " + entry.getKey() + " LIKE %:" + entry.getValue() + "%)";
//            }
//        }
//        return employeeRepository.findAllEmployees(pageable, s1);
//    }


//    @Override
//    public Page<Employee> findEmployeesByPageWithFilters(Integer page, Integer size, Integer id, String fname, String lname) {
//        Pageable pageable = PageRequest.of(page, size);
//        return employeeRepository.findEmployeesByPageWithFilters(pageable, id, fname, lname);
//    }

//    @Override
//    public Page<Employee> findEmployeesByPageWithFilters(String sortBy, String sortDir, Integer page,
//                                                         Integer size, Integer id, String fname, String lname,
//                                                         Integer departmentId, Integer positionId) {
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return employeeRepository.findEmployeesByPageWithFilters(pageable, id, fname, lname, departmentId, positionId);
//    }

    @Override
    public Page<Employee> findEmployeesByPageWithFilters(String sortBy, String sortDir, Integer page,
                                                         Integer size, Integer id, String fname, String lname,
                                                         List<Integer> departmentIds, List<Integer> positionIds) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findEmployeesByPageWithFilters(pageable, id, fname, lname, departmentIds, positionIds);
    }

    @Override
    public Employee findOne(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "Id", id));
    }

//    @Override
//    public List<Employee> findByDept(Integer id) {
//        if(departmentRepository.existsById(id)) {
//            return employeeRepository.findEmployeeByDept(id);
//        }
//        return null;
//    }

    @Override
    public Employee create(Employee employee) {
        if (employee.getPositionId() == null && employee.getDepartmentId() == null) {
            throw new Error("Re-check: departmentId, positionId are mandatory");
        }
        positionRepository.findById(employee.getPositionId()).orElseThrow(() ->
                new ResourceNotFoundException("Position", "Id", employee.getPositionId()));

        departmentRepository.findById(employee.getDepartmentId()).orElseThrow(() ->
                new ResourceNotFoundException("Department", "Id", employee.getDepartmentId()));
        Employee newEmployee = new Employee();

        newEmployee.setFname(employee.getFname());
        newEmployee.setLname(employee.getLname());
        newEmployee.setGender(employee.getGender());
        newEmployee.setDob(employee.getDob());
        newEmployee.setAddress(employee.getAddress());
        newEmployee.setPhone(employee.getPhone());
        newEmployee.setEmail(employee.getEmail());
//        newEmployee.setPassword(employee.getPassword());
        newEmployee.setDepartmentId(employee.getDepartmentId());
        newEmployee.setPositionId((employee.getPositionId()));

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findMany(String strList) {
//        List<Optional<Employee>> employeeList = new ArrayList<>();
//        Integer[] array = (Integer[]) Arrays.stream(listStr.split(",")).toArray();
//        Arrays.stream(array).map(a -> employeeList.add(employeeRepository.findById(a)));
        String[] strArray = strList.split(",");
        Integer[] numberArray = new Integer[strArray.length];
        for(int i = 0; i < strArray.length; i++ ) {
            numberArray[i] = Integer.parseInt(strArray[i]);
        }
        return employeeRepository.findEmployeesByIds(List.of(numberArray));
    }

    @Override
    public void createMany(List<Employee> employees) {
        employees.forEach(this::create);
    }

    @Override
    public Employee update(Employee employee) {


        Employee existingEmployee = employeeRepository.findById(employee.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "Id", employee.getId()));
        positionRepository.findById(existingEmployee.getPositionId()).orElseThrow(() ->
                new ResourceNotFoundException("Position", "Id", existingEmployee.getPositionId()));
        departmentRepository.findById(existingEmployee.getDepartmentId()).orElseThrow(() ->
                new ResourceNotFoundException("Department", "Id", existingEmployee.getDepartmentId()));

        existingEmployee.setFname(employee.getFname());
        existingEmployee.setLname(employee.getLname());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setDob(employee.getDob());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setEmail(employee.getEmail());
//        existingEmployee.setPassword(employee.getPassword());
        existingEmployee.setPositionId(employee.getPositionId());
        existingEmployee.setDepartmentId(employee.getDepartmentId());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "Id", id));
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteMany(List<Integer> ids) {
        employeeRepository.deleteByIdIn(ids);
        System.out.println("delete employees" + ids);
    }

    @Override
    public List<Employee> findByDeptAndPos(Integer idDept, Integer idPos) {
        return employeeRepository.findEmployeesFromDeptAndPos(idDept, idPos);
    }
}
