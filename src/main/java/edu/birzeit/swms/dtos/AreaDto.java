package edu.birzeit.swms.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDto {

    @ApiModelProperty(notes = "The database generates area ID")
    private int id;
    @ApiModelProperty(notes = "defines the name of the area", required = true)
    private String name;
    @ApiModelProperty(notes = "defines the ploygon cavered by the area", required = true)
    private PolygonDto polygonDto;


}
