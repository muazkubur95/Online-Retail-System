package miu.edu.onlineRetailSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import miu.edu.onlineRetailSystem.contract.ReviewResponse;
import miu.edu.onlineRetailSystem.domain.Customer;
import miu.edu.onlineRetailSystem.domain.Item;
import miu.edu.onlineRetailSystem.domain.Review;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.repository.CustomerRepository;
import miu.edu.onlineRetailSystem.repository.ItemRepository;
import miu.edu.onlineRetailSystem.repository.ReviewRepository;
import miu.edu.onlineRetailSystem.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ReviewResponse save(int customerId, int itemId, ReviewResponse reviewResponse) {
        Review review = mapper.map(reviewResponse, Review.class);
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Id", customerId)
        );
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException("Item", "Id", itemId)
        );
        customer.getReviews().add(review);
        review.setDate(LocalDateTime.now());
        review = reviewRepository.save(review);
        item.getReviews().add(review);

        return mapper.map(review, ReviewResponse.class);
    }

    @Override
    public ReviewResponse update(int reviewId, ReviewResponse reviewResponse) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(
                "Review", "Id", reviewId
        ));
//        Review review = mapper.map(reviewResponse, Review.class);
        review.setTitle(reviewResponse.getTitle());
        review.setDescription(reviewResponse.getDescription());
        review.setDate(LocalDateTime.now());
        review.setStars(reviewResponse.getStars());
        review = reviewRepository.save(review);

        return mapper.map(review, ReviewResponse.class);
    }

    @Override
    public ReviewResponse view(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->new ResourceNotFoundException("review","review",reviewId));
        ReviewResponse reviewResponse = mapper.map(review, ReviewResponse.class);
        return reviewResponse;
    }



    @Override
    public ReviewResponse remove(int id) {

        Review review = reviewRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("review","review",id));
        reviewRepository.delete(review);
        ReviewResponse reviewResponse = mapper.map(review, ReviewResponse.class);
        return reviewResponse;
    }

    @Override
    public ReviewResponse findById(int id) {
        Review review = reviewRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("review","review",id));;
        ReviewResponse reviewResponse = mapper.map(review, ReviewResponse.class);
        return reviewResponse;
    }
}
