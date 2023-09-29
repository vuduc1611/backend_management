package net.javaguides.springboot.controller;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "${FE_API_URL}")
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<List<Employee>>(employeeService.findAllData(), HttpStatus.OK);
    }

    // build get employee by id REST API
    // http://localhost:8080/api/employees

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
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


    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer employeeId) {
        return new ResponseEntity<Employee>(employeeService.findOne(employeeId), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @PostMapping("/many")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<Employee> employeeList) {
        employeeService.createMany((employeeList));
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.CREATED);
    }


    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.update(employee), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);
    }

    @DeleteMapping("/many")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteManyEmployee(@RequestParam("ids") List<Integer> ids) {
        employeeService.deleteMany(ids);
        return new ResponseEntity<String>("Employees deleted successfully!.", HttpStatus.OK);
    }

    @GetMapping("/dept/{idDept}/pos/{idPos}")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> findDeptAndPos(@PathVariable("idDept") Integer idDept, @PathVariable("idPos") Integer idPos) {
        return new ResponseEntity<List<Employee>>(employeeService.findByDeptAndPos(idDept, idPos), HttpStatus.OK);
    }

    @GetMapping("/findmany")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> findManyByIds(@RequestParam("ids") String ids) {
        return new ResponseEntity<List<Employee>>(employeeService.findMany(ids), HttpStatus.OK);
    }
}
