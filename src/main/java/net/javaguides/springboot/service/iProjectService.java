package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Project;


import java.util.List;

public interface iProjectService {
    List<Project> findAll();
    Project findOne(Integer id);
    Project create(Project project);
    Project update(Project project);
    void delete(Integer id);
}
