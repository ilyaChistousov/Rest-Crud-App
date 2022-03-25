package service;

import dto.FileDto;
import mapper.BaseMapper;
import mapper.FromFileToFileDtoMapperImpl;
import model.File;
import model.Event;
import org.hibernate.graph.GraphSemantic;
import repository.FileRepository;
import repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileService {

    private final FileRepository fileRepository = new FileRepository();
    private final UserRepository userRepository = new UserRepository();
    private final BaseMapper<FileDto, File> mapper = new FromFileToFileDtoMapperImpl();

    public Long save(FileDto fileDto, Long userId) {
        var file = mapper.mappingFromDtoToEntity(fileDto);
        var event = Event.builder()
                .createData(LocalDateTime.now())
                .build();
        file.setEvent(event);

        var maybeUser = userRepository.getById(userId,
                Map.of(GraphSemantic.LOAD.getJpaHintName(),userRepository.USER_EVENTS_GRAPH));

        if(maybeUser.isPresent()) {

            fileRepository.save(file);

            maybeUser.get().addEvent(event);

            userRepository.update(maybeUser.get());

            return file.getId();
        } else {
            throw new NullPointerException();
        }
    }

    public Optional<FileDto> getById(Long fileId) {
        return fileRepository.getById(fileId)
                .map(mapper::mappingFromEntityToDto);

    }

    public List<FileDto> getAll() {
        return fileRepository.getAll().stream()
                .map(mapper::mappingFromEntityToDto)
                .collect(Collectors.toList());
    }

    public boolean update(FileDto fileDto) {
        var maybeFile = fileRepository.getById(fileDto.getId());
        maybeFile.ifPresent(f -> {
            f.setPathFile(fileDto.getPathFile());
            fileRepository.update(f);
        });

        return maybeFile.isPresent();
    }

    public boolean delete(Long fileId) {
        var maybeFile = fileRepository.getById(fileId);
        maybeFile.ifPresent(fileRepository::delete);
        return maybeFile.isPresent();
    }
}
