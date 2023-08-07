package net.javaguides.springboot.repository;

import net.javaguides.springboot.Dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    @Query(value = "SELECT e FROM Employee e WHERE e.departmentId = :id")
    List<Employee> findEmployeeByDept(@Param("id") Integer id);


    @Query("SELECT e FROM Employee e WHERE e.positionId = :id")
    List<Employee> findEmployeeByPosition(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE  FROM Employee e WHERE e.id IN :ids")
    void deleteByIdIn(List<Integer> ids);



    @Query("SELECT e FROM Employee e WHERE 1 = 1 " +
            "AND (:id IS NULL OR e.id = :id) " +
            "AND (:fname IS NULL OR e.fname LIKE %:fname%) " +
            "AND (:lname IS NULL OR e.lname LIKE %:lname%) " +
            "AND (:departmentId IS NULL OR e.departmentId = :departmentId) " +
            "AND (:positionId IS NULL OR e.positionId = :positionId)")
    Page<Employee> findEmployeesByPageWithFilters(Pageable pageable, Integer id, String fname, String lname,Integer departmentId,Integer positionId );
//    AND (:departmentId IS NULL OR e.departmentId LIKE %:departmentId%) AND (:positionId IS NULL OR e.positionId LIKE %:positionId%)
}