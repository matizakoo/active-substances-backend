package pl.engineer.active.substances.dto;

import java.util.List;

public class SearchEngineModel {
    private DiseaseDTO disease;
    private List<ActiveSubstanceDTO> activeSubstances;

    public SearchEngineModel(DiseaseDTO disease, List<ActiveSubstanceDTO> activeSubstances) {
        this.disease = disease;
        this.activeSubstances = activeSubstances;
    }

    // Gettery i settery
    public DiseaseDTO getDisease() {
        return disease;
    }

    public void setDisease(DiseaseDTO disease) {
        this.disease = disease;
    }

    public List<ActiveSubstanceDTO> getActiveSubstances() {
        return activeSubstances;
    }

    public void setActiveSubstances(List<ActiveSubstanceDTO> activeSubstances) {
        this.activeSubstances = activeSubstances;
    }
}
