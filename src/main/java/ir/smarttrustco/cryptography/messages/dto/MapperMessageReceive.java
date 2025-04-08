package ir.smarttrustco.cryptography.messages.dto;

import ir.smarttrustco.cryptography.basic.BaseMapper;
import ir.smarttrustco.cryptography.messages.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperMessageReceive extends BaseMapper<MessageEntity , MessageReceiveDto> {
    @Override
    @Mapping(source = "sender" , target = "sender.username")
    MessageEntity toEntity(MessageReceiveDto messageReceiveDto);

    @Override
    @Mapping(target = "sender" , source = "sender.username")
    MessageReceiveDto toDto(MessageEntity entity);

}
