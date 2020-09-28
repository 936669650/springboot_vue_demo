package com.hzy.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuVo {
    private List<TreeVo> tree;
    private List<PhoneSpecsListVo> list;
    private String price;
    private Integer stock_num;
    private Boolean none_sku = false;
    private Boolean hide_stock = false;
}
