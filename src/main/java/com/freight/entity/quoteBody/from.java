package com.freight.entity.quoteBody;

import lombok.Data;

@Data
public class from {
    private String PostCode;
    private String Suburb;
    private String City;
    private double lat;
    private double lon;
}
