package service;

import dto.EventDto;
import mapper.FromEventToEventDtoMapper;
import repository.EventRepository;
import java.util.List;
import java.util.stream.Collectors;

public class EventService {

    private final EventRepository eventRepository = new EventRepository();
    private final FromEventToEventDtoMapper mapper = new FromEventToEventDtoMapper();

    public List<EventDto> getByUserId(Long userId) {
        return eventRepository.getByUserId(userId).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

}
