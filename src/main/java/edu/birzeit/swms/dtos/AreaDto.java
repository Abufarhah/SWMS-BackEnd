package edu.birzeit.swms.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDto {

    @ApiModelProperty(notes = "The database generated area ID")
    private int id;
    private String name;

}
