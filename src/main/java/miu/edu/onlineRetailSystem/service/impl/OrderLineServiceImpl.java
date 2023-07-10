package miu.edu.onlineRetailSystem.service.impl;

import miu.edu.onlineRetailSystem.contract.OrderLineResponse;
import miu.edu.onlineRetailSystem.domain.Item;
import miu.edu.onlineRetailSystem.domain.Order;
import miu.edu.onlineRetailSystem.domain.OrderLine;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.repository.AddressRepository;
import miu.edu.onlineRetailSystem.repository.ItemRepository;
import miu.edu.onlineRetailSystem.repository.OrderLineRepository;
import miu.edu.onlineRetailSystem.repository.OrderRepository;
import miu.edu.onlineRetailSystem.service.OrderLineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public OrderLineResponse findById(int orderLineId) {
        OrderLine orderLine = orderLineRepository.findById(orderLineId).orElseThrow(
                () -> new ResourceNotFoundException("OrderLine", "Id", orderLineId)
        );

        return mapper.map(orderLine, OrderLineResponse.class);
    }

    @Override
    public OrderLineResponse save(int orderId, OrderLineResponse orderLineResponse) {
        OrderLine orderLine = mapper.map(orderLineResponse, OrderLine.class);
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order", "Id", orderId)
        );
        int itemId = orderLineResponse.getItem().getId();
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException("Item", "Id", itemId)
        );

        order.getLineItems().add(orderLine);
        orderLine.setItem(item);
        OrderLine savedOrderLine = orderLineRepository.save(orderLine);

        return mapper.map(savedOrderLine, OrderLineResponse.class);
    }

    @Override
    public OrderLineResponse update(int orderLineId, OrderLineResponse orderLineResponse) {

        OrderLine orderLine = orderLineRepository.findById(orderLineId).orElseThrow(
                () -> new ResourceNotFoundException("OrderLine", "Id", orderLineId)
        );

        OrderLine newOrderLine = mapper.map(orderLineResponse, OrderLine.class);
        int itemId = orderLineResponse.getItem().getId();
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException("Item", "Id", itemId)
        );
        orderLine.setDiscount(newOrderLine.getDiscount());
        orderLine.setQuantity(newOrderLine.getQuantity());
        orderLine.setItem(item);

        OrderLine savedOrderLine = orderLineRepository.save(orderLine);
        return mapper.map(savedOrderLine, OrderLineResponse.class);
    }

    @Override
    public List<OrderLineResponse> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineRepository.findAll();

        return orderLines
                .stream()
                .map((orderLine) -> mapper.map(orderLine, OrderLineResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderLineResponse remove(int orderLineId) {
        return null;
    }
}
