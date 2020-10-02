package edu.birzeit.swms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private int id;
    private String firstName;
    private String lastName;
    private int phone;
    private String address;
    private String username;

}
