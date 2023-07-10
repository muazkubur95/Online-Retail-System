package miu.edu.onlineRetailSystem.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.onlineRetailSystem.nonDomain.AddressType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private int id;
    private String street;
    private String city;
    private String state;
    private String zipCode;

    private CustomerResponse customerResponse;

    private AddressType addressType;

    private boolean isDefaultShippingAddress = false;

}
