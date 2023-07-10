package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.CreditCardResponse;
import miu.edu.onlineRetailSystem.domain.CreditCard;

import java.util.Optional;

public interface CreditCardService {
    CreditCardResponse save(int customerId, CreditCardResponse creditCardResponse);

    CreditCardResponse update(int creditCardId, CreditCardResponse creditCardResponse);

    CreditCardResponse remove(int id);

    CreditCardResponse findById(int id);

//    CreditCardResponse getCreditCard(int customerId, int creditCardId);
}
