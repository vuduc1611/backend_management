package net.javaguides.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public  interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

//    @Query("SELECT e.id, e.fname, e.lname, e.gender, e.dob, e.address, e.phone, e.email, e.departmentId, e.positionId FROM Employee e")
//    List<EmployeeResponse> findAllData();

    @Query("SELECT e FROM Employee e WHERE e.departmentId = :id")
    List<Employee> findEmployeeByDept(@Param("id") Integer id);


    @Query("SELECT e FROM Employee e WHERE e.positionId = :id")
    List<Employee> findEmployeeByPosition(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("SELECT e FROM Employee e WHERE e.id IN :ids")
    List<Employee> findEmployeesByIds(List<Integer> ids);

    @Modifying
    @Transactional
    @Query("DELETE  FROM Employee e WHERE e.id IN :ids")
    void deleteByIdIn(List<Integer> ids);

    @Query("SELECT e FROM Employee e WHERE 1 = 1 " +
            "AND (:id IS NULL OR e.id = :id) " +
            "AND (:fname IS NULL OR e.fname LIKE %:fname%) " +
            "AND (:lname IS NULL OR e.lname LIKE %:lname%) " +
            "AND (COALESCE(:departmentIds) IS NULL OR e.departmentId IN (:departmentIds)) " +
            "AND (COALESCE(:positionIds) IS NULL OR e.positionId IN (:positionIds))")
    Page<Employee> findEmployeesByPageWithFilters(Pageable pageable, Integer id, String fname, String lname, @Param("departmentIds") List<Integer> departmentIds, @Param("positionIds") List<Integer> positionIds);

    @Query("SELECT e FROM Employee e WHERE 1 = 1 AND departmentId = :idDept AND positionId = :idPos")
    List<Employee> findEmployeesFromDeptAndPos(@PathVariable("idDept") Integer idDept, @PathVariable("idPos") Integer idPos);


    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Employee findEmployeeByEmail(@PathVariable("email") String email);

//    @Query ("SELECT  FROM Employee e WHERE departmentID = :idDept")
//    List<Position> findExistPosInDept(Integer idDept);
}