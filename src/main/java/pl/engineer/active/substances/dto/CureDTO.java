package pl.engineer.active.substances.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CureDTO {
    private Integer id;
    private DiseaseDTO diseaseEntity;
    private ActiveSubstanceDTO activeSubstanceEntity;
}
