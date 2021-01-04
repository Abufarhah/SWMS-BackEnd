package edu.birzeit.swms.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The database generates employee ID")
    private int id;
    @ApiModelProperty(required = true)
    private String firstName;
    @ApiModelProperty(required = true)
    private String lastName;
    @ApiModelProperty(example = "0599123456",required = true)
    @Pattern(regexp="(^$|^05[0-9]{8})")
    private String phone;
    @ApiModelProperty(required = true)
    private String address;
    @ApiModelProperty(required = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Integer> areaIdsList;

}
