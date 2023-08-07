package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE  FROM Department d WHERE d.departmentId IN :ids")
    void deleteByIds(List<Integer> ids);
}
