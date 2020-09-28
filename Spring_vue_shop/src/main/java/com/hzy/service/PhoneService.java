package com.hzy.service;

import com.hzy.vo.DataVo;
import com.hzy.vo.PhoneInfoVo;
import com.hzy.vo.SpecsDataVo;

import java.util.List;

public interface PhoneService {
    DataVo findDataVo();
    SpecsDataVo findSpecsByPhoneId(Integer phoneId);
    void subStock(Integer specsId, Integer quantity);
    List<PhoneInfoVo> findPhoneInfoVoBycateGoryType(Integer categrory);
}
