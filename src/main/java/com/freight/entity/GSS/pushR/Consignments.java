package com.freight.entity.GSS.pushR;

import lombok.Data;

/**
 * Auto-generated: 2021-10-09 16:53:55
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Consignments {

    private String Connote;
    private String TrackingUrl;
    private float Cost;
    private int CarrierType;
    private boolean IsSaturdayDelivery;
    private boolean IsRural;
    private boolean IsOvernight;
    private boolean IsResidential;
    private boolean HasTrackPaks;
    private long ConsignmentId;
    private String Items;
    private OutputFiles OutputFiles;
    public void setConnote(String Connote) {
         this.Connote = Connote;
     }
     public String getConnote() {
         return Connote;
     }

    public void setTrackingUrl(String TrackingUrl) {
         this.TrackingUrl = TrackingUrl;
     }
     public String getTrackingUrl() {
         return TrackingUrl;
     }

    public void setCost(float Cost) {
         this.Cost = Cost;
     }
     public float getCost() {
         return Cost;
     }

    public void setCarrierType(int CarrierType) {
         this.CarrierType = CarrierType;
     }
     public int getCarrierType() {
         return CarrierType;
     }

    public void setIsSaturdayDelivery(boolean IsSaturdayDelivery) {
         this.IsSaturdayDelivery = IsSaturdayDelivery;
     }
     public boolean getIsSaturdayDelivery() {
         return IsSaturdayDelivery;
     }

    public void setIsRural(boolean IsRural) {
         this.IsRural = IsRural;
     }
     public boolean getIsRural() {
         return IsRural;
     }

    public void setIsOvernight(boolean IsOvernight) {
         this.IsOvernight = IsOvernight;
     }
     public boolean getIsOvernight() {
         return IsOvernight;
     }

    public void setIsResidential(boolean IsResidential) {
         this.IsResidential = IsResidential;
     }
     public boolean getIsResidential() {
         return IsResidential;
     }

    public void setHasTrackPaks(boolean HasTrackPaks) {
         this.HasTrackPaks = HasTrackPaks;
     }
     public boolean getHasTrackPaks() {
         return HasTrackPaks;
     }

    public void setConsignmentId(long ConsignmentId) {
         this.ConsignmentId = ConsignmentId;
     }
     public long getConsignmentId() {
         return ConsignmentId;
     }


    public void setItems(String Items) {
         this.Items = Items;
     }
     public String getItems() {
         return Items;
     }

}