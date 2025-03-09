package ir.smarttrustco.cryptography.person;

import ir.smarttrustco.cryptography.basic.BaseMapper;
import ir.smarttrustco.cryptography.person.dto.PersonRegistryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper extends BaseMapper<PersonEntity, PersonRegistryDto> {
}
