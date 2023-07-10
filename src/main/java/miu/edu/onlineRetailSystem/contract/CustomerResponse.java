package miu.edu.onlineRetailSystem.contract;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.onlineRetailSystem.nonDomain.Role;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

//    private AddressResponse billingAddress;

    private List<AddressResponse> shippingAddresses = new ArrayList<>();

    private List<CreditCardResponse> creditCards = new ArrayList<>();

    List<ReviewResponse> reviewResponses = new ArrayList<>();

//    private AddressResponse defaultShippingAddress;

    public String getName() {
        return firstName + " " + lastName;
    }
}
