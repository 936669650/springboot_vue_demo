package com.hzy.service.impl;

import com.hzy.entity.BuyerAddress;
import com.hzy.repository.BuyerAddressRepository;
import com.hzy.service.AddressService;
import com.hzy.valida.AddressValida;
import com.hzy.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private BuyerAddressRepository buyerAddressRepository;

    @Override
    public List<AddressVo> findAll() {
        List<BuyerAddress> buyerAddressList = buyerAddressRepository.findAll();
        return buyerAddressList.stream()
                .map(e -> new AddressVo(
                        e.getAreaCode(),
                        e.getAddressId(),
                        e.getBuyerName(),
                        e.getBuyerPhone(),
                        e.getBuyerAddress()
                )).collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdate(AddressValida addressValida) {
        BuyerAddress buyerAddress;
        if(addressValida.getId() == null){
            buyerAddress = new BuyerAddress();
        }else{
            buyerAddress = buyerAddressRepository.findById(addressValida.getId()).get();
        }
        buyerAddress.setBuyerName(addressValida.getName());
        buyerAddress.setBuyerPhone(addressValida.getTel());
        StringBuffer buffer = new StringBuffer();
        buffer.append(addressValida.getProvince())
                .append(addressValida.getCity())
                .append(addressValida.getCounty())
                .append(addressValida.getAddressDetail());
        buyerAddress.setBuyerAddress(buffer.toString());
        buyerAddress.setAreaCode(addressValida.getAreaCode());
    }
}
