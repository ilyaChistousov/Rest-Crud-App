package mapper;

import dto.UserDto;
import model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FromUserToUserDtoMapper extends BaseMapper<UserDto, User> {

    @Mapping(target = "events", ignore = true)
    @Override
    UserDto mappingFromEntityToDto(User entity);

    @Mapping(target = "events", ignore = true)
    @Override
    User mappingFromDtoToEntity(UserDto dto);
}
