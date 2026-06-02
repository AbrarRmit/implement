package com.assignment.model;

import java.time.LocalDate;
import java.time.Period;


public class Driver {
 private String driverID;
 private String name;
 private int experienceYears;
 private String licenseType; // Light, Medium, Heavy, PublicTransport
 private String address;
 private String birthdate;


public Driver(String driverID,String name,int experienceYears,String licenseType,String address,String birthdate){

    this.driverID = driverID;
    this.name = name;
    this.experienceYears = experienceYears;
    this.licenseType = licenseType;
    this.address = address;
    this.birthdate = birthdate;
}

public String getDriverID(){
    return driverID;
}
public String getName(){
    return name;
}
public int getExperienceYears(){
    return experienceYears;
}
public String getLicenseType(){
    return licenseType;
}
public String getAddress(){
    return address;
}
public String getBirthDate(){
    return birthdate;
}

public boolean validateDriverID(){
    if(driverID == null || driverID.length() != 10 ){
        return false;
    }
    int specialChar = 0;
    //check if any character is digit and if the starting 2 character are between 2 and 9
    for(int i = 0;i < 2;++i ){
        char c = driverID.charAt(i);
        if(!Character.isDigit(c)){
            return false;
        }else if(c >= '0' && c < '2'){
            return false;
            
        }
    }
    //count special number 
    for(int i = 2;i < 8;++i){
        char c = driverID.charAt(i);
        if(!Character.isLetterOrDigit(c)){
            //increment special character if character is not alphabet or number
            ++specialChar;
        }
    }
    //retun false if atleast aTLEAST 2 special character not used
    if(specialChar < 2){
        return false;
    }
    for(int i = 8;i < 10;++i){
        char c =  driverID.charAt(i);
        if(!Character.isUpperCase(c)){
            return false;
        }
    }
    return true;
}

public boolean validateDriverAddress(){
    if (address == null) {
            return false;
        }
        String[] parts = address.split("\\|");
        //since there are 5 part in address Street Number|Street Name|City|State|Country
        return parts.length == 5;
}
public boolean validateDriverBirthdate(){

    if (birthdate == null) {
            return false;
        }
        return birthdate.matches("\\d{2}-\\d{2}-\\d{4}");
}

public boolean validateDriverLicense(){
    String[] licenseCategory = {"Light", "Medium", "Heavy", "PublicTransport"};
    for(int i = 0;i < 4;++i){
        if(licenseType.equals(licenseCategory[i])){
            return true;
        }
    }
    return false;
}
public int getAge(){
    //Split birthdate into date month and year
    String[] split = birthdate.split("-");
    int  date = Integer.parseInt(split[0]);
    int month = Integer.parseInt(split[1]);
    int year = Integer.parseInt(split[2]);

    LocalDate birthDate = LocalDate.of(year, month, date);
    LocalDate currenDate = LocalDate.now();

    //Difference between currentdate and birthDate gives us the age of driver
    return Period.between(birthDate, currenDate).getYears();
}

@Override
public String toString() {
    //Address at the end because it already has |
    return driverID + " | " + name + " | " + experienceYears + " | " + licenseType + " | " + birthdate  + " | " + address;
           
}
}