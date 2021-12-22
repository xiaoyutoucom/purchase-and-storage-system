package com.freight.entity.CPPH.Rrates;

import java.util.List;

/**
 * Auto-generated: 2021-11-30 12:4:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CPPHRrate {

    private String carrierName;
    private String customerId;
    private String serviceStandard;
    private float totalRateExcludingGst;
    private List<TotalRateBreakdown> totalRateBreakdown;
    private List<Options> options;
    public void setCarrierName(String carrierName) {
         this.carrierName = carrierName;
     }
     public String getCarrierName() {
         return carrierName;
     }

    public void setCustomerId(String customerId) {
         this.customerId = customerId;
     }
     public String getCustomerId() {
         return customerId;
     }

    public void setServiceStandard(String serviceStandard) {
         this.serviceStandard = serviceStandard;
     }
     public String getServiceStandard() {
         return serviceStandard;
     }

    public void setTotalRateExcludingGst(float totalRateExcludingGst) {
         this.totalRateExcludingGst = totalRateExcludingGst;
     }
     public float getTotalRateExcludingGst() {
         return totalRateExcludingGst;
     }

    public void setTotalRateBreakdown(List<TotalRateBreakdown> totalRateBreakdown) {
         this.totalRateBreakdown = totalRateBreakdown;
     }
     public List<TotalRateBreakdown> getTotalRateBreakdown() {
         return totalRateBreakdown;
     }

    public void setOptions(List<Options> options) {
         this.options = options;
     }
     public List<Options> getOptions() {
         return options;
     }

}