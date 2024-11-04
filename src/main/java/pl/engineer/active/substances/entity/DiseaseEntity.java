package pl.engineer.active.substances.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disease")
@Getter
@Setter
@ToString
public class DiseaseEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    private String description;

    @OneToMany(mappedBy = "diseaseEntity")
    private List<CureEntity> activeSubstancesDTOList = new ArrayList<>();
}
