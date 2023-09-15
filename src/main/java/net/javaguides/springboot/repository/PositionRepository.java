package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position,Integer> {

    @Modifying
    @Transactional
    @Query("DELETE  FROM Position p WHERE p.id IN :ids")
    void deleteByIds(List<Integer> ids);
}
