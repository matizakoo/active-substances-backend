package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
@Getter
@Setter
@EqualsAndHashCode
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    // pesel
    private String uniqueId;
    private Integer age;
    private String gender;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity userEntity;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PatientDiseaseSubstanceEntity> patientDiseaseSubstances = new ArrayList<>();
}
