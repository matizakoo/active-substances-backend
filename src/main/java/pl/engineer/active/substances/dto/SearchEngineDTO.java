package pl.engineer.active.substances.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SearchEngineDTO {
    private Boolean pregnance;
    private DiseaseDTO diseaseModelDTO; // nowa choroba
    private List<DiseaseDTO> diseaseModelDTOList; // aktualne choroby
    private List<ActiveSubstanceDTO> activesubstanceModel;
}
