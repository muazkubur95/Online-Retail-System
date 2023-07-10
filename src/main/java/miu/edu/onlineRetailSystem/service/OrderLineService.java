package miu.edu.onlineRetailSystem.service;

import miu.edu.onlineRetailSystem.contract.OrderLineResponse;

import java.util.List;

public interface OrderLineService {

    OrderLineResponse findById(int orderLineId);

    OrderLineResponse save(int orderId, OrderLineResponse orderLineResponse);

    OrderLineResponse update(int orderLineId, OrderLineResponse orderLineResponse);

    OrderLineResponse remove(int orderLineId);

    List<OrderLineResponse> getAllOrderLines();
}
