package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.domain.Order;
import miu.edu.onlineRetailSystem.events.EmailEvent;
import miu.edu.onlineRetailSystem.events.ProcessEvent;
import miu.edu.onlineRetailSystem.events.ShipOrderEvent;
import miu.edu.onlineRetailSystem.nonDomain.OrderStatus;
import miu.edu.onlineRetailSystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class ProcessOrderServiceImpl implements ProcessOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    @Async
    @EventListener
    public void processOrder(ProcessEvent processEvent) {
        Order order = orderRepository.findByIdAndCustomer(processEvent.getCustomerId(),
                processEvent.getOrderId());
        order.setStatus(OrderStatus.PROCESSED);
        orderRepository.save(order);
        publisher.publishEvent(new EmailEvent(order.getCustomer().getEmail(),
                "Your order has been processed"));
        publisher.publishEvent(new ShipOrderEvent(processEvent.getCustomerId(),
                processEvent.getOrderId()));
    }
}
