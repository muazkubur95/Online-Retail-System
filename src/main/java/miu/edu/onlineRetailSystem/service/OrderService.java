package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.OrderLineResponse;
import miu.edu.onlineRetailSystem.contract.OrderResponse;
import miu.edu.onlineRetailSystem.contract.ReviewResponse;
import miu.edu.onlineRetailSystem.nonDomain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface OrderService {

    OrderResponse save(int customerId, OrderResponse orderResponse);

    OrderResponse update(int customerId, int orderId, OrderResponse orderResponse);

    /**
     * return null when the order does not exist
     * throw an exception when a quantity of an orderLine is greater than the quantity in stock
     *  otherwise return an OrderResponse object
     * @param orderId
     * @return
     * @throws CustomerException
     */
    OrderResponse placeOrder(int customerId, int orderId);

    OrderResponse updateStatus(int customerId, int orderId, OrderStatus orderStatus);

    Page<OrderResponse> findAllByCustomer(int customerId, Pageable pageable);

    OrderResponse getOrder(int customerId, int orderId);

    Collection<OrderLineResponse> getCustomerOrderLines(int customerId, int orderId);
    OrderLineResponse getCustomerOrderLine(int customerId, int orderId, int orderLineId);

    ReviewResponse getReviewByCustomerAndOrder(int customer, int orderId, int reviewId);

    ReviewResponse getReviewByIdAndCustomerAndOrder(int customerId, int orderId, int reviewId);
}
