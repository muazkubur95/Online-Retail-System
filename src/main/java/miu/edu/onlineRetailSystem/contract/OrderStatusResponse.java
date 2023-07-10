package miu.edu.onlineRetailSystem.contract;

import lombok.Data;

public enum OrderStatusResponse {
    NEW,
    PLACED,
    PROCESSED,
    SHIPPED,
    DELIVERED,
    RETURNED
}
