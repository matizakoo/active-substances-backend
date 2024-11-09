package pl.engineer.active.substances.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SearchEngineDTO {
    private Boolean pregnance;
    private List<DiseaseDTO> diseaseModelDTO; // nowe choroby
    private List<DiseaseDTO> diseaseModelDTOList; // aktualne choroby
    private List<ActiveSubstanceDTO> activesubstanceModel;
}
