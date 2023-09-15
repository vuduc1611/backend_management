package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Position;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.PositionRepository;
import net.javaguides.springboot.service.iPositionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PositionService implements iPositionService {
    private PositionRepository positionRepository;
    private EmployeeRepository employeeRepository;

    public PositionService(PositionRepository positionRepository, EmployeeRepository employeeRepository) {
        super();
        this.positionRepository = positionRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

//    @Override
//    public Position findOne(Long id) {
//        return positionRepository.findById(id).orElseThrow(()->
//                new ResourceNotFoundException("Position", "Id", id));
//    }


    @Override
    public Position create(Position position) {
        Position newPosition = new Position();
        newPosition.setName(position.getName());
        return positionRepository.save(newPosition);
    }

    @Override
    public Position update(Position position) {
        if(position.getId() == null) {
            throw new RuntimeException("Position Id is not found");
        }
        Position existingPos = positionRepository.findById(position.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Position", "Id", position.getId()));

        existingPos.setName(position.getName());
        return positionRepository.save(existingPos);
    }

    @Override
    public void delete(Integer id) {
        positionRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Position", "Id", id));
        positionRepository.deleteById(id);
    }

    @Override
    public List<Employee> findEmployeesByPosition(Integer id) {
        return employeeRepository.findEmployeeByPosition(id);
    }

    @Override
    public void deleteMany(List<Integer> ids) {
        positionRepository.deleteByIds(ids);
    }
}
