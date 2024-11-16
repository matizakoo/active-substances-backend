package pl.engineer.active.substances.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiseaseActiveSubstancesDTO {
    private DiseaseDTO diseaseDTO;
    private List<ActiveSubstanceDTO> activeSubstanceDTO;
}
