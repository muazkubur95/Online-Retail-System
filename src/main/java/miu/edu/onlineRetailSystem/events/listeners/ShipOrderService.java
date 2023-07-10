package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.events.ShipOrderEvent;

public interface ShipOrderService {
    void shipOrder(ShipOrderEvent shipOrderEvent);
}
