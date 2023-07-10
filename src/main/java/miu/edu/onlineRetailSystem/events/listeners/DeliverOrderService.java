package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.events.DeliverEvent;

public interface DeliverOrderService {
    void deliverOrder(DeliverEvent deliverEvent);
}
