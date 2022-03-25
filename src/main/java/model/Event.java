package model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event extends BaseEntity<Long> {

    @Column(name = "create_data")
    private LocalDateTime createData;

    @ManyToOne(fetch = FetchType.LAZY)
        private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private File file;

}