package miu.edu.onlineRetailSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import miu.edu.onlineRetailSystem.contract.CreditCardResponse;
import miu.edu.onlineRetailSystem.domain.CreditCard;
import miu.edu.onlineRetailSystem.domain.Customer;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.repository.CreditCardRepository;
import miu.edu.onlineRetailSystem.repository.CustomerRepository;
import miu.edu.onlineRetailSystem.service.CreditCardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;
    @Override
    public CreditCardResponse save(int customerId, CreditCardResponse creditCardResponse) {

        CreditCard creditCard = mapper.map(creditCardResponse, CreditCard.class);

        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Id", customerId)
        );

        customer.getCreditCards().add(creditCard);

        creditCardRepository.save(creditCard);

        return creditCardResponse;
    }

    @Override
    public CreditCardResponse update(int creditCardId, CreditCardResponse creditCardResponse) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId).orElseThrow(() ->new ResourceNotFoundException("CreditCard", "Id", creditCardId));
//        CreditCard creditCard = mapper.map(creditCardResponse, CreditCard.class);
        creditCard.setNumber(creditCardResponse.getNumber());
        creditCard.setExpirationDate(creditCardResponse.getExpirationDate());
        creditCard.setSecurityCode(creditCardResponse.getSecurityCode());
        creditCardRepository.save(creditCard);
        return creditCardResponse;
    }

    @Override
    public CreditCardResponse remove(int id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("CreditCard", "Id", id));
        creditCardRepository.delete(creditCard);
        CreditCardResponse creditCardresponse = mapper.map(creditCard, CreditCardResponse.class);
        return creditCardresponse;
    }

    @Override
    public CreditCardResponse findById(int id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("CreditCard", "Id", id));
        CreditCardResponse creditCardresponse = mapper.map(creditCard, CreditCardResponse.class);
        return creditCardresponse;
    }

}
