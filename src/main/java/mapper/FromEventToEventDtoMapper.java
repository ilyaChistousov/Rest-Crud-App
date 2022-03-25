package mapper;

import dto.EventDto;
import model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FromEventToEventDtoMapper extends BaseMapper<EventDto, Event> {

    @Mapping(target = "file", ignore = true)
    @Override
    EventDto mappingFromEntityToDto(Event entity);

    @Mapping(target = "file", ignore = true)
    @Override
    Event mappingFromDtoToEntity(EventDto dto);
}
