package net.javaguides.springboot.controller;


import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.DepartmentRepository;
import net.javaguides.springboot.service.impl.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "${FE_API_URL}")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private DepartmentService departmentService;
    @Autowired
    DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        super();
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public List<Department> getAll(){
        return departmentService.findAll();
    }



    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> create(@RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.create(department), HttpStatus.CREATED);
    }


    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> update(@RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.update(department), HttpStatus.OK);
    }



    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        departmentService.delete(id);
        return new ResponseEntity<String>("Department deleted successfully!.", HttpStatus.OK);
    }
    //ok

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getEmployeesByDept(@PathVariable("id") Integer id) {
        return new ResponseEntity<List<Employee>>(departmentService.fillEmployeeByDept(id), HttpStatus.OK);
    }
    @DeleteMapping("/many")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDepartments(@RequestParam("ids") List<Integer> ids) {
        departmentService.deleteMany(ids);
        return new ResponseEntity<String>("Employees deleted successfully!.", HttpStatus.OK);
    }
}
