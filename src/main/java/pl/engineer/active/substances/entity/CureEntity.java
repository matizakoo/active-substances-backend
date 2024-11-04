package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cure")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CureEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "disease_id", nullable = true)
    private DiseaseEntity diseaseEntity;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "active_substance_id", nullable = true)
    private ActiveSubstanceEntity activeSubstanceEntity;
}
