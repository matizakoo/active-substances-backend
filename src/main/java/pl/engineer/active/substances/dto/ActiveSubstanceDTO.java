package pl.engineer.active.substances.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ActiveSubstanceDTO {
    private Integer id;
    private String name;
    private Boolean pregnance;
    private String dosage;
    private String description;
}
