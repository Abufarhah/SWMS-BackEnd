package edu.birzeit.swms.dtos;

import edu.birzeit.swms.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinDto {

    @ApiModelProperty(notes = "The database generates bin ID")
    private int id;
    @ApiModelProperty(notes = "defines latitude coordinate of the bin")
    private double latitude;
    @ApiModelProperty(notes = "defines longitude coordinate of the bin")
    private double longitude;
    @ApiModelProperty(notes = "defines the status of the bin in terms of fullness")
    @Enumerated(EnumType.STRING)
    private Status status;

}
