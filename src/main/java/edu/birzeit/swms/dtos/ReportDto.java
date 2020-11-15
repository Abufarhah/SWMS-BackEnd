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
    private String subject;
    private String body;
    private String sentBy;
    private int binId;

}
