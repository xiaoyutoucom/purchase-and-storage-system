package com.freight.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Freight_warehouse对象", description="")
public class Freight_warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String fliwayaccount;
    private String warehouse;

    private String code;

    private String shortcode;

    private String addressline;

    private String suburb;

    private String city;

    private String postcode;

    private String lon;

    private String lat;

    private String phonenumber;

    @TableField(fill = FieldFill.INSERT)
    private Date create_time;

    private Integer is_deleted;
    private String storeid;

}
