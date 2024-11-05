package pl.engineer.active.substances.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ActiveSubstanceConflictDTO {
    private ActiveSubstanceDTO activesubstanceDTO;
    private List<ActiveSubstanceDTO> activeSubstanceDTOList;
    private ActiveSubstanceDTO activesubstanceDTO2;
}
