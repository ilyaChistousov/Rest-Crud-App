package service;

import dto.UserDto;
import mapper.BaseMapper;
import mapper.FromUserToUserDtoMapperImpl;
import model.User;
import org.hibernate.graph.GraphSemantic;
import repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final BaseMapper<UserDto, User> mapper = new FromUserToUserDtoMapperImpl();

    public Long save(UserDto userDto) {
        var user = mapper.mappingFromDtoToEntity(userDto);
        userRepository.save(user);
        return user.getId();
    }

    public Optional<UserDto> getById(Long userId) {
        return userRepository.getById(userId, Map.of(GraphSemantic.LOAD.getJpaHintName(),
                userRepository.USER_EVENTS_GRAPH))
                .map(mapper::mappingFromEntityToDto);
    }

    public List<UserDto> getAll() {
        return userRepository.getAll(userRepository.USER_EVENTS_GRAPH).stream()
                .map(mapper::mappingFromEntityToDto)
                .collect(Collectors.toList());
    }

    public boolean update(UserDto userDto) {
        var maybeUser = userRepository.getById(userDto.getId());
        maybeUser.ifPresent(u -> {
            u.setUsername(userDto.getUsername());
            u.setEmail(userDto.getEmail());
            userRepository.update(u);
        });
        return maybeUser.isPresent();
    }

    public boolean delete(Long id) {
        var maybeUser = userRepository.getById(id);
        maybeUser.ifPresent(userRepository::delete);
        return maybeUser.isPresent();
    }
}
