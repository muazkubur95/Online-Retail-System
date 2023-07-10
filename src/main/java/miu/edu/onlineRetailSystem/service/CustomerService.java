package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CustomerService {
    CustomerResponse save(CustomerResponse customerResponse);
    CustomerResponse update(int customerId, CustomerResponse customerResponse);

    CustomerResponse getCustomer(int customerId);

    Page<CustomerResponse> findAll(Pageable pageable);

    Collection<AddressResponse> getCustomerAddresses(int customerId);

    AddressResponse saveCustomerAddress(int customerId, AddressResponse addressResponse);

    AddressResponse getCustomerAddress(int customerId, int addressId);

    AddressResponse updateCustomerAddress(int customerId, int addressId, AddressResponse addressResponse);

    void deleteCustomerAddress(int customerId, int addressId);

    Collection<CreditCardResponse> getCustomerCreditCards(int customerId);

    CreditCardResponse saveCustomerCreditCard(int customerId, CreditCardResponse creditCardResponse);

    CreditCardResponse getCustomerCreditCard(int customerId, int creditCardId);

    CreditCardResponse updateCustomerCreditCard(int customerId, int creditCardId, CreditCardResponse creditCardResponse);

    CreditCardResponse deleteCustomerCreditCard(int customerId, int creditCardId);

    Page<OrderResponse> getCustomerOrders(int customerId, Pageable pageable);

    OrderResponse saveCustomerOrder(int customerId, OrderResponse orderResponse);

    OrderResponse getCustomerOrder(int customerId, int orderId);

    OrderResponse updateCustomerOrder(int customerId, int orderId, OrderResponse orderResponse);

    OrderResponse returnCustomerOrder(int customerId, int orderId);

    OrderResponse placeCustomerOrder(int customerId, int orderId);

    Collection<OrderLineResponse> getCustomerOrderLines(int customerId, int orderId);

    OrderLineResponse saveCustomerOrderLine(int customerId, int orderId, OrderLineResponse orderLineResponse);

    OrderLineResponse getCustomerOrderLine(int customerId, int orderId, int orderLineId);

    OrderLineResponse updateCustomerOrderLine(int customerId, int orderId, int orderLineId, OrderLineResponse orderLineResponse);

    OrderLineResponse deleteCustomerOrderLine(int customerId, int orderId, int orderLineId);

    ReviewResponse getCustomerOrderReview(int customerId, int orderId, int reviewId);

    ReviewResponse saveCustomerOrderItemReview(int customerId, int orderId, int itemId, ReviewResponse reviewResponse);

    ReviewResponse updateCustomerOrderReview(int customerId, int orderId, int itemId, int reviewId, ReviewResponse reviewResponse);

    ReviewResponse deleteCustomerOrderReview(int customerId, int orderId, int reviewId);

    ReviewResponse getCustomerOrderReviewWithReviewId(int customerId, int orderId, int reviewId);

    Collection<AddressResponse> getCustomerShippingAddresses(int customerId);
}
