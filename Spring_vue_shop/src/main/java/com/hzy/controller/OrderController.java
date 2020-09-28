package com.hzy.controller;

import com.hzy.dto.OrderDTO;
import com.hzy.exception.PhoneException;
import com.hzy.service.OrderService;
import com.hzy.utils.ResultVoUtil;
import com.hzy.valida.OrderValida;
import com.hzy.vo.OrderDetailVO;
import com.hzy.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public ResultVo create(@Valid @RequestBody OrderValida order, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数错误,orderForm={}",order);
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setSpecsId(order.getSpecsId());
        orderDTO.setBuyerAddress(order.getAddress());
        orderDTO.setBuyerName(order.getName());
        orderDTO.setBuyerPhone(order.getTel());
        orderDTO.setPhoneQuantity(order.getQuantity());
        OrderDTO result = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVoUtil.success(map);
    }

    @GetMapping("detail/{orderId}")
    public OrderDetailVO detail(@PathVariable String orderId){
        return orderService.detail(orderId);
    }

    @RequestMapping("pay/{orderId}")
    public ResultVo pay(@PathVariable("orderId") String orderId){
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderService.pay(orderId));
        return ResultVoUtil.success(map);
    }
}
