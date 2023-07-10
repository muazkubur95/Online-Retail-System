package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.events.ProcessEvent;

public interface ProcessOrderService {
    void processOrder(ProcessEvent processEvent);
}
