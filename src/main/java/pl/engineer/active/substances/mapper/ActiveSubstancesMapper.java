package pl.engineer.active.substances.mapper;

import org.mapstruct.Mapper;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActiveSubstancesMapper {
    ActiveSubstanceDTO mapActiveSubstancesEntityToActiveSubstancesDTO(ActiveSubstanceEntity activeSubstanceEntity);
    List<ActiveSubstanceDTO> mapActiveSubstancesEntityToActiveSubstancesDTO(List<ActiveSubstanceEntity> activeSubstanceEntityList);
    ActiveSubstanceEntity mapActiveSubstancesDTOToActiveSubstancesEntity(ActiveSubstanceDTO activeSubstanceDTO);
}
