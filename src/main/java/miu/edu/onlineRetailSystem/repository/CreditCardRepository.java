package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.contract.CreditCardResponse;
import miu.edu.onlineRetailSystem.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

}
