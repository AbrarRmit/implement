package com.assignment.model;

public class Bus {
private String busID;
 private int capacity;
 private double fuelLevel;
 private String fuelType; // Diesel, Hybrid, Electricity

 public Bus(String busID,int capacity,double fuelLevel,String fuelType){
    this.busID = busID;
    this.capacity = capacity;
    this.fuelLevel = fuelLevel;
    this.fuelType = fuelType;
 }

 public String getBusID(){
    return busID;
 }
 public int getCapacity(){
    return capacity;
 }
 public double getFuelLevel(){
    return fuelLevel;
 }
 public String getFuelType(){
    return fuelType;
 }

 public boolean validateBusID(){
   if(busID == null || busID.length() != 8){
      return false;
   }
   for(int i = 0;i < 8;++i){
      char c = busID.charAt(i);
      if(!Character.isDigit(c)){
         return false;
      }
   }
   return true;
 }
 public boolean validateBusFuelType(){
   if(fuelType == null){
      return false;
   }
   if(fuelType.equals("Diesel") || fuelType.equals("Hybrid") || fuelType.equals("Electricity")){
      return true;
   }
   return false;
   }
}
