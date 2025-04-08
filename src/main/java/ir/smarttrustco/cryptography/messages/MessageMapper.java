package ir.smarttrustco.cryptography.messages;

import ir.smarttrustco.cryptography.basic.BaseMapper;
import ir.smarttrustco.cryptography.messages.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper extends BaseMapper<MessageEntity, MessageDto> {
    @Override
    @Mapping(source = "sender.username" , target = "sender")
    @Mapping(source = "receiver.username" , target = "receiver")
    MessageDto toDto(MessageEntity messageEntity);

    @Override
    @Mapping(target = "sender.username" , source = "sender")
    @Mapping(target = "receiver.username" , source = "receiver")
    MessageEntity toEntity(MessageDto messageDto);

}
