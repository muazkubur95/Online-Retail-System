package miu.edu.onlineRetailSystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import miu.edu.onlineRetailSystem.contract.*;
import miu.edu.onlineRetailSystem.domain.*;
import miu.edu.onlineRetailSystem.exception.CustomerErrorException;
import miu.edu.onlineRetailSystem.logging.ILogger;
import miu.edu.onlineRetailSystem.nonDomain.AddressType;
import miu.edu.onlineRetailSystem.nonDomain.OrderStatus;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.repository.*;
import miu.edu.onlineRetailSystem.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ILogger logger;

    @Override
    public CustomerResponse save(CustomerResponse customerResponse) {
        Customer customer = modelMapper.map(customerResponse, Customer.class);
        customer = customerRepository.save(customer);

        logger.info("Customer " + customer.getName() + " created!!");

        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse update(int customerId, CustomerResponse customerResponse) {
        // make sure the customer exist
        getCustomer(customerId);

        if (customerId != customerResponse.getId()) {
            logger.error("Customer " + customerResponse.getName() + " does not exists");
            throw new ResourceNotFoundException("Customer", "Id", customerId);
        }

        Customer customer = modelMapper.map(customerResponse, Customer.class);
        customer = customerRepository.save(customer);
        logger.error("Customer " + customer.getName() + " updated successfully");

        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse getCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Id", customerId)
        );

        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }

    @Override
    public Collection<AddressResponse> getCustomerAddresses(int customerId) {
        return addressService.getAddresses(customerId);
    }

    @Override
    public AddressResponse saveCustomerAddress(int customerId, AddressResponse addressResponse) {

        return addressService.save(customerId, addressResponse);
    }

    @Override
    public AddressResponse getCustomerAddress(int customerId, int addressId) {
        return addressService.getAddress(customerId, addressId);
    }

    @Override
    public AddressResponse updateCustomerAddress(int customer, int addressId, AddressResponse addressResponse) {
        return addressService.update(customer, addressId, addressResponse);
    }

    @Override
    public void deleteCustomerAddress(int customerId, int addressId) {

        addressService.delete(customerId, addressId);
        logger.info("Customer " + customerId + " deleted successfully");
    }

    @Override
    public Collection<CreditCardResponse> getCustomerCreditCards(int customerId) {
        return customerRepository.findCreditCardByCustomer(customerId)
                .stream()
                .map(creditCard -> modelMapper.map(creditCard, CreditCardResponse.class))
                .toList();
    }

    @Override
    public CreditCardResponse saveCustomerCreditCard(int customerId, CreditCardResponse creditCardResponse) {
        CustomerResponse customerResponse = getCustomer(customerId);

        return creditCardService.save(customerId, creditCardResponse);
    }

    @Override
    public CreditCardResponse getCustomerCreditCard(int customerId, int creditCardId) {
        CreditCard creditCard = customerRepository.findCreditCardByCreditCardIdAndCustomer(customerId, creditCardId);
        if (creditCard == null)
            throw new ResourceNotFoundException("CreditCard", "Id", creditCardId);

        return modelMapper.map(creditCard, CreditCardResponse.class);
    }

    @Override
    public CreditCardResponse updateCustomerCreditCard(int customerId, int creditCardId, CreditCardResponse creditCardResponse) {
        CreditCard creditCard = customerRepository.findCreditCardByCreditCardIdAndCustomer(customerId, creditCardId);
        if (creditCard == null || creditCardResponse.getId() != creditCardId)
            throw new ResourceNotFoundException("CreditCard", "Id", creditCardId);

        return creditCardService.update(creditCardId, creditCardResponse);
    }

    @Override
    public CreditCardResponse deleteCustomerCreditCard(int customerId, int creditCardId) {
        CreditCard creditCard = customerRepository.findCreditCardByCreditCardIdAndCustomer(customerId, creditCardId);
        if (creditCard == null)
            throw new ResourceNotFoundException("CreditCard", "Id", creditCardId);

        return creditCardService.remove(creditCardId);
    }

    @Override
    public Page<OrderResponse> getCustomerOrders(int customerId, Pageable pageable) {
        return orderService.findAllByCustomer(customerId, pageable);
    }

    @Override
    public OrderResponse saveCustomerOrder(int customerId, OrderResponse orderResponse) {
        return orderService.save(customerId, orderResponse);
    }

    @Override
    public OrderResponse getCustomerOrder(int customerId, int orderId) {
        return orderService.getOrder(customerId, orderId);
    }

    @Override
    public OrderResponse updateCustomerOrder(int customer, int orderId, OrderResponse orderResponse) {
        return orderService.update(customer, orderId, orderResponse);
    }

    @Override
    public OrderResponse returnCustomerOrder(int customerId, int orderId) {
        return orderService.updateStatus(customerId, orderId, OrderStatus.RETURNED);
    }

    @Override
    public OrderResponse placeCustomerOrder(int customerId, int orderId) {
        return orderService.placeOrder(customerId, orderId);
    }

    @Override
    public Collection<OrderLineResponse> getCustomerOrderLines(int customerId, int orderId) {
        return orderService.getCustomerOrderLines(customerId, orderId);
    }

    @Override
    public OrderLineResponse saveCustomerOrderLine(int customerId, int orderId, OrderLineResponse orderLineResponse) {
        // make sure the order exist for this customer
        getCustomerOrder(customerId, orderId);

        return orderLineService.save(orderId, orderLineResponse);
    }

    @Override
    public OrderLineResponse getCustomerOrderLine(int customerId, int orderId, int orderLineId) {
        return orderService.getCustomerOrderLine(customerId, orderId, orderLineId);
    }

    @Override
    public OrderLineResponse updateCustomerOrderLine(int customerId, int orderId, int orderLineId, OrderLineResponse orderLineResponse) {
        // make sure the orderLine exist for this customer and order
        getCustomerOrderLine(customerId, orderId, orderLineId);

        return orderLineService.update(orderLineId, orderLineResponse);
    }

    @Override
    public OrderLineResponse deleteCustomerOrderLine(int customerId, int orderId, int orderLineId) {
        // make sure the orderLine exist for this customer and order
        getCustomerOrderLine(customerId, orderId, orderLineId);

        return orderLineService.remove(orderLineId);
    }

    @Override
    public ReviewResponse getCustomerOrderReview(int customerId, int orderId, int reviewId) {
        return orderService.getReviewByCustomerAndOrder(customerId, orderId, reviewId);
    }

    @Override
    public ReviewResponse saveCustomerOrderItemReview(int customerId, int orderId, int itemId, ReviewResponse reviewResponse) {
        // make sure order exist for this customer before adding a review
        getCustomerOrder(customerId, orderId);

        return reviewService.save(customerId, itemId, reviewResponse);
    }

    @Override
    public ReviewResponse updateCustomerOrderReview(int customerId, int orderId, int itemId, int reviewId, ReviewResponse reviewResponse) {
        // make sure the review exist for this customerId and orderId
        getCustomerOrderReviewWithReviewId(customerId, orderId, reviewId);
        logger.info("customer " + customerId + " updated review " + " for Item " + itemId + " in order " + itemId + " updated!");

        return reviewService.update(reviewId, reviewResponse);
    }

    @Override
    public ReviewResponse deleteCustomerOrderReview(int customerId, int orderId, int reviewId) {
        // make sure the review exist for this customerId and orderId
        getCustomerOrderReviewWithReviewId(customerId, orderId, reviewId);

        return reviewService.remove(reviewId);
    }

    @Override
    public ReviewResponse getCustomerOrderReviewWithReviewId(int customerId, int orderId, int reviewId) {
        return orderService.getReviewByIdAndCustomerAndOrder(customerId, orderId, reviewId);
    }

    @Override
    public Collection<AddressResponse> getCustomerShippingAddresses(int customerId) {
        return addressService.getShippingAddressByCustomerId(customerId, AddressType.SHIPPING_ADDRESS);
    }
}
