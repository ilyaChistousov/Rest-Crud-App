package mapper;

import dto.EventDto;
import model.Event;

public class FromEventToEventDtoMapperExample {

    public EventDto mapToDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .createdDate(event.getCreateData())
                .fileId(event.getFile().getId())
                .userId(event.getUser().getId())
                .build();
    }
}
