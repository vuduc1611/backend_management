package net.javaguides.springboot.controller;

import net.javaguides.springboot.Dto.EmployeeResponse;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private ModelMapper modelMapper;

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        super();
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    // build get all employees REST API
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employeeResponseList = employeeService.findAllData().stream().map(employee -> modelMapper.map(employee, EmployeeResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<List<EmployeeResponse>>(employeeResponseList, HttpStatus.OK);
    }

    // build get employee by id REST API
    // http://localhost:8080/api/employees

    @GetMapping("")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String fname,
            @RequestParam(required = false) String lname,
            @RequestParam(required = false) List<Integer> departmentIds,
            @RequestParam(required = false) List<Integer> positionIds
    ) {

        return new ResponseEntity<Page<Employee>>(employeeService.findEmployeesByPageWithFilters(sortBy, sortDir, page, size,
                id, fname, lname, departmentIds, positionIds), HttpStatus.OK);
    }


    //    @GetMapping("{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer employeeId) {
//        return new ResponseEntity<Employee>(employeeService.findOne(employeeId), HttpStatus.OK);
//    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") Integer employeeId) {
        Employee employee = employeeService.findOne(employeeId);
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        return new ResponseEntity<EmployeeResponse>(employeeResponse, HttpStatus.OK);
    }

    // build create employee REST API
    @PostMapping("")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @PostMapping("/many")
    public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<Employee> employeeList) {
        employeeService.createMany((employeeList));
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.CREATED);
    }

    // build update employee REST API
    // http://localhost:8080/api/employees/1
    @PutMapping("")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.update(employee), HttpStatus.OK);
    }

//    @GetMapping("/department/{id}")
//    public ResponseEntity<List<Employee>> getEmpByDepartment(@PathVariable("id") Integer id) {
//        return new ResponseEntity<List<Employee>>(employeeService.findByDept(id), HttpStatus.OK);
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

    @GetMapping("/dept/{idDept}/pos/{idPos}")
    public ResponseEntity<List<Employee>> findDeptAndPos(@PathVariable("idDept") Integer idDept, @PathVariable("idPos") Integer idPos) {
        return new ResponseEntity<List<Employee>>(employeeService.findByDeptAndPos(idDept, idPos), HttpStatus.OK);
    }
}
