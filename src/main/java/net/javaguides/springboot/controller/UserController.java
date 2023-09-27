package net.javaguides.springboot.controller;

import net.javaguides.springboot.Dto.DtoUserChangePass;
import net.javaguides.springboot.Dto.EmailDetails;
import net.javaguides.springboot.Dto.MessageResponse;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.RoleRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;



    public UserController(UserRepository userRepository, EmployeeRepository employeeRepository, RoleRepository roleRepository, UserDetailsServiceImpl userDetailsService) {
        super();
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("{username}")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> getInfoUser(@PathVariable("username") String username) {
        Optional<User> userCur = userRepository.findByUsername(username);
        Employee curEm = employeeRepository.findEmployeeByEmail(userCur.get().getEmail());
        return new ResponseEntity<>(curEm, HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody User user) {
        User curUser = userRepository.findById(user.getId()).orElseThrow(()->
                new ResourceNotFoundException("User", "Id", user.getId()));

//        System.out.println("check pass :" + user.getPassword());
        if(user.getEmail() != null || user.getUsername() != null
                || user.getPassword() != null || user.getRoles().isEmpty()
                || user.getFirstname() != null || user.getLastname() != null
        ) {
            curUser.setUsername(user.getUsername());
            curUser.setFirstname(user.getFirstname());
            curUser.setLastname(user.getLastname());
            curUser.setEmail(user.getEmail());
            curUser.setRoles(user.getRoles());
//            System.out.println("check pass 11" + user.getPassword());
            curUser.setPassword(encoder.encode(user.getPassword()));
            return new ResponseEntity<User>(userRepository.save(curUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("All Flied are mandatory!"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            user.getRoles().removeAll(roleRepository.findAll());
            userRepository.delete(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return null;
    }

    @PostMapping("/forgetPassword")
    public  ResponseEntity<?> forgetPassword(@RequestBody EmailDetails emailDetails) {
        if(userDetailsService.sendEmail(emailDetails.getEmail()) != "success") {
            return new ResponseEntity<String>(userDetailsService.sendEmail(emailDetails.getEmail()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(userDetailsService.sendEmail(emailDetails.getEmail()), HttpStatus.OK);
    }
    @PutMapping("/changePassword")
    public  ResponseEntity<?> changePassword(@RequestBody DtoUserChangePass dtoUserChangePass) {
        return new ResponseEntity<String>(userDetailsService.changePassword(dtoUserChangePass), HttpStatus.OK);
    }
}
