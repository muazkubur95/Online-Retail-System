package miu.edu.onlineRetailSystem.service.impl;

import jakarta.transaction.Transactional;
import miu.edu.onlineRetailSystem.contract.*;
import miu.edu.onlineRetailSystem.domain.*;
import miu.edu.onlineRetailSystem.events.ProcessEvent;
import miu.edu.onlineRetailSystem.exception.CustomerErrorException;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.logging.ILogger;
import miu.edu.onlineRetailSystem.repository.*;
import miu.edu.onlineRetailSystem.nonDomain.OrderStatus;
import miu.edu.onlineRetailSystem.service.OrderLineService;
import miu.edu.onlineRetailSystem.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    ILogger logger;

    @Override
    public OrderResponse save(int customerId, OrderResponse orderResponse) {
        Order order = modelMapper.map(orderResponse, Order.class);
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer does not exists with id: " + customerId);
                    return new ResourceNotFoundException("Customer", "Id", customerId);
                }
        );
        Address defaultShippingAddress = addressRepository.findDefaultAddressByCustomer(customerId);
        if (defaultShippingAddress == null) {
            logger.error("Please add a shipping address before placing order");
            throw new CustomerErrorException("Please add a shipping address");
        }


        order.setShippingAddress(defaultShippingAddress);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.NEW);
        if (orderResponse.getLineItems().size() == 0) {
            logger.warning("Add at least one item!");
            throw new CustomerErrorException("Add at least one item!");
        }

        order.setLineItems(new ArrayList<>());

        order = orderRepository.save(order);

        for (OrderLineResponse orderLineResponse : orderResponse.getLineItems()) {
            orderLineService.save(order.getId(), orderLineResponse);
        }
        logger.info("Customer " + customer.getName() + " created order " + order.getId() + " successfully!");
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse update(int customerId, int orderId, OrderResponse orderResponse) {
        Order order = orderRepository.findByIdAndStatusAndCustomer(customerId, orderId, OrderStatus.NEW);
        if (order == null || orderResponse.getId() != orderId)
            throw new ResourceNotFoundException("Order", "Id", orderId);

        order = modelMapper.map(orderResponse, Order.class);
        order = orderRepository.save(order);

        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse placeOrder(int customerId, int orderId) {
        Order order = orderRepository.findByIdAndStatusAndCustomer(customerId, orderId, OrderStatus.NEW);
        if (order == null)
            throw new ResourceNotFoundException("Order", "Id", orderId);
        Address defaultShippingAddress = addressRepository.findDefaultAddressByCustomer(customerId);
        if (defaultShippingAddress == null) {
            logger.error("Please add a shipping address before placing order");
            throw new CustomerErrorException("Please add a shipping address");
        }

        updateStock(order);
        order.setStatus(OrderStatus.PLACED);
        order.setShippingAddress(defaultShippingAddress);
        order = orderRepository.save(order);

        publisher.publishEvent(new ProcessEvent(customerId, orderId));

        logger.info("Order " + orderId + "placed successfully!");
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse updateStatus(int customerId, int orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findByIdAndCustomer(customerId, orderId);
        if (order == null)
            throw new ResourceNotFoundException("Order", "Id", orderId);

        order.setStatus(orderStatus);
        order = orderRepository.save(order);

        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public Page<OrderResponse> findAllByCustomer(int customerId, Pageable pageable) {
        return orderRepository.findAllByCustomer(customerId, pageable).map(order -> modelMapper.map(order, OrderResponse.class));
    }

    @Override
    public OrderResponse getOrder(int customerId, int orderId) {
        Order order = orderRepository.findByIdAndCustomer(customerId, orderId);
        if (order == null)
            throw new ResourceNotFoundException("Order", "Id", orderId);

        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public Collection<OrderLineResponse> getCustomerOrderLines(int customerId, int orderId) {
        return orderRepository.findOrderLinesByCustomer(customerId, orderId)
                .stream()
                .map(orderLine -> modelMapper.map(orderLine, OrderLineResponse.class))
                .toList();
    }

    @Override
    public OrderLineResponse getCustomerOrderLine(int customerId, int orderId, int orderLineId) {
        OrderLine orderLine = orderRepository.findOrderLineByIdAndCustomerAndOrder(customerId, orderId, orderLineId);
        if (orderLine == null)
            throw new ResourceNotFoundException("OrderLine", "Id", orderLineId);

        return modelMapper.map(orderLine, OrderLineResponse.class);
    }

    @Override
    public ReviewResponse getReviewByCustomerAndOrder(int customerId, int orderId, int reviewId) {
        Review review = orderRepository.findReviewByCustomerAndOrder(customerId, orderId, reviewId);

        if (review == null)
            throw new ResourceNotFoundException("Review", "orderId", orderId);

        return modelMapper.map(review, ReviewResponse.class);
    }

    @Override
    public ReviewResponse getReviewByIdAndCustomerAndOrder(int customerId, int orderId, int reviewId) {
        Review review = orderRepository.findReviewByIdAndCustomerAndOrder(customerId, orderId, reviewId);
        if (review == null)
            throw new ResourceNotFoundException("Review", "Id", reviewId);

        return modelMapper.map(review, ReviewResponse.class);
    }

    private void updateStock(Order order) {

        for (OrderLine orderLine : order.getLineItems()) {
            int inStock = orderLine.getItem().getQuantityInStock();
            int quantity = orderLine.getQuantity();
            if (quantity > inStock) throw new CustomerErrorException("Quantity of " + orderLine.getItem().getName() +
                    " is greater than the quantity in stock");
            orderLine.getItem().setQuantityInStock(inStock - quantity);
        }
    }
}
