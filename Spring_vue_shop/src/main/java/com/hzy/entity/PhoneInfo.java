package com.hzy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表
 */
@Entity
@Data
public class PhoneInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phoneId;
    //商品名称
    private String phoneName;
    //商品单价
    private BigDecimal phonePrice;
    //描述
    private String phoneDescription;
    //库存
    private Integer phoneStock;
    //小图
    private String phoneIcon;
    //类目编号
    private Integer categoryType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //标签
    private String phoneTag;
}
