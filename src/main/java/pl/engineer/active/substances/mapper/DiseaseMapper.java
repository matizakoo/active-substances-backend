package pl.engineer.active.substances.mapper;

import org.mapstruct.Mapper;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;
import pl.engineer.active.substances.entity.DiseaseEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiseaseMapper {
    DiseaseDTO mapActiveSubstancesEntityToActiveSubstancesDTO(DiseaseEntity diseaseEntity);
    DiseaseEntity mapActiveSubstancesDTOToActiveSubstancesEntity(DiseaseDTO diseaseDTO);
    List<DiseaseDTO> mapActiveSubstancesEntityToActiveSubstancesDTO(List<DiseaseEntity> diseaseEntityList);
}
