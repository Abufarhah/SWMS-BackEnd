package edu.birzeit.swms.dtos;

import edu.birzeit.swms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinDto {

    private int id;
    private double latitude;
    private double longitude;
    @Enumerated(EnumType.STRING)
    private Status status;

}
