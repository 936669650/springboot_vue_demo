package com.hzy.service.impl;

import com.hzy.entity.PhoneCategory;
import com.hzy.entity.PhoneInfo;
import com.hzy.entity.PhoneSpecs;
import com.hzy.enums.ResultEnum;
import com.hzy.exception.PhoneException;
import com.hzy.repository.PhoneCategoryRepository;
import com.hzy.repository.PhoneInfoRepository;
import com.hzy.repository.PhoneSpecsRepository;
import com.hzy.service.PhoneService;
import com.hzy.utils.PhoneUtils;
import com.hzy.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneInfoRepository phoneInfoRepository;

    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;

    @Autowired
    private PhoneSpecsRepository phoneSpecsRepository;

    @Override
    public DataVo findDataVo() {
        DataVo dataVo = new DataVo();
        List<PhoneCategory> phoneCategoryList = phoneCategoryRepository.findAll();
        List<PhoneCategoryVo> phoneCategoryVoList = phoneCategoryList.stream()
                .map(e -> new PhoneCategoryVo(
                        e.getCategoryName(),
                        e.getCategoryType()
                )).collect(Collectors.toList());

        List<PhoneInfo> phoneInfoList = phoneInfoRepository.findAllByCategoryType(phoneCategoryList.get(0).getCategoryType());
        List<PhoneInfoVo> phoneInfoVoList = phoneInfoList.stream()
                .map(e -> new PhoneInfoVo(
                        e.getPhoneId(),
                        e.getPhoneName(),
                        e.getPhonePrice()+".00",
                        e.getPhoneDescription(),
                        e.getPhoneIcon(),
                        PhoneUtils.getTags(e.getPhoneTag())
                )).collect(Collectors.toList());
        dataVo.setCategories(phoneCategoryVoList);
        dataVo.setPhones(phoneInfoVoList);
        return dataVo;
    }

    @Override
    public List<PhoneInfoVo> findPhoneInfoVoBycateGoryType(Integer categroryType) {
        List<PhoneInfo> phoneInfoList = phoneInfoRepository.findAllByCategoryType(categroryType);
        List<PhoneInfoVo> phoneInfoVoList = phoneInfoList.stream().map(e -> new PhoneInfoVo(
                e.getPhoneId(),
                e.getPhoneName(),
                e.getPhonePrice()+".00",
                e.getPhoneDescription(),
                e.getPhoneIcon(),
                PhoneUtils.getTags(e.getPhoneTag())
        )).collect(Collectors.toList());
        return phoneInfoVoList;
    }

    @Override
    public SpecsDataVo findSpecsByPhoneId(Integer phoneId) {
        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneId).get();
        List<PhoneSpecs> phoneSpecsList = phoneSpecsRepository.findAllByPhoneId(phoneId);

        //tree封装
        List<PhoneSpecsVo> phoneSpecsVos = new ArrayList<>();
        List<PhoneSpecsListVo> phoneSpecsListVos = new ArrayList<>();
        PhoneSpecsVo phoneSpecsVo;
        PhoneSpecsListVo phoneSpecsListVo;
        for (PhoneSpecs phoneSpecs : phoneSpecsList) {
            phoneSpecsVo = new PhoneSpecsVo();
            phoneSpecsListVo = new PhoneSpecsListVo();
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsVo);
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsListVo);
            phoneSpecsVos.add(phoneSpecsVo);
            phoneSpecsListVos.add(phoneSpecsListVo);
        }
        TreeVo treeVo = new TreeVo();
        List<TreeVo> treeVos = new ArrayList<>();
        treeVo.setV(phoneSpecsVos);
        treeVos.add(treeVo);

        //sku封装
        SkuVo skuVo = new SkuVo();
        skuVo.setTree(treeVos);
        skuVo.setList(phoneSpecsListVos);
        skuVo.setStock_num(phoneInfo.getPhoneStock());
        Integer phonePrice = phoneInfo.getPhonePrice().intValue();
        skuVo.setPrice(phonePrice+".00");

        //data封装
        SpecsDataVo specsDataVo = new SpecsDataVo();
        specsDataVo.setSku(skuVo);
        Map<String,String> goods = new HashMap<>();
        goods.put("picture",phoneInfo.getPhoneIcon());
        specsDataVo.setGoods(goods);
        return specsDataVo;
    }

    /**
     * 库存减少数量
     * @param specsId
     * @param quantity
     */
    @Override
    public void subStock(Integer specsId, Integer quantity) {
        PhoneSpecs phoneSpecs = phoneSpecsRepository.findById(specsId).get();
        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();
        Integer result = phoneSpecs.getSpecsStock() - quantity;
        if(result<0){
            log.error("【扣库存】库存不足");
            throw new PhoneException(ResultEnum.PHONE_STOCK_ERROR);
        }
        phoneSpecs.setSpecsStock(result);
        phoneSpecsRepository.save(phoneSpecs);
        result = phoneInfo.getPhoneStock() - quantity;
        if(result<0){
            log.error("【扣库存】库存不足");
            throw new PhoneException(ResultEnum.PHONE_STOCK_ERROR);
        }
        phoneInfo.setPhoneStock(result);
        phoneInfoRepository.save(phoneInfo);
    }
}
