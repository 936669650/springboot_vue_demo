package com.hzy.service;

import com.hzy.dto.OrderDTO;
import com.hzy.vo.OrderDetailVO;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
    OrderDetailVO detail(String orderId);
    String pay(String orderId);
}
