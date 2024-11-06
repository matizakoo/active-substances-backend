package pl.engineer.active.substances.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class ActiveSubstancesDiseasesConflictDTO {
    private ActiveSubstanceDTO activesubstanceDTO;
    private List<DiseaseDTO> diseaseDTO;
}
