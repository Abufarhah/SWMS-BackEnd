package edu.birzeit.swms.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

    @ApiModelProperty(notes = "The database generates report ID")
    private int id;
    @ApiModelProperty(required = true)
    private String subject;
    @ApiModelProperty(required = true)
    private String body;
    @ApiModelProperty(required = true)
    private int userId;
    @ApiModelProperty(required = true)
    private int binId;

}
