package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.CreditCard;
import miu.edu.onlineRetailSystem.domain.Customer;
import miu.edu.onlineRetailSystem.domain.Review;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;



    @Test
    void findCreditCardByCustomer_WhenValidCustomerId_ShouldReturnCreditCards() {
        // Arrange
        Customer customer = new Customer();
        List<CreditCard> creditCards = new ArrayList<>();
        CreditCard creditCard1 = new CreditCard();
        CreditCard creditCard2 = new CreditCard();
        customer.getCreditCards().add(creditCard1);
        customer.getCreditCards().add(creditCard2);
        customer.getCreditCards().add(creditCard2);
        entityManager.persist(customer);
        entityManager.flush();

        Collection<CreditCard> result = customerRepository.findCreditCardByCustomer(customer.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(creditCard1));
        assertTrue(result.contains(creditCard2));
    }

    @Test
    void findCreditCardByCreditCardIdAndCustomer_WhenValidIds_ShouldReturnCreditCard() {
        // Arrange
        Customer customer = new Customer();
        List<CreditCard> creditCards = new ArrayList<>();
        CreditCard creditCard = new CreditCard();
        customer.getCreditCards().add(creditCard);
        entityManager.persist(customer);
        entityManager.flush();

        CreditCard result = customerRepository.findCreditCardByCreditCardIdAndCustomer(customer.getId(), creditCard.getId());

        assertNotNull(result);
        assertEquals(creditCard.getId(), result.getId());
        assertEquals(creditCard.getNumber(), result.getNumber());
        // Add more assertions as needed
    }

    @Test
    void findReviewsByCustomer_WhenValidCustomerId_ShouldReturnReviewsPage() {
        // Arrange
        Customer customer = new Customer();
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        Review review2 = new Review();
        reviews.add(review1);
        reviews.add(review2);
        customer.setReviews(reviews);
        entityManager.persist(customer);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);

        Page<Review> result = customerRepository.findReviewsByCustomer(customer.getId(), pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(review1));
        assertTrue(result.getContent().contains(review2));
    }

}
