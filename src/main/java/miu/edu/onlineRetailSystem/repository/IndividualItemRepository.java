package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.IndividualItem;
import miu.edu.onlineRetailSystem.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualItemRepository extends JpaRepository<IndividualItem,Integer> {
}
