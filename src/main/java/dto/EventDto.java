package dto;

import lombok.Builder;
import lombok.Data;
import model.User;

import java.time.LocalDateTime;

@Data
@Builder
public class EventDto {

    private Long id;
    private LocalDateTime createdDate;
    private Long fileId;
    private Long userId;
}
