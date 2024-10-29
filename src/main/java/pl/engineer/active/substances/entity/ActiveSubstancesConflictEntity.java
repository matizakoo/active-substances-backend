package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "substance_conflict")
@Getter
@Setter
public class ActiveSubstancesConflictEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "substance_id", nullable = false)
    private ActiveSubstanceEntity substance;

    @ManyToOne
    @JoinColumn(name = "conflicting_substance_id", nullable = false)
    private ActiveSubstanceEntity conflictingSubstance;
}