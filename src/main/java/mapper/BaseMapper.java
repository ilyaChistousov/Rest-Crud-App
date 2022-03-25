package mapper;

import org.mapstruct.Context;

public interface BaseMapper <D, E> {

    D mappingFromEntityToDto(E entity);

    E mappingFromDtoToEntity(D dto);
}
