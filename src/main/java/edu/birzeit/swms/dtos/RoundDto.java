package edu.birzeit.swms.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.birzeit.swms.enums.RoundStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The database generates report ID")
    private int id;
    @ApiModelProperty(required = true)
    private RoundStatus roundStatus;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(required = true)
    private LocalDateTime startTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(required = true)
    private LocalDateTime endTime;
    @ApiModelProperty(required = true)
    private List<Integer> binIdsList;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userId;

}
