package miu.edu.onlineRetailSystem.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipOrderEvent {
    private int customerId;
    private int orderId;
}
