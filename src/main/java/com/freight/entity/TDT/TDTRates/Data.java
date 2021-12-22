package com.freight.entity.TDT.TDTRates;

/**
 * Auto-generated: 2021-10-27 18:42:33
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@lombok.Data
public class Data {

    private String postcode;
    private String region;
    private String city;
    private String suburb;
    private int distanceFromAkl;
    private int distanceFromHam;
    private double distanceFromChc;
    private int commissionRateFromAkl;
    private int commissionRateFromHam;
    private double commissionRateFromChc;
    private float shippingCostFromAkl;
    private float shippingCostFromHam;
    private float shippingCostFromChc;

}