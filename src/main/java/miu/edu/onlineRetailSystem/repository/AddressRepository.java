package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.contract.AddressResponse;
import miu.edu.onlineRetailSystem.domain.Address;
import miu.edu.onlineRetailSystem.nonDomain.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId AND a.addressType = :shippingAddress")
    List<Address> getShippingAddressByCustomerId(@Param("customerId") Integer customerId, @Param("shippingAddress") AddressType shippingAddress);

    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId AND a.addressType = :billingAddress")
    Address getBillingAddressByCustomerId(@Param("customerId") Integer customerId, @Param("billingAddress") AddressType billingAddress);

    @Query("select a from Address a where a.customer.id = :customerId")
    Collection<Address> findByCustomer(@Param("customerId") int customerId);

    @Query("select a from Address a where a.customer.id = :customerId and a.id = :addressId")
    Address findByIdAndCustomer(@Param("customerId") int customerId, @Param("addressId") int addressId);

    @Query("select a from Address a where a.customer.id = :customerId and a.isDefaultShippingAddress = true")
    Address findDefaultAddressByCustomer(@Param("customerId") int customerId);
}
