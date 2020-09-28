package com.hzy.service;

import com.hzy.valida.AddressValida;
import com.hzy.vo.AddressVo;

import java.util.List;

public interface AddressService {
    List<AddressVo> findAll();
    void saveOrUpdate(AddressValida addressValida);
}
