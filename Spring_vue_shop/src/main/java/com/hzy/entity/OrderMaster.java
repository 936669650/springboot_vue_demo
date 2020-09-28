package com.hzy.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 */
@Data
@Entity
public class OrderMaster {

    @Id
    private String orderId;
    //买家名字
    private String buyerName;
    //买家电话
    private String buyerPhone;
    //买家地址
    private String buyerAddress;
    //商品编号
    private Integer phoneId;
    //商品名称
    private String phoneName;
    //商品数量
    private Integer phoneQuantity;
    //商品小图
    private String phoneIcon;
    //规格编号
    private Integer specsId;
    //规格名称
    private String specsName;
    //规格单价
    private BigDecimal specsPrice;
    //订单总金额
    private BigDecimal orderAmount;
    //支付状态 默认0--未支付
    private Integer payStatus;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
