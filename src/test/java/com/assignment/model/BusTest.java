package com.assignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Bus ID validation rule B1
 */
class BusTest {

   @Test
   void validEightDigitBusIdShouldBeAccepted() {
      Bus bus = new Bus("12345678", 60, 90.0, "Diesel");

      assertTrue(bus.validateBusID());
   }

   @Test
   void busIdContainingAlphabeticCharacterShouldBeRejected() {
      Bus bus = new Bus("1234A678", 60, 90.0, "Diesel");

      assertFalse(bus.validateBusID());
   }

   @Test
   void busIdWithInvalidLengthShouldBeRejected() {
      Bus bus = new Bus("1234567", 60, 90.0, "Diesel");

      assertFalse(bus.validateBusID());
   }
}