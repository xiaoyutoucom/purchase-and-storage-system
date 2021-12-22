package com.freight.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Freight_freight3placcount对象", description="")
public class Freight_freight3placcount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "仓库")
    private String warehouse;

    @ApiModelProperty(value = "公司介绍	    ")
    private String company;

    @ApiModelProperty(value = "客户ID")
    private String customerid;

    @TableField(fill = FieldFill.INSERT)
    private Date create_time;

    private Integer is_deleted;


}
