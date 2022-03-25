package mapper;

import dto.FileDto;
import model.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FromFileToFileDtoMapper extends BaseMapper<FileDto, File> {

    @Mapping(target = "event", ignore = true)
    @Override
    FileDto mappingFromEntityToDto(File entity);

    @Mapping(target = "event", ignore = true)
    @Override
    File mappingFromDtoToEntity(FileDto dto);
}
