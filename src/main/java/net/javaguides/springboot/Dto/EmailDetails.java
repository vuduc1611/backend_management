package net.javaguides.springboot.Dto;

import lombok.Data;

@Data
public class EmailDetails {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
}
