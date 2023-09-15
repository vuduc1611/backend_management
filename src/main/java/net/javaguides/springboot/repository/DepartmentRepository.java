package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Position;
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
//    @Query("SELECT departmentId , COUNT(*) AS count FROM Employee e GROUP BY departmentId")
//    List<?> countEmployeesWithDepartment();

}
