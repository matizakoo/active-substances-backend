package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cure")
@Getter
@Setter
public class CureEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "disease_id", nullable = false)
    private DiseaseEntity diseaseEntity;
    @ManyToOne
    @JoinColumn(name = "active_substance_id", nullable = false)
    private ActiveSubstanceEntity activeSubstanceEntity;
}
