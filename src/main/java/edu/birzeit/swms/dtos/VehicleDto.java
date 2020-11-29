package edu.birzeit.swms.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    @ApiModelProperty(notes = "The database generates vehicle ID")
    private int id;
    @ApiModelProperty(required = true)
    private String number;
    @ApiModelProperty(required = true)
    private List<Integer> employees;

}
