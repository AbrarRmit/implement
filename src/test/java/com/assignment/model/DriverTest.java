package com.assignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Driver validation rules D1, D2, D3
 */
class DriverTest {

   @Test
   void tcD101ValidDriverIdFormatShouldBeAccepted() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertTrue(driver.validateDriverID());
   }

   @Test
   void tcD102DriverIdStartingWithOneShouldBeRejected() {
      Driver driver = new Driver(
            "15@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertFalse(driver.validateDriverID());
   }

   @Test
   void tcD103DriverIdWithLowercaseFinalCharactersShouldBeRejected() {
      Driver driver = new Driver(
            "45@#abCDxy",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertFalse(driver.validateDriverID());
   }

   @Test
   void tcD201ValidPipeDelimitedAddressShouldBeAccepted() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertTrue(driver.validateDriverAddress());
   }

   @Test
   void tcD202AddressMissingCountryShouldBeRejected() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC",
            "01-01-1995");

      assertFalse(driver.validateDriverAddress());
   }

   @Test
   void tcD203AddressUsingCommaDelimiterShouldBeRejected() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12,King Street,Melbourne,VIC,Australia",
            "01-01-1995");

      assertFalse(driver.validateDriverAddress());
   }

   @Test
   void tcD301ValidBirthdateFormatShouldBeAccepted() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertTrue(driver.validateDriverBirthdate());
   }

   @Test
   void tcD302WrongBirthdateFormatShouldBeRejected() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "1995-01-01");

      assertFalse(driver.validateDriverBirthdate());
   }

   @Test
   void tcD303InvalidCalendarDateShouldBeRejected() {
      Driver driver = new Driver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "31-13-1995");

      assertFalse(driver.validateDriverBirthdate());
   }
}