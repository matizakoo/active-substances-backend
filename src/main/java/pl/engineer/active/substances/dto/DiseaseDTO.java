package pl.engineer.active.substances.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.engineer.active.substances.entity.CureEntity;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class DiseaseDTO {
    private Integer id;
    private String name;
    private String description;
    private String pregnancy;
    private List<ActiveSubstanceDTO> activeSubstancesDTOList = new ArrayList<>();
}
