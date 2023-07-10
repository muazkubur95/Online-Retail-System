package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

    public Optional<OrderLine> findById(int id);
}
