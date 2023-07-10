package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.Address;
import miu.edu.onlineRetailSystem.domain.Customer;
import miu.edu.onlineRetailSystem.nonDomain.AddressType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AddressRepository addressRepository;
    @Test
    //getShippingAddressByCustomerId
    public void whenFindByCustomerId_thenReturnShippingAddress() {
        // given
        List<Address> shippingAddressList = new ArrayList<>();
        Customer buyer = new Customer();
        buyer.setFirstName("John");
        buyer.setLastName("Doe");
        buyer.setEmail("john.doe@example.com");
        System.out.println("buyer"+buyer);

        Address shippingAddress = new Address();
        shippingAddress.setCity("City 2");
        shippingAddress.setStreet("Street 2");
        shippingAddress.setState("State 2");
        shippingAddress.setZipCode("12345");
        shippingAddress.setAddressType(AddressType.SHIPPING_ADDRESS);
        shippingAddressList.add(shippingAddress);
        entityManager.persist(buyer);
        shippingAddress.setCustomer(buyer);
        entityManager.persist(shippingAddress);
        entityManager.flush();
        // when
        List<Address> found = addressRepository.getShippingAddressByCustomerId(buyer.getId(), AddressType.SHIPPING_ADDRESS);
        // when
        assertThat(found.get(0))
                .isEqualTo(shippingAddress);

    }

    @Test
    //getBillingAddressByCustomerId
    public void whenFindByCustomerId_thenReturnBillingAddress() {
        // given
        List<Address> addresses = new ArrayList<>();
        Customer buyer = new Customer();
        buyer.setFirstName("John");
        buyer.setLastName("Doe");
        buyer.setEmail("john.doe@example.com");

        Address billingAddress = new Address();
        billingAddress.setCity("City 3");
        billingAddress.setStreet("Street 3");
        billingAddress.setState("State 3");
        billingAddress.setZipCode("12345");
        billingAddress.setAddressType(AddressType.BILLING_ADDRESS);
        entityManager.persist(buyer);
        billingAddress.setCustomer(buyer);
        entityManager.persist(buyer);
        entityManager.persist(billingAddress);
        entityManager.flush();
        System.out.println("customerId"+buyer.getId());
        // when
        System.out.println(billingAddress);
        Address found = addressRepository.getBillingAddressByCustomerId(buyer.getId(), AddressType.BILLING_ADDRESS);
//        //then
        assertThat(found)
                .isEqualTo(billingAddress);
    }
    
    @Test
    public void whenFindByCustomerId_thenReturnAddressList() {
        // given
        List<Address> addresses = new ArrayList<>();
        Customer buyer = new Customer();
        buyer.setFirstName("John");
        buyer.setLastName("Doe");
        buyer.setEmail("john.doe@example.com");

        Address billingAddress = new Address();
        billingAddress.setCity("City 3");
        billingAddress.setStreet("Street 3");
        billingAddress.setState("State 3");
        billingAddress.setZipCode("12345");
        
        Address shippingAddress = new Address();
        shippingAddress.setCity("City 2");
        shippingAddress.setStreet("Street 2");
        shippingAddress.setState("State 2");
        shippingAddress.setZipCode("12345");
        shippingAddress.setAddressType(AddressType.SHIPPING_ADDRESS);

        addresses.add(shippingAddress);
        addresses.add(billingAddress);
        billingAddress.setAddressType(AddressType.BILLING_ADDRESS);
        entityManager.persist(buyer);
        billingAddress.setCustomer(buyer);
        shippingAddress.setCustomer(buyer);
        entityManager.persist(buyer);
        entityManager.persist(shippingAddress);
        entityManager.persist(billingAddress);
        entityManager.flush();
        System.out.println("customerId"+buyer.getId());
        // when
        System.out.println(billingAddress);
        Collection<Address> found = addressRepository.findByCustomer(buyer.getId());
//        //then
        assertThat(found)
                .isEqualTo(addresses);
    }

    @Test
    public void whenfindByIdAndCustomerthenReturnAddress () {
        // given
        List<Address> shippingAddressList = new ArrayList<>();
        Customer buyer = new Customer();
        buyer.setFirstName("John");
        buyer.setLastName("Doe");
        buyer.setEmail("john.doe@example.com");

        Address billingAddress = new Address();
        billingAddress.setCity("City 3");
        billingAddress.setStreet("Street 3");
        billingAddress.setState("State 3");
        billingAddress.setZipCode("12345");
        billingAddress.setAddressType(AddressType.BILLING_ADDRESS);

        entityManager.persist(buyer);
        billingAddress.setCustomer(buyer);
        entityManager.persist(buyer);
        entityManager.persist(billingAddress);
        entityManager.flush();
        System.out.println("customerId"+buyer.getId());
        // when
        System.out.println(billingAddress);
        Address found = addressRepository.findByIdAndCustomer(buyer.getId(),billingAddress.getId());
//        //then
        assertThat(found)
                .isEqualTo(billingAddress);

    }
    @Test
    public void whenfindDefaultAddressByCustomerthenReturnAddress () {
        // given
        List<Address> shippingAddressList = new ArrayList<>();
        Customer buyer = new Customer();
        buyer.setFirstName("John");
        buyer.setLastName("Doe");
        buyer.setEmail("john.doe@example.com");

        Address billingAddress = new Address();
        billingAddress.setCity("City 3");
        billingAddress.setStreet("Street 3");
        billingAddress.setState("State 3");
        billingAddress.setZipCode("12345");
        billingAddress.setDefaultShippingAddress(true);
        entityManager.persist(buyer);
        billingAddress.setCustomer(buyer);
        entityManager.persist(buyer);
        entityManager.persist(billingAddress);
        entityManager.flush();
        System.out.println("customerId"+buyer.getId());
        // when
        System.out.println(billingAddress);
        Address found = addressRepository.findDefaultAddressByCustomer(buyer.getId());
//        //then
        assertThat(found)
                .isEqualTo(billingAddress);

    }

    }
    

