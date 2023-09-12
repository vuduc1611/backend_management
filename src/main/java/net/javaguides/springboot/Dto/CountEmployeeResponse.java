package net.javaguides.springboot.Dto;

import lombok.Data;

@Data
public class CountEmployeeResponse {
    private String name;
    private Long qty;

    public CountEmployeeResponse(String name, Long qty) {
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
}
