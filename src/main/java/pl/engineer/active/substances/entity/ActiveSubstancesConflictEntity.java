package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "substance_conflict")
@Getter
@Setter
@NoArgsConstructor
public class ActiveSubstancesConflictEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "activesubstance1_id", referencedColumnName = "id")
    private ActiveSubstanceEntity substance;

    @ManyToOne
    @JoinColumn(name = "activesubstance2_id", referencedColumnName = "id")
    private ActiveSubstanceEntity conflictingSubstance;

    public ActiveSubstancesConflictEntity(ActiveSubstanceEntity substance, ActiveSubstanceEntity conflictingSubstance) {
        this.substance = substance;
        this.conflictingSubstance = conflictingSubstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveSubstancesConflictEntity that = (ActiveSubstancesConflictEntity) o;
        return (Objects.equals(substance, that.substance) && Objects.equals(conflictingSubstance, that.conflictingSubstance)) ||
                (Objects.equals(substance, that.conflictingSubstance) && Objects.equals(conflictingSubstance, that.substance));
    }

    @Override
    public int hashCode() {
        return Objects.hash(substance, conflictingSubstance) + Objects.hash(conflictingSubstance, substance);
    }
}