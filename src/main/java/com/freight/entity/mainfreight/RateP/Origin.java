/**
  * Copyright 2021 bejson.com 
  */
package com.freight.entity.mainfreight.RateP;
/**
 * Auto-generated: 2021-10-26 8:16:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Origin {

    private String freightRequiredDateTime;
    private String freightRequiredDateTimeZone;
    private Address address;
    public void setFreightRequiredDateTime(String freightRequiredDateTime) {
         this.freightRequiredDateTime = freightRequiredDateTime;
     }
     public String getFreightRequiredDateTime() {
         return freightRequiredDateTime;
     }

    public void setFreightRequiredDateTimeZone(String freightRequiredDateTimeZone) {
         this.freightRequiredDateTimeZone = freightRequiredDateTimeZone;
     }
     public String getFreightRequiredDateTimeZone() {
         return freightRequiredDateTimeZone;
     }

    public void setAddress(Address address) {
         this.address = address;
     }
     public Address getAddress() {
         return address;
     }

}