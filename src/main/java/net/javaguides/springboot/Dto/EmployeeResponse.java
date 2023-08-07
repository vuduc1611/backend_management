package net.javaguides.springboot.Dto;

import lombok.Data;
import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Position;

@Data
public class EmployeeResponse {

    private Integer id;

    private String fname;

    private String lname;

    private String gender;

    private String dob;

    private String address;

    private String phone;

    private String email;

    private String password;

    private String departmentName;

    private String positionName;
}
