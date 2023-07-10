package miu.edu.onlineRetailSystem.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private int id;

    private CustomerResponse customer;

    private AddressResponse shippingAddress;

    private OrderStatusResponse status;

    private List<OrderLineResponse> lineItems = new ArrayList<>();

    public void addOrderLineResponse(OrderLineResponse orderLineResponse) {
        this.lineItems.add(orderLineResponse);
    }
}
