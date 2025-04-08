package ir.smarttrustco.cryptography.signatureMessage;

import ir.smarttrustco.cryptography.basic.BaseMapper;
import ir.smarttrustco.cryptography.signatureMessage.dto.SignatureMessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SignatureMessageMapper extends BaseMapper<SignatureMessageEntity, SignatureMessageDto> {
    @Override
    @Mappings({
            @Mapping(source = "messageCode", target = "message.code"),
            @Mapping(source = "title", target = "message.title")
    })
    SignatureMessageEntity toEntity(SignatureMessageDto signatureMessageDto);

    @Override
    @Mappings({
            @Mapping(target = "messageCode", source = "message.code"),
            @Mapping(target = "title", source = "message.title")
    })
    SignatureMessageDto toDto(SignatureMessageEntity signatureMessageEntity);
}
