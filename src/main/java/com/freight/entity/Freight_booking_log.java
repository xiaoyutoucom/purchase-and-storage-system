package com.freight.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2021-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Freight_booking_log对象", description="")
public class Freight_booking_log implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String order_ref;

    private String connote_number;

//    private float quote_m2h;
//
//    private float quote_tdt;
//
//    private float quote_gss;

    private float order_shipping_fee;

    private String order_shipping_by;

    private String order_postcode_from;

    private String order_region_from;

    private String order_postcode_to;

    private String order_region_to;

    private float order_cbm;
    private String order_company;
    private Integer is_success;
    private float order_weight;
    private String order_type;

    private float order_value;
    private String  order_distance;
    private Date order_time;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date create_time;
    @TableLogic
    private Integer is_deleted;


}
