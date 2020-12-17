package edu.birzeit.swms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequestDto {

    String topicName;
    List<String> tokens;
    
}
