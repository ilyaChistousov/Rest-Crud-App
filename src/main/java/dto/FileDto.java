package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {

    private Long id;
    private String pathFile;
    private EventDto event;
}
