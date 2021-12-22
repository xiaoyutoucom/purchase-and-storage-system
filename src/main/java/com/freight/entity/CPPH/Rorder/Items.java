package com.freight.entity.CPPH.Rorder;

import java.util.List;

/**
 * Auto-generated: 2021-11-30 14:30:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Items {

    private String consignmentItemId;
    private String barcode;
    private String humanReadableBarcode;
    private String rateServiceCode;
    private List<TotalRateBreakdown> totalRateBreakdown;
    public void setConsignmentItemId(String consignmentItemId) {
         this.consignmentItemId = consignmentItemId;
     }
     public String getConsignmentItemId() {
         return consignmentItemId;
     }

    public void setBarcode(String barcode) {
         this.barcode = barcode;
     }
     public String getBarcode() {
         return barcode;
     }

    public void setHumanReadableBarcode(String humanReadableBarcode) {
         this.humanReadableBarcode = humanReadableBarcode;
     }
     public String getHumanReadableBarcode() {
         return humanReadableBarcode;
     }

    public void setRateServiceCode(String rateServiceCode) {
         this.rateServiceCode = rateServiceCode;
     }
     public String getRateServiceCode() {
         return rateServiceCode;
     }

    public void setTotalRateBreakdown(List<TotalRateBreakdown> totalRateBreakdown) {
         this.totalRateBreakdown = totalRateBreakdown;
     }
     public List<TotalRateBreakdown> getTotalRateBreakdown() {
         return totalRateBreakdown;
     }

}