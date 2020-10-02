package edu.birzeit.swms.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDto {

    @ApiModelProperty(notes = "The database generated citizen ID")
    private int id;
    private String firstName;
    private String lastName;
    private int phone;
    private String username;
}
