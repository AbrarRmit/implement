package com.assignment.helper;

import com.assignment.model.Bus;
import com.assignment.model.Driver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for bus and driver assignment rules B3, B4, B5
 */
class BusAndDriverTest {

   private final BusAndDriver validator = new BusAndDriver();

   private Driver createDriver(int experienceYears, String licenseType, String birthdate) {
      return new Driver(
            "45@#abCDXY",
            "John Smith",
            experienceYears,
            licenseType,
            "12|King Street|Melbourne|VIC|Australia",
            birthdate);
   }

   @Test
   void tcB301DriverUnderFiftyCanDriveLargeBus() {
      Driver driver = createDriver(8, "Heavy", "01-01-1985");
      Bus bus = new Bus("12345678", 60, 90.0, "Diesel");

      assertTrue(validator.driverAgeRestriction(driver, bus));
   }

   @Test
   void tcB302DriverOverFiftyCannotDriveLargeBus() {
      Driver driver = createDriver(20, "Heavy", "01-01-1965");
      Bus bus = new Bus("12345678", 60, 90.0, "Diesel");

      assertFalse(validator.driverAgeRestriction(driver, bus));
   }

   @Test
   void tcB303DriverOverFiftyCanDriveSmallBus() {
      Driver driver = createDriver(20, "Heavy", "01-01-1965");
      Bus bus = new Bus("12345678", 40, 90.0, "Diesel");

      assertTrue(validator.driverAgeRestriction(driver, bus));
   }

   @Test
   void tcB401DriverWithFiveYearsExperienceCanDriveElectricBus() {
      Driver driver = createDriver(5, "Heavy", "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertTrue(validator.electricBusRestriction(driver, bus));
   }

   @Test
   void tcB402DriverWithLessThanFiveYearsExperienceCannotDriveElectricBus() {
      Driver driver = createDriver(4, "Heavy", "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertFalse(validator.electricBusRestriction(driver, bus));
   }

   @Test
   void tcB403LowExperienceDriverCanDriveDieselBus() {
      Driver driver = createDriver(2, "Medium", "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Diesel");

      assertTrue(validator.electricBusRestriction(driver, bus));
   }

   @Test
   void tcB501HeavyLicenceDriverCanDriveHybridBus() {
      Driver driver = createDriver(8, "Heavy", "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Hybrid");

      assertTrue(validator.driverLicenceRestriction(driver, bus));
   }

   @Test
   void tcB502PublicTransportLicenceDriverCanDriveElectricBus() {
      Driver driver = createDriver(8, "PublicTransport", "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertTrue(validator.driverLicenceRestriction(driver, bus));
   }

   @Test
   void tcB503MediumLicenceDriverCannotDriveElectricBus() {
      Driver driver = createDriver(8, "Medium", "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertFalse(validator.driverLicenceRestriction(driver, bus));
   }
}