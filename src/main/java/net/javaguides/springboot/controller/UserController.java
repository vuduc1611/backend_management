package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
     UserRepository userRepository;

     EmployeeRepository employeeRepository;

    public UserController(UserRepository userRepository, EmployeeRepository employeeRepository) {
        super();
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }
    @GetMapping("{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')or hasRole('PM')")
    public ResponseEntity<?> getInfoUser(@PathVariable("username") String username) {
        Optional<User> userCur = userRepository.findByUsername(username);
        Employee curEm = employeeRepository.findEmployeeByEmail(userCur.get().getEmail());
//        System.out.println("check " + curEm);
        return new ResponseEntity<>(curEm, HttpStatus.OK);
    }
}
