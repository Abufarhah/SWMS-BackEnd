package edu.birzeit.swms.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.birzeit.swms.enums.ReportStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The database generates report ID")
    private int id;
    @ApiModelProperty(required = true)
    private String subject;
    @ApiModelProperty(required = true)
    private String body;
    private String imageUrl;
    @ApiModelProperty(required = true)
    private ReportStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime created;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(required = true)
    private int userId;
    @ApiModelProperty(required = true)
    private int binId;

}
