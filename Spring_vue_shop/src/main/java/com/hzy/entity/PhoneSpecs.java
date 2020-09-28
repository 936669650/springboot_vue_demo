package com.hzy.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品规格表
 */
@Entity
@Data
public class PhoneSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specsId;

    private Integer phoneId;
    //规格名称
    private String specsName;
    //库存
    private Integer specsStock;
    //单价
    private BigDecimal specsPrice;
    //小图
    private String specsIcon;
    //预览图
    private String specsPreview;

    private Date createTime;

    private Date updateTime;
}
