package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Builder
@Data
@Entity(name = "files")
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntity<Long> {

    @Column(name = "file_path")
    private String pathFile;

    @OneToOne(cascade = CascadeType.ALL)
    private Event event;

    public void setEvent(Event event) {
        this.event = event;
        event.setFile(this);
    }
}
