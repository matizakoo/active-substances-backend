package pl.engineer.active.substances.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.engineer.active.substances.entity.CureEntity;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiseaseDTO {
    private Integer id;
    private String name;
    private String description;
    private List<ActiveSubstanceDTO> activeSubstancesDTOList = new ArrayList<>();
}
