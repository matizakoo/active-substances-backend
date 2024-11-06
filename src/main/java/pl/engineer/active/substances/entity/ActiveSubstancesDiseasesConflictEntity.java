package pl.engineer.active.substances.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "active_substances_diseases_conflict")
@Getter
@Setter
@NoArgsConstructor
public class ActiveSubstancesDiseasesConflictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "active_substance_id", referencedColumnName = "id")
    private ActiveSubstanceEntity substance;

    @ManyToOne
    @JoinColumn(name = "disease_id", referencedColumnName = "id")
    private DiseaseEntity diseaseEntity;

    public ActiveSubstancesDiseasesConflictEntity(ActiveSubstanceEntity substance, DiseaseEntity diseaseEntity) {
        this.substance = substance;
        this.diseaseEntity = diseaseEntity;
    }
}
