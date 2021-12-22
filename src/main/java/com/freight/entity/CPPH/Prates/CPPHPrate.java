package com.freight.entity.CPPH.Prates;

import com.freight.entity.CPPH.Porder.StandardItems;

import java.util.List;

/**
 * Auto-generated: 2021-11-30 12:2:29
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CPPHPrate {

    private DeliveryAddress pickupAddress;
    private DeliveryAddress deliveryAddress;
    private boolean saturdayDelivery;
    private boolean txtNotifications;
    private List<StandardItems> standardItems;
    public void setPickupAddress(DeliveryAddress pickupAddress) {
         this.pickupAddress = pickupAddress;
     }
     public DeliveryAddress getPickupAddress() {
         return pickupAddress;
     }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
         this.deliveryAddress = deliveryAddress;
     }
     public DeliveryAddress getDeliveryAddress() {
         return deliveryAddress;
     }

    public void setSaturdayDelivery(boolean saturdayDelivery) {
         this.saturdayDelivery = saturdayDelivery;
     }
     public boolean getSaturdayDelivery() {
         return saturdayDelivery;
     }

    public void setTxtNotifications(boolean txtNotifications) {
         this.txtNotifications = txtNotifications;
     }
     public boolean getTxtNotifications() {
         return txtNotifications;
     }

    public void setStandardItems(List<StandardItems> standardItems) {
         this.standardItems = standardItems;
     }
     public List<StandardItems> getStandardItems() {
         return standardItems;
     }

}