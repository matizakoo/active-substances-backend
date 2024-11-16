package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient_disease_substance")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PatientDiseaseSubstanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference // Unika serializacji "patient" w odpowiedzi JSON
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @JsonBackReference // Unika serializacji "patient" w odpowiedzi JSON
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disease_id")
    private DiseaseEntity disease; // Tylko obiekt choroby, bez zagnieżdżonych relacji

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "active_substance_id")
    private ActiveSubstanceEntity activeSubstance; // Tylko obiekt substancji aktywnej, bez zagnieżdżonych relacji

    public PatientDiseaseSubstanceEntity(UserEntity userEntity, PatientEntity patient, DiseaseEntity disease, ActiveSubstanceEntity activeSubstance) {
        this.userEntity = userEntity;
        this.patient = patient;
        this.disease = disease;
        this.activeSubstance = activeSubstance;
    }
}