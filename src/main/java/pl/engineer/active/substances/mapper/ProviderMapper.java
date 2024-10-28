package pl.engineer.active.substances.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.engineer.active.substances.dto.ProviderDTO;
import pl.engineer.active.substances.entity.ProviderEntity;

@Mapper(componentModel = "spring")
public interface ProviderMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nameOfProvider", target = "nameOfProvider")
    })
    ProviderEntity mapProviderDTOToProviderEntity(ProviderDTO providerDTO);
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nameOfProvider", target = "nameOfProvider")
    })
    ProviderDTO mapProviderEntityToProviderDTO(ProviderEntity providerEntity);
}
