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

   @Test
   void driverUnderFiftyCanDriveLargeBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1985");
      Bus bus = new Bus("12345678", 60, 90.0, "Diesel");

      assertTrue(validator.driverAgeRestriction(driver, bus));
   }

   @Test
   void driverOverFiftyCannotDriveLargeBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            20,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1965");
      Bus bus = new Bus("12345678", 60, 90.0, "Diesel");

      assertFalse(validator.driverAgeRestriction(driver, bus));
   }

   @Test
   void driverOverFiftyCanDriveSmallBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            20,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1965");
      Bus bus = new Bus("12345678", 40, 90.0, "Diesel");

      assertTrue(validator.driverAgeRestriction(driver, bus));
   }

   @Test
   void driverWithFiveYearsExperienceCanDriveElectricBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            5,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertTrue(validator.electricBusRestriction(driver, bus));
   }

   @Test
   void driverWithLessThanFiveYearsExperienceCannotDriveElectricBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            4,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertFalse(validator.electricBusRestriction(driver, bus));
   }

   @Test
   void lowExperienceDriverCanDriveDieselBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            2,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Diesel");

      assertTrue(validator.electricBusRestriction(driver, bus));
   }

   @Test
   void heavyLicenceDriverCanDriveHybridBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Hybrid");

      assertTrue(validator.driverLicenceRestriction(driver, bus));
   }

   @Test
   void publicTransportLicenceDriverCanDriveElectricBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "PublicTransport",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertTrue(validator.driverLicenceRestriction(driver, bus));
   }

   @Test
   void mediumLicenceDriverCannotDriveElectricBus() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");
      Bus bus = new Bus("12345678", 40, 90.0, "Electricity");

      assertFalse(validator.driverLicenceRestriction(driver, bus));
   }
}