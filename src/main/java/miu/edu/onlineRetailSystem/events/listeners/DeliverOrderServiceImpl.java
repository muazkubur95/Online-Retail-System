package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.domain.Order;
import miu.edu.onlineRetailSystem.events.DeliverEvent;
import miu.edu.onlineRetailSystem.events.EmailEvent;
import miu.edu.onlineRetailSystem.nonDomain.OrderStatus;
import miu.edu.onlineRetailSystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DeliverOrderServiceImpl implements DeliverOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    @EventListener
    public void deliverOrder(DeliverEvent deliverEvent) {
        Order order = orderRepository.findByIdAndCustomer(deliverEvent.getCustomerId(),
                deliverEvent.getOrderId());
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
        publisher.publishEvent(new EmailEvent(order.getCustomer().getEmail(),
                "Your order has been delivered"));
    }
}
