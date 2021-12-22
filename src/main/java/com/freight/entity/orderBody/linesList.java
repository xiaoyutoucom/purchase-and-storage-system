package com.freight.entity.orderBody;

import lombok.Data;

@Data
public class linesList {
    public String productCode;
    public String description;
    public String unit;
    public String volume ;
    public String weight;
    public String length;
    public String width;
    public String height;
}
