package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.impl.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        super();
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public List<Department> getAll(){
        return departmentService.findAll();
    }



    @PostMapping("")
    public ResponseEntity<Department> create(@RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.create(department), HttpStatus.CREATED);
    }


    @PutMapping("")
    public ResponseEntity<Department> update(@RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.update(department), HttpStatus.OK);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        departmentService.delete(id);
        return new ResponseEntity<String>("Department deleted successfully!.", HttpStatus.OK);
    }
    //ok

    @GetMapping("{id}")
    public ResponseEntity<List<Employee>> getEmployeesByDept(@PathVariable("id") Integer id) {
        return new ResponseEntity<List<Employee>>(departmentService.fillEmployeeByDept(id), HttpStatus.OK);
    }
    @DeleteMapping("/many")
    public ResponseEntity<String> deleteDepartments(@RequestParam("ids") List<Integer> ids) {
        departmentService.deleteMany(ids);
        return new ResponseEntity<String>("Employees deleted successfully!.", HttpStatus.OK);
    }
}
