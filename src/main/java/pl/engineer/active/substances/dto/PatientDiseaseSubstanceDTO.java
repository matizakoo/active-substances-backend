package pl.engineer.active.substances.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.engineer.active.substances.entity.*;
import pl.engineer.active.substances.user.UserDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDiseaseSubstanceDTO {
    private UserDTO userDTO;
    private PatientDTO patientDTO;
    private List<DiseaseActiveSubstancesDTO> diseaseActiveSubstancesDTOList;
}
