package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Position;
import net.javaguides.springboot.service.impl.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "${FE_API_URL}")
@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private PositionService positionService;

    public PositionController(PositionService positionService) {
        super();
        this.positionService = positionService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public List<Position> getAll(){
        return positionService.findAll();
    }


    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Position> create(@RequestBody Position position){
        return new ResponseEntity<Position>(positionService.create(position), HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Position> update(@RequestBody Position position){
        return new ResponseEntity<Position>(positionService.update(position), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        positionService.delete(id);
        return new ResponseEntity<String>("Posistion id  deleted successfully!.", HttpStatus.OK);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> findEmployeesByPos(@PathVariable("id") Integer id){
        return new ResponseEntity<List<Employee>>(positionService.findEmployeesByPosition(id), HttpStatus.OK);
    }

    @DeleteMapping("/many")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePositions(@RequestParam("ids") List<Integer> ids) {
        positionService.deleteMany(ids);
        return new ResponseEntity<String>("Positions deleted successfully!.", HttpStatus.OK);
    }

}
