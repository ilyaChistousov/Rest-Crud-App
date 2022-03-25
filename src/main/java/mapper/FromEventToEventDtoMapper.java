package mapper;

import dto.EventDto;
import model.Event;
import org.mapstruct.Mapper;

@Mapper
public interface FromEventToEventDtoMapper extends BaseMapper<EventDto, Event> {

    @Override
    EventDto mappingFromEntityToDto(Event entity);

    @Override
    Event mappingFromDtoToEntity(EventDto dto);
}
