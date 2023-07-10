package miu.edu.onlineRetailSystem.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailEvent {
    private String to;
    private String message;
}
