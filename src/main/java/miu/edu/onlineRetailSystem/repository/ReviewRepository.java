package miu.edu.onlineRetailSystem.repository;

import miu.edu.onlineRetailSystem.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Integer> {



}
