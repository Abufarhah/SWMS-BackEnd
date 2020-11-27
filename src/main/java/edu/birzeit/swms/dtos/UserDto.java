package edu.birzeit.swms.dtos;

import edu.birzeit.swms.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @ApiModelProperty(notes = "The database generates citizen ID")
    private int id;
    @ApiModelProperty(required = true)
    private String firstName;
    @ApiModelProperty(required = true)
    private String lastName;
    @ApiModelProperty(example = "0599123456",required = true)
    @Pattern(regexp = "(^$|^05[0-9]{8})")
    private String phone;
    @ApiModelProperty(required = true)
    private String address;
    @ApiModelProperty(required = true)
    private String username;
    @ApiModelProperty(required = true)
    private String password;
    @ApiModelProperty(required = true)
    private UserRole role;

}
