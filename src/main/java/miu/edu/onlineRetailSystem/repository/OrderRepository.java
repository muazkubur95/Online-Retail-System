package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.Order;
import miu.edu.onlineRetailSystem.domain.OrderLine;
import miu.edu.onlineRetailSystem.nonDomain.OrderStatus;
import miu.edu.onlineRetailSystem.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.id = :orderId and o.status = :orderStatus and o.customer.id = :customerId")
    Order findByIdAndStatusAndCustomer(@Param("customerId") int customerId, @Param("orderId") int orderId, @Param("orderStatus") OrderStatus orderStatus);

    @Query("select o from Order o where o.customer.id = :customerId")
    Page<Order> findAllByCustomer(@Param(value = "customerId") int customerId, Pageable pageable);

    @Query("select o from Order o where o.customer.id = :customerId and o.id = :orderId")
    Order findByIdAndCustomer(@Param("customerId") int customerId, @Param("orderId") int orderId);

    @Query("select ol from Order o join o.lineItems ol where o.customer.id = :customerId and o.id = :orderId")
    Collection<OrderLine> findOrderLinesByCustomer(@Param("customerId") int customerId, @Param("orderId") int orderId);

    @Query("select ol from Order o join o.lineItems ol where o.customer.id = :customerId and o.id = :orderId and ol.id = :orderLineId")
    OrderLine findOrderLineByIdAndCustomerAndOrder(@Param("customerId") int customerId, @Param("orderId") int orderId, @Param("orderLineId") int orderLineId);

    @Query("select r from Order o join o.customer.reviews r where o.customer.id = :customerId and o.id = :orderId and r.id = :reviewId")
    Review findReviewByCustomerAndOrder(@Param("customerId") int customerId, @Param("orderId") int orderId, @Param("reviewId") int reviewId);

    @Query("select r from Order o join o.customer.reviews r where o.customer.id = :customerId and o.id = :orderId and r.id = :reviewId")
    Review findReviewByIdAndCustomerAndOrder(@Param("customerId") int customerId, @Param("orderId") int orderId, @Param("reviewId") int reviewId);
}
