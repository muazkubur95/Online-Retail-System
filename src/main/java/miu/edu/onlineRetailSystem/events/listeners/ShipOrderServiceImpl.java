package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.domain.Order;
import miu.edu.onlineRetailSystem.events.DeliverEvent;
import miu.edu.onlineRetailSystem.events.EmailEvent;
import miu.edu.onlineRetailSystem.events.ShipOrderEvent;
import miu.edu.onlineRetailSystem.nonDomain.OrderStatus;
import miu.edu.onlineRetailSystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ShipOrderServiceImpl implements ShipOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    @EventListener
    public void shipOrder(ShipOrderEvent shipOrderEvent) {
        Order order = orderRepository.findByIdAndCustomer(shipOrderEvent.getCustomerId(),
                shipOrderEvent.getOrderId());
        order.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);
        publisher.publishEvent(new EmailEvent(order.getCustomer().getEmail(),
                "Your order has been shipped"));
        publisher.publishEvent(new DeliverEvent(shipOrderEvent.getCustomerId(),
                shipOrderEvent.getOrderId()));
    }
}
