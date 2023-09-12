package net.javaguides.springboot.service.impl;

import lombok.Data;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
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
    public Project findOne(Integer id) {
        return projectRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Project", "Id", id));
    }
    @Override
    public Project create(Project project) {
        if(project.getName() == null || project.getDescription() == null
                || project.getCreatedAt() == null  || project.getValue() == null
                || project.getIdPM() == null  || project.getMembersId() == null
        ) {
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
        existingProject.setDescription(project.getDescription());
        existingProject.setCreatedAt(project.getCreatedAt());
        existingProject.setCompletedAt(project.getCompletedAt());
        existingProject.setMembersId(project.getMembersId());
        existingProject.setIdPM(project.getIdPM());
        existingProject.setValue(project.getValue());
        return projectRepository.save(existingProject);
    }

    @Override
    public void delete(Integer id) {
        projectRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Project", "Id", id));

        projectRepository.deleteById(id);
    }
}
