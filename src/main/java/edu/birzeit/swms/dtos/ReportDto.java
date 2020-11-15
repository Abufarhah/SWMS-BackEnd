package edu.birzeit.swms.dtos;

import edu.birzeit.swms.models.User;
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
    private String sentBy;
    @ApiModelProperty(required = true)
    private int binId;

}
