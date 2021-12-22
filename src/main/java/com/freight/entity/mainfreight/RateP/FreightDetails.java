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
public class FreightDetails {

    private String units;
    private String packTypeCode;
    private float weight;
    private float volume;
    public void setUnits(String units) {
         this.units = units;
     }
     public String getUnits() {
         return units;
     }

    public void setPackTypeCode(String packTypeCode) {
         this.packTypeCode = packTypeCode;
     }
     public String getPackTypeCode() {
         return packTypeCode;
     }

    public void setWeight(float weight) {
         this.weight = weight;
     }
     public float getWeight() {
         return weight;
     }

    public void setVolume(float volume) {
         this.volume = volume;
     }
     public float getVolume() {
         return volume;
     }

}