package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.ReviewResponse;
import miu.edu.onlineRetailSystem.domain.Review;
import miu.edu.onlineRetailSystem.exception.ResourceNotFoundException;
import miu.edu.onlineRetailSystem.repository.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewServiceImplTests {
    @Mock
    private ModelMapper modelMapper;
    @Autowired
    private ReviewService reviewService;
    @MockBean
    private ReviewRepository reviewRepository;
    @Before
    public void setUp() {
        int reviewId =1;
        Review review = new Review(reviewId,"Review on watch","good",4, LocalDateTime.of(2017, 2, 13, 15, 56));
        Optional<Review> reviewOptional = Optional.of(review);
        Mockito.when(reviewRepository.findById(1))
                .thenReturn(reviewOptional);
    }
    //Testing on save method
//    @Test
//    public void whenValidSaveThenReviewShouldBeSave() {
//        ReviewResponse reviewResponse = new ReviewResponse(1,"Review on watch","good",3, LocalDateTime.of(2017, 2, 13, 15, 56) );
//        List<ReviewResponse> list = new ArrayList<>();
//        list.add(reviewService.save(1,1,reviewResponse));
//        ReviewResponse result = list.get(0);
//        assertThat(reviewResponse).isEqualTo(result);
//    }
    //testing on remove method
    @Test
    public void whenValidRemoveThenReviewShouldBeRemove() {
        int reviewId =1;
        ReviewResponse reviewResponse = new ReviewResponse(reviewId,"Review on watch","good",4, LocalDateTime.of(2017, 2, 13, 15, 56));
        List<ReviewResponse> list = new ArrayList<>();
        list.add(reviewResponse);
        ReviewResponse response = list.get(0);
        ReviewResponse removed = reviewService.remove(reviewId);
        assertThat(response).isEqualTo(removed);

    }


    //testing on update method
//    @Test
//    public void whenValidUpdateThenReviewShouldBeUpdate() {
//        int reviewId = 1;
//        ReviewResponse reviewResponse = new ReviewResponse(reviewId,"Review on watch","good",4, LocalDateTime.of(2017, 2, 13, 15, 56));
//        ReviewResponse updatedReviewResponse = new ReviewResponse(reviewId,"Review on watch","bad",1, LocalDateTime.of(2017, 2, 13, 15, 56));
//        ReviewResponse update = reviewService.update(reviewId,updatedReviewResponse);
//        assertThat(updatedReviewResponse).isEqualTo(update);
//
//    }

    //testing on findById method
    @Test
    public void whenValidViewThenReviewShouldBeFound() {
        int reviewId =1;
        ReviewResponse found = reviewService.findById(1);
        assertThat(found.getId()).isEqualTo(reviewId);
    }

    //failure case
    @Test

    public void remove_WhenReviewDoesNotExist_ShouldThrowResourceNotFoundException() {

        int reviewId = 1;

        ReviewResponse reviewResponse = new ReviewResponse();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            reviewService.remove(reviewId);

        });

        verify(reviewRepository).findById(reviewId);

        verifyNoMoreInteractions(reviewRepository, modelMapper);

    }
    @Test

    public void update_WhenReviewDoesNotExist_ShouldThrowResourceNotFoundException() {

        int reviewId = 1;

        ReviewResponse reviewResponse = new ReviewResponse();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {

            reviewService.update(reviewId, reviewResponse);

        });

        verify(reviewRepository).findById(reviewId);
        verifyNoMoreInteractions(reviewRepository, modelMapper);
    }
    @Test
    public void view_WhenReviewDoesNotExist_ShouldThrowResourceNotFoundException() {
        int reviewId = 1;

        ReviewResponse reviewResponse = new ReviewResponse();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {

            reviewService.findById(reviewId);

        });

        verify(reviewRepository).findById(reviewId);
        verifyNoMoreInteractions(reviewRepository, modelMapper);

    }



}
