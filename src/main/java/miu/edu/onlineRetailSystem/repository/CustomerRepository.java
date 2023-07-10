package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.CreditCard;
import miu.edu.onlineRetailSystem.domain.Customer;
import miu.edu.onlineRetailSystem.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select cc from Customer c join c.creditCards cc where c.id = :customerId")
    Collection<CreditCard> findCreditCardByCustomer(@Param("customerId") int customerId);

    @Query("select cc from Customer c join c.creditCards cc where c.id = :customerId and cc.id = :creditCardId")
    CreditCard findCreditCardByCreditCardIdAndCustomer(@Param("customerId") int customerId, @Param("creditCardId") int creditCardId);

    @Query("select r from Customer c join c.reviews r where c.id = :customerId")
    Page<Review> findReviewsByCustomer(@Param("customerId") int customerId, Pageable pageable);

    Optional<Customer> findByEmail(String email);
}
