package pl.engineer.active.substances.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "activeSubstanceEntity")
    private List<CureEntity> diseases = new ArrayList<>();
}
