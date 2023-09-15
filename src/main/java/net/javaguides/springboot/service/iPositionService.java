package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Position;

import java.util.List;

public interface iPositionService {
    List<Position> findAll();
    Position create(Position position);
    Position update(Position position);
    void delete(Integer id);
    List<Employee> findEmployeesByPosition(Integer id);
    void deleteMany(List<Integer> ids);

}
