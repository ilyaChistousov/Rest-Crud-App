package mapper;

public interface BaseMapper <D, E> {

    D mappingFromEntityToDto(E entity);

    E mappingFromDtoToEntity(D dto);
}
