package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "active_substance")
@Getter
@Setter
@ToString
public class ActiveSubstanceEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Boolean pregnance;
    @NotNull
    private String dosage;
    @NotNull
    private String description;
    @OneToMany(mappedBy = "activeSubstanceEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CureEntity> diseases = new ArrayList<>();
//    @OneToMany(mappedBy = "activeSubstance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<PatientDiseaseSubstanceEntity> patientDiseaseSubstanceEntities = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveSubstanceEntity that = (ActiveSubstanceEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
