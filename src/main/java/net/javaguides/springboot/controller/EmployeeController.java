package net.javaguides.springboot.controller;

import net.javaguides.springboot.Dto.EmployeeResponse;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        super();
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    // build get all employees REST API
//    @GetMapping("")
//    public List<Employee> getAllEmployees() {
//        return employeeService.findAll();
//    }

    // build get employee by id REST API
    // http://localhost:8080/api/employees/1

    @GetMapping("")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String fname,
            @RequestParam(required = false) String lname,
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) Integer positionId
    ) {

        return new ResponseEntity<Page<Employee>>(employeeService.findEmployeesByPageWithFilters(sortBy, sortDir,page, size,
                id, fname, lname, departmentId, positionId), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer employeeId) {
        return new ResponseEntity<Employee>(employeeService.findOne(employeeId), HttpStatus.OK);
    }

    // build create employee REST API
    @PostMapping("")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.create(employee), HttpStatus.CREATED);
    }

    // build update employee REST API
    // http://localhost:8080/api/employees/1
    @PutMapping("")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.update(employee), HttpStatus.OK);
    }

//    @GetMapping("/department/{id}")
//    public ResponseEntity<List<Employee>> getEmpByDepartment(@PathVariable("id") long id) {
//        return new ResponseEntity<List<Employee>>(em.findEmployeesByDepartment(id), HttpStatus.OK);
//    }
//
//    @GetMapping("/positions/{id}")
//    public ResponseEntity<List<Employee>> findEmployeeByPosition(@PathVariable("id") long id) {
//        return new ResponseEntity<List<Employee>>(employeeService.findEmployeeByPosition(id), HttpStatus.OK);
//    }


    // build delete employee REST API
    // http://localhost:8080/api/employees/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);
    }

    @DeleteMapping("/many")
    public ResponseEntity<String> deleteManyEmployee(@RequestParam("ids") List<Integer> ids) {
        employeeService.deleteMany(ids);
        return new ResponseEntity<String>("Employees deleted successfully!.", HttpStatus.OK);
    }
}
