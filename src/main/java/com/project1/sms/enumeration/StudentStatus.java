package com.project1.sms.enumeration;

import lombok.Getter;

@Getter
public enum StudentStatus {
    ACTIVE("Active"),
    GRADUATED("Graduated"),
    SUSPENDED("Suspended"),
    UNENROLL( "Unenroll");


     private final String label;

     StudentStatus(String label){
         this.label = label;
     }


}
