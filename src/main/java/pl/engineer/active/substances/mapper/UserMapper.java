package pl.engineer.active.substances.mapper;

import org.mapstruct.Mapper;
import pl.engineer.active.substances.entity.UserEntity;
import pl.engineer.active.substances.user.UserDTO;
import pl.engineer.active.substances.user.UserRegistrationDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO mapToUserDTO(UserEntity userEntity);
    UserEntity mapToUserEntity(UserDTO userEntity);
}
