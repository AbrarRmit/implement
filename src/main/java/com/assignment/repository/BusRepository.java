package com.assignment.repository;

import java.io.*;
import java.util.ArrayList;
import com.assignment.model.Bus;
public class BusRepository {

    private static final String FILE_NAME = "buses.txt";

    // Add bus
    public boolean add(Bus bus) {

        //Reject duplicate bus IDs before writing to file.
        if (retrieveById(bus.getBusID()) != null) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                     
            writer.write(
                    bus.getBusID() + "|" +
                    bus.getCapacity() + "|" +
                    bus.getFuelLevel() + "|" +
                    bus.getFuelType()
            );

            writer.newLine();

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    // Retrieve all buses
    public ArrayList<Bus> retrieve() {

        ArrayList<Bus> buses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                Bus bus = new Bus(
                        data[0],
                        Integer.parseInt(data[1]),
                        Double.parseDouble(data[2]),
                        data[3]
                );

                buses.add(bus);
            }

        } catch (IOException e) {
            return buses;
        }

        return buses;
    }

    // Retrieve by ID
    public Bus retrieveById(String busID) {

        for (Bus bus : retrieve()) {

            if (bus.getBusID().equals(busID)) {
                return bus;
            }
        }

        return null;
    }

    //Update bus
    public boolean update(String busID, Bus updatedBus) {

        ArrayList<Bus> buses = retrieve();
        boolean updated = false;

        for (int i = 0; i < buses.size(); i++) {

            if (buses.get(i).getBusID().equals(busID)) {

                buses.set(i, updatedBus);
                updated = true;
                break;
            }
        }

        if (!updated) {
            return false;
        }

        try (BufferedWriter writer =  new BufferedWriter(new FileWriter(FILE_NAME))) {
        
            for (Bus bus : buses) {

                writer.write(
                        bus.getBusID() + "|" +
                        bus.getCapacity() + "|" +
                        bus.getFuelLevel() + "|" +
                        bus.getFuelType()
                );

                writer.newLine();
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    //Count buses
    public int count() {
        return retrieve().size();
    }
}