package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.service.impl.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "${FE_API_URL}")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<Project>> getProjects() {
        return new ResponseEntity<List<Project>>(projectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/id")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<Project> getOne(@PathVariable("id") Integer id) {
        return new ResponseEntity<Project>(projectService.findOne(id), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('PM')")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return new ResponseEntity<Project>(projectService.create(project), HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('PM')")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        return new ResponseEntity<Project>(projectService.update(project), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('PM')")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        projectService.delete(id);
        return new ResponseEntity<String>("Project delete success", HttpStatus.OK);
    }
}
