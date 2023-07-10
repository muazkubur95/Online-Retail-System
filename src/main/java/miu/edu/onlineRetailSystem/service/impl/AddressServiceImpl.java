package miu.edu.onlineRetailSystem.service.impl;

import jakarta.transaction.Transactional;
import miu.edu.onlineRetailSystem.contract.AddressResponse;
import miu.edu.onlineRetailSystem.domain.Address;
import miu.edu.onlineRetailSystem.nonDomain.AddressType;
import miu.edu.onlineRetailSystem.domain.Customer;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.repository.AddressRepository;
import miu.edu.onlineRetailSystem.repository.CustomerRepository;
import miu.edu.onlineRetailSystem.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public AddressResponse save(int customerID, AddressResponse addressResponse) {
        if (addressResponse.isDefaultShippingAddress())
            changeDefaultShippingAddress(customerID);
        else if (addressResponse.getAddressType() == AddressType.SHIPPING_ADDRESS) {
            Address defaultAddress = addressRepository.findDefaultAddressByCustomer(customerID);
            if (defaultAddress == null)
                addressResponse.setDefaultShippingAddress(true);
        } else {
            addressResponse.setDefaultShippingAddress(false);
        }
        Address address = mapper.map(addressResponse, Address.class);
        Customer customer = customerRepository.findById(customerID).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Id", customerID)
        );
        address.setCustomer(customer);
        address = addressRepository.save(address);

        return mapper.map(address, AddressResponse.class);
    }

    private void changeDefaultShippingAddress(int customerId) {
        Address address = addressRepository.findDefaultAddressByCustomer(customerId);

        if (address != null) {
            address.setDefaultShippingAddress(false);
            addressRepository.save(address);
        }
    }

    @Override
    public AddressResponse update(int customerId, int addressId, AddressResponse addressResponse) {
        Address address = addressRepository.findByIdAndCustomer(customerId, addressId);
        if (address == null || addressResponse.getId() != addressId)
            throw new ResourceNotFoundException("Address", "Id", addressId);

        address = mapper.map(addressResponse, Address.class);

        address = addressRepository.save(address);

        return mapper.map(address, AddressResponse.class);
    }

    public void delete(int customerId, int addressId) {
        Address address = addressRepository.findByIdAndCustomer(customerId, addressId);
        if (address == null)
            throw new ResourceNotFoundException("Address", "Id", addressId);

        addressRepository.deleteById(addressId);
    }

    @Override
    public List<AddressResponse> getShippingAddressByCustomerId(Integer customerId, AddressType addressType) {
        Customer foundCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));

        return addressRepository.getShippingAddressByCustomerId(customerId, addressType)
                .stream().map(entity -> mapper.map(entity, AddressResponse.class))
                .collect(Collectors.toList());

    }

    @Override
    public AddressResponse getBillingAddressByCustomerId(Integer customerId, AddressType addressType) {
        Customer foundCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));

        return mapper.map(addressRepository.getBillingAddressByCustomerId(customerId, addressType), AddressResponse.class);

    }

    @Override
    public Collection<AddressResponse> getAddresses(int customerId) {

        return addressRepository.findByCustomer(customerId)
                .stream()
                .map(address -> mapper.map(address, AddressResponse.class))
                .toList();
    }

    @Override
    public AddressResponse getAddress(int customerId, int addressId) {
        Address address = addressRepository.findByIdAndCustomer(customerId, addressId);
        if (address == null)
            throw new ResourceNotFoundException("Address", "Id", addressId);

        return mapper.map(address, AddressResponse.class);
    }


}
