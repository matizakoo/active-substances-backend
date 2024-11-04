package pl.engineer.active.substances.mapper;

import org.mapstruct.Mapper;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.entity.ActiveSubstanceEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActiveSubstancesMapper {
//    @Mappings({
//            @Mapping(source = "id", target = "id"),
//            @Mapping(source = "nameOfProvider", target = "nameOfProvider")
//    })
    ActiveSubstanceEntity mapProviderDTOToProviderEntity(ActiveSubstanceDTO providerDTO);
//    @Mappings({
//            @Mapping(source = "id", target = "id"),
//            @Mapping(source = "nameOfProvider", target = "nameOfProvider")
//    })
    ActiveSubstanceDTO mapActiveSubstancesEntityToActiveSubstancesDTO(ActiveSubstanceEntity activeSubstanceEntity);
    List<ActiveSubstanceDTO> mapActiveSubstancesEntityToActiveSubstancesDTO(List<ActiveSubstanceEntity> activeSubstanceEntityList);
    ActiveSubstanceEntity mapActiveSubstancesDTOToActiveSubstancesEntity(ActiveSubstanceDTO activeSubstanceDTO);
}
