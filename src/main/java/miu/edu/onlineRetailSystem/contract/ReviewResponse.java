package miu.edu.onlineRetailSystem.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private int id;
    private String title;
    private String description;
    private int stars;
    private LocalDateTime date;

//    private CustomerResponse buyerResponse;

}
