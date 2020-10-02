package edu.birzeit.swms.dtos;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @ApiModelProperty(notes = "The database generated employee ID")
    private int id;
    private String firstName;
    private String lastName;
    @ApiModelProperty(example = "0599123456")
    @Pattern(regexp="(^$|^05[0-9]{8})")
    private String phone;
    private String address;
    private String username;

}
