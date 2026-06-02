package com.assignment.helper;

import com.assignment.model.Bus;
import com.assignment.model.Driver;

public class BusAndDriver {

    //Reject bus driver older than 50 years old to drive bus with more than 49 people
    public boolean driverAgeRestriction(Driver d,Bus b){

        if(b.getCapacity() >= 50 && d.getAge() > 50 ){
            return false;
        }
        return true;
    }
    //Reject drivers with less than 5 years of experience from electric bus
    public boolean electricBusRestriction(Driver d,Bus b){
        if(b.getFuelType().equals("Electric") && d.getExperienceYears() < 5){
            return false;
        }
        return true;
    }
 
    public boolean driverLicenceRestriction(Driver d,Bus b){
        if((b.getFuelType().equals("Electric") || b.getFuelType().equals("Hybrid")) && (!d.getLicenseType().equals("Heavy") && !d.getLicenseType().equals("PublicTransport"))){
            return false;
        }
        return true;
    }
}

