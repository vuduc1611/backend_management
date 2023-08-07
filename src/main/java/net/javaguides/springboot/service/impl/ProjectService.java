package net.javaguides.springboot.service.impl;

import lombok.Data;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.ProjectRepository;
import net.javaguides.springboot.service.iProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements iProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        super();
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project create(Project project) {
        if(project.getName() == null || project.getDescription() == null || project.getStart() == null || project.getEnd() == null || project.getValue() == null) {
            throw new RuntimeException("Please check all flied are mandatory");
        }
        return projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        Project existingProject = projectRepository.findById(project.getId()).orElseThrow(()->
            new ResourceNotFoundException("Project", "Id", project.getId()));

        existingProject.setDescription(project.getDescription());
        existingProject.setName(project.getName());
        existingProject.setStart(project.getStart());
        existingProject.setEnd(project.getEnd());
        existingProject.setValue(project.getValue());
        return projectRepository.save(existingProject);
    }

    @Override
    public void delete(Integer id) {
        Project existingProject = projectRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Project", "Id", id));

        projectRepository.deleteById(id);
    }
}
