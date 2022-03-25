package service;

import dto.EventDto;
import mapper.BaseMapper;
import mapper.FromEventToEventDtoMapperExample;
import mapper.FromEventToEventDtoMapperImpl;
import model.Event;
import repository.EventRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventService {

    private final EventRepository eventRepository = new EventRepository();
    private final BaseMapper<EventDto, Event> mapper = new FromEventToEventDtoMapperImpl();
    private final FromEventToEventDtoMapperExample mapperExample = new FromEventToEventDtoMapperExample();

    public Long save(EventDto eventDto) {
        var event = mapper.mappingFromDtoToEntity(eventDto);
        eventRepository.save(event);
        return event.getId();
    }

    public Optional<EventDto> getById(Long eventId) {
        return eventRepository.getById(eventId)
                .map(mapper::mappingFromEntityToDto);

    }

    public List<EventDto> getAll() {
        return eventRepository.getAll().stream()
                .map(mapper::mappingFromEntityToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getByUserId(Long userId) {
        return eventRepository.getByUserId(userId).stream()
                .map(mapperExample::mapToDto)
                .collect(Collectors.toList());
    }

    public EventDto update(EventDto eventReadDto) {
        var event = mapper.mappingFromDtoToEntity(eventReadDto);
        eventRepository.update(event);
        return mapper.mappingFromEntityToDto(event);
    }

    public boolean delete(EventDto eventDto) {
        eventRepository.delete(mapper.mappingFromDtoToEntity(eventDto));
        return eventRepository.getById(eventDto.getId()).isPresent();
    }

}
