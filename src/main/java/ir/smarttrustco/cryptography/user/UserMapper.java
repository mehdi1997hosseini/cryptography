package ir.smarttrustco.cryptography.user;

import ir.smarttrustco.cryptography.basic.BaseMapper;
import ir.smarttrustco.cryptography.user.dto.UserRegistryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserEntity, UserRegistryDto> {
    @Mapping(source = "personId", target = "person.id")
    UserEntity toEntity(UserRegistryDto registryDto);
    @Mapping(source = "person.id", target = "personId")
    UserRegistryDto toDto(UserEntity userEntity);

}
