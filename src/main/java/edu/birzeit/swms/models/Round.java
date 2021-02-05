package edu.birzeit.swms.models;

import edu.birzeit.swms.enums.RoundStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private RoundStatus roundStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bin_round",
            joinColumns = @JoinColumn(name = "bin_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "round_id", referencedColumnName = "id"))
    private List<Bin> binList;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private User employee;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modified;
}
