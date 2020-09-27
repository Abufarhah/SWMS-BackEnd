package edu.birzeit.swms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDto {

    private int id;
    private String firstName;
    private String lastName;
    private int phone;
    private String username;
}