package com.hzy.service.impl;

import com.hzy.dto.OrderDTO;
import com.hzy.entity.OrderMaster;
import com.hzy.entity.PhoneInfo;
import com.hzy.entity.PhoneSpecs;
import com.hzy.enums.PayStatusEnum;
import com.hzy.enums.ResultEnum;
import com.hzy.exception.PhoneException;
import com.hzy.repository.OrderMasterRepository;
import com.hzy.repository.PhoneInfoRepository;
import com.hzy.repository.PhoneSpecsRepository;
import com.hzy.service.OrderService;
import com.hzy.service.PhoneService;
import com.hzy.utils.KeyUtils;
import com.hzy.vo.OrderDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private PhoneSpecsRepository phoneSpecsRepository;
    @Autowired
    private PhoneInfoRepository phoneInfoRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private PhoneService phoneService;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        PhoneSpecs phoneSpecs = phoneSpecsRepository.findById(orderDTO.getSpecsId()).get();
        if(phoneSpecs == null){
            log.error("【创建订单】规格不存在,phoneSpecs={}",phoneSpecs);
            throw new PhoneException(ResultEnum.SPECS_NOT_EXIST);
        }
        BeanUtils.copyProperties(phoneSpecs,orderMaster);
        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();
        if(phoneInfo == null){
            log.error("【创建订单】规格不存在,phoneSpecs={}",phoneSpecs);
            throw new PhoneException(ResultEnum.PHONE_NOT_EXIST);
        }
        BeanUtils.copyProperties(phoneInfo,orderMaster);

        //总价
        BigDecimal orderAmount = phoneSpecs.getSpecsPrice().divide(new BigDecimal(100))
                .multiply(new BigDecimal(orderDTO.getPhoneQuantity()))
                .add(new BigDecimal(10));
        orderMaster.setOrderAmount(orderAmount);
        //id
        orderMaster.setOrderId(KeyUtils.createUniqueKey());
        orderDTO.setOrderId(orderMaster.getOrderId());
        //status
        orderMaster.setPayStatus(PayStatusEnum.UNPIAD.getCode());
        orderMasterRepository.save(orderMaster);
        //库存修改
        phoneService.subStock(orderDTO.getSpecsId(),orderDTO.getPhoneQuantity());
        return orderDTO;
    }

    @Override
    public OrderDetailVO detail(String orderId) {
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if(orderMaster == null){
            log.error("【查询订单】订单不存在,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_EXIST);
        }
        orderDetailVO.setSpecsPrice(orderMaster.getSpecsPrice().divide(new BigDecimal(100))+".00");
        BeanUtils.copyProperties(orderMaster,orderDetailVO);
        return orderDetailVO;
    }

    @Override
    public String pay(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if(orderMaster == null){
            log.error("【支付订单】订单不存在,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(orderMaster.getPayStatus().equals(PayStatusEnum.UNPIAD.getCode())){
            orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
            orderMasterRepository.save(orderMaster);
        } else {
            log.error("【支付订单】订单已支付,orderMaster={}",orderMaster);
        }
        return orderId;
    }
}
