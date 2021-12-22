package com.freight.entity.GSS.ratesR;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2021-10-09 13:33:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class GSSratesR {

    private List<Available> Available;
    private List<String> Rejected;
    private ValidationErrors ValidationErrors;
    public void setAvailable(List<Available> Available) {
         this.Available = Available;
     }
     public List<Available> getAvailable() {
         return Available;
     }

    public void setRejected(List<String> Rejected) {
         this.Rejected = Rejected;
     }
     public List<String> getRejected() {
         return Rejected;
     }

    public void setValidationErrors(ValidationErrors ValidationErrors) {
         this.ValidationErrors = ValidationErrors;
     }
     public ValidationErrors getValidationErrors() {
         return ValidationErrors;
     }

}