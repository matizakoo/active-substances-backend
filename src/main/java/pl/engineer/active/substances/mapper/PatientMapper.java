package pl.engineer.active.substances.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.dto.PatientDTO;
import pl.engineer.active.substances.dto.PatientDiseaseSubstanceDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;
import pl.engineer.active.substances.entity.PatientDiseaseSubstanceEntity;
import pl.engineer.active.substances.entity.PatientEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toPatientDTO(PatientEntity patientEntity);
    PatientEntity toPatientEntity(PatientDTO patientEntity);
    List<PatientDTO> toPatientDTOList(List<PatientEntity> patientEntity);
    List<PatientEntity> toPatientEntityList(List<PatientDTO> patientEntity);

}

