package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.service.impl.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    @GetMapping("")
    public ResponseEntity<List<Project>> getProjects() {
        return new ResponseEntity<List<Project>>(projectService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {


        return new ResponseEntity<Project>(projectService.create(project), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        return new ResponseEntity<Project>(projectService.update(project), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        projectService.delete(id);
        return new ResponseEntity<String>("Project delete success", HttpStatus.OK);
    }
}
