package com.freight.entity.TDT.TDTOrder;

import lombok.Data;


@Data
public class LinesList {

    private int consignmentId;
    private String currency;
    private String description;
    private int height;
    private int id;
    private String imageUrl;
    private int length;
    private String productCode;
    private int unit;
    private int value;
    private int volume;
    private int weight;
    private int width;

}