package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.CreditCardResponse;
import miu.edu.onlineRetailSystem.domain.CreditCard;
import miu.edu.onlineRetailSystem.domain.Customer;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.repository.CreditCardRepository;
import miu.edu.onlineRetailSystem.repository.CustomerRepository;
import miu.edu.onlineRetailSystem.service.impl.CreditCardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreditCardServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CreditCardRepository creditCardRepository;
    @InjectMocks
    private CreditCardService creditCardService = new CreditCardServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_WhenCustomerExists_ShouldReturnCreditCardResponse() {
        int customerId = 1;
        CreditCardResponse creditCardResponse = new CreditCardResponse();
        Customer customer = new Customer();
        customer.setId(customerId);
        CreditCard creditCard = new CreditCard();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(modelMapper.map(creditCardResponse, CreditCard.class)).thenReturn(creditCard);

        CreditCardResponse result = creditCardService.save(customerId, creditCardResponse);

        verify(customerRepository).findById(customerId);
        verify(creditCardRepository).save(creditCard);
        assertSame(creditCardResponse, result);
    }

    @Test
    void remove_WhenCreditCardExists_ShouldReturnCreditCardResponse() {
        int creditCardId = 1;
        CreditCard creditCard = new CreditCard();
        creditCard.setId(creditCardId);
        when(creditCardRepository.findById(creditCardId)).thenReturn(Optional.of(creditCard));
        when(modelMapper.map(any(CreditCard.class), eq(CreditCardResponse.class))).thenReturn(new CreditCardResponse());

        CreditCardResponse result = creditCardService.remove(creditCardId);

//        assertNotNull(result);
        verify(creditCardRepository).findById(creditCardId);
        verify(creditCardRepository).delete(creditCard);
        verify(modelMapper).map(creditCard, CreditCardResponse.class);
        verifyNoMoreInteractions(creditCardRepository, modelMapper);
    }

    @Test
    void remove_WhenCreditCardDoesNotExist_ShouldThrowResourceNotFoundException() {
        int creditCardId = 1;
        when(creditCardRepository.findById(creditCardId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            creditCardService.remove(creditCardId);
        });
        verify(creditCardRepository).findById(creditCardId);
        verifyNoMoreInteractions(creditCardRepository, modelMapper);
    }



    @Test
    void update_WhenCreditCardDoesNotExist_ShouldThrowResourceNotFoundException() {
        int creditCardId = 1;
        CreditCardResponse creditCardResponse = new CreditCardResponse();
        when(creditCardRepository.findById(creditCardId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            creditCardService.update(creditCardId, creditCardResponse);
        });
        verify(creditCardRepository).findById(creditCardId);
        verifyNoMoreInteractions(creditCardRepository, modelMapper);
    }

    @Test
    void findById_WhenCreditCardExists_ShouldReturnCreditCardResponse() {
        int creditCardId = 1;
        CreditCard creditCard = new CreditCard();
        creditCard.setId(creditCardId);
        CreditCardResponse creditCardResponse = new CreditCardResponse();
        when(creditCardRepository.findById(creditCardId)).thenReturn(Optional.of(creditCard));
        when(modelMapper.map(creditCard, CreditCardResponse.class)).thenReturn(creditCardResponse);

        CreditCardResponse result = creditCardService.findById(creditCardId);

        assertEquals(creditCardResponse, result);
        verify(creditCardRepository).findById(creditCardId);
        verify(modelMapper).map(creditCard, CreditCardResponse.class);
        verifyNoMoreInteractions(creditCardRepository, modelMapper);
    }


    @Test
    void findById_WhenCreditCardDoesNotExist_ShouldThrowResourceNotFoundException() {
        int creditCardId = 1;
        when(creditCardRepository.findById(creditCardId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            creditCardService.findById(creditCardId);
        });
        verify(creditCardRepository).findById(creditCardId);
        verifyNoMoreInteractions(creditCardRepository, modelMapper);
    }

}









