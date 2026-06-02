package com.assignment.repository;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import com.assignment.model.Driver;
public class DriverRepository {

    // HashMap for fast lookup and duplicate prevention
    private HashMap<String, Driver> drivers;

    // TXT file name
    private final String FILE_NAME = "drivers.txt";

    public DriverRepository() {

        drivers = new HashMap<>();

        // Load existing drivers from TXT file
        loadDriversFromFile();
    }


    public boolean add(Driver driver) {

        //  Unique ID check
        if (drivers.containsKey(driver.getDriverID())) {

            return false;
        }

        drivers.put(driver.getDriverID(), driver);

        // save to text file
        saveDriversToFile();

        return true;
    }

    // Retrieve every drivers

    public Collection<Driver> retrieve() {

        return drivers.values();
    }

    // Retrieve with ID

    public Driver retrieveByID(String driverID) {

        return drivers.get(driverID);
    }

    // Updating driver details

    public boolean update(String driverID,Driver updatedDriver) {
    
        // Driver must exist
        if (!drivers.containsKey(driverID)) {
            return false;
        }

        Driver oldDriver = drivers.get(driverID);

        // driverID cannot change
        if (!oldDriver.getDriverID().equals(updatedDriver.getDriverID())) {
            return false;
        }

        // name cannot change
        if (!oldDriver.getName().equals(updatedDriver.getName())) {
            return false;
        }

        //  licence type restriction
        if (oldDriver.getExperienceYears() > 10 && oldDriver.getLicenseType().equals(updatedDriver.getLicenseType())) {
            return false;
        }

        // Replace old driver
        drivers.put(driverID, updatedDriver);

        // Save changes
        saveDriversToFile();

        return true;
    }
    
    // Count drivers
    
    public int count() {

        return drivers.size();
    }

    
    // Load drivers from text file
    private void loadDriversFromFile() {

        File file = new File(FILE_NAME);

        // File may not exist initially
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
                    
            String line;

            while ((line = reader.readLine()) != null) {
            // Limit 6 ensures address absorbs all internal |
            // Field order = driverID|name|experienceYears|licenseType|birthdate|address
                String[] data = line.split("\\|", 6);

                // Guard against malformed lines (#13)
                if (data.length < 6) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                Driver driver = new Driver(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        data[3],
                        data[4],
                        data[5]
                );

                drivers.put(driver.getDriverID(), driver);
            }

        } catch (IOException e) {

            System.out.println("Error reading driver file.");
        }
    }

    //Save all drivers

    private void saveDriversToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Driver driver : drivers.values()) {
                writer.write(driver.toString());
                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println("Error writing driver file.");
        }
    }
}