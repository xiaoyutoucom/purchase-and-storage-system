/**
  * Copyright 2021 bejson.com 
  */
package com.freight.entity.mainfreight.RateP;

import java.util.List;

/**
 * Auto-generated: 2021-10-26 8:16:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class mainfreightRatesP {

    private Account account;
    private ServiceLevel serviceLevel;
    private Origin origin;
    private Destination destination;
    private List<FreightDetails> freightDetails;
    public void setAccount(Account account) {
         this.account = account;
     }
     public Account getAccount() {
         return account;
     }

    public void setServiceLevel(ServiceLevel serviceLevel) {
         this.serviceLevel = serviceLevel;
     }
     public ServiceLevel getServiceLevel() {
         return serviceLevel;
     }

    public void setOrigin(Origin origin) {
         this.origin = origin;
     }
     public Origin getOrigin() {
         return origin;
     }

    public void setDestination(Destination destination) {
         this.destination = destination;
     }
     public Destination getDestination() {
         return destination;
     }

    public void setFreightDetails(List<FreightDetails> freightDetails) {
         this.freightDetails = freightDetails;
     }
     public List<FreightDetails> getFreightDetails() {
         return freightDetails;
     }

}