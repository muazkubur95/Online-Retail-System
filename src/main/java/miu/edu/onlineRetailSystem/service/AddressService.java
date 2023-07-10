package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.AddressResponse;
import miu.edu.onlineRetailSystem.nonDomain.AddressType;

import java.util.Collection;
import java.util.List;

public interface AddressService {
    public AddressResponse save(int customerID,AddressResponse addressResponse);

    /**
     * Save shipping address
     * @param addressId
     * @param addressResponse
     * @return
     */
    AddressResponse update(int customer, int addressId, AddressResponse addressResponse);

    void delete(int customerId, int addressId);
    List<AddressResponse> getShippingAddressByCustomerId(Integer customerId, AddressType shippingAddress);
    AddressResponse getBillingAddressByCustomerId(Integer customerId, AddressType billingAddress);

    Collection<AddressResponse> getAddresses(int customerId);

    AddressResponse getAddress(int customerId, int addressId);
}
