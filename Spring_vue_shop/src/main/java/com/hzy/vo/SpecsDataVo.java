package com.hzy.vo;

import lombok.Data;

import java.util.Map;

@Data
public class SpecsDataVo {
    private Map<String,String> goods;
    private SkuVo sku;
}
