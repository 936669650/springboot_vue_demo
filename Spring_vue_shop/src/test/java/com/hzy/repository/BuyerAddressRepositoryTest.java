package com.hzy.repository;

import com.hzy.entity.BuyerAddress;
import com.hzy.entity.PhoneCategory;
import com.hzy.service.AddressService;
import com.hzy.service.PhoneService;
import com.hzy.vo.AddressVo;
import com.hzy.vo.DataVo;
import com.hzy.vo.SpecsDataVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuyerAddressRepositoryTest {

    @Autowired
    private BuyerAddressRepository buyerAddressRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    PhoneCategoryRepository phoneCategoryRepository;

//    @Test
//    public void findAll(){
////        SpecsDataVo specsByPhoneId = phoneService.findSpecsByPhoneId(1);
//        List<AddressVo> list = addressService.list();
//        int i= 0;
//
//    }

}