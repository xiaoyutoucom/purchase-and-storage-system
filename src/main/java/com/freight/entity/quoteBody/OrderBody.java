package com.freight.entity.quoteBody;

import lombok.Data;
@Data
public class OrderBody {
    public String carrierName;
    public String customerId;
    private String accountId;
    private from from;
    private from to;
    private int numberPieces;
//    private List<items> items;
    private items items;
}
