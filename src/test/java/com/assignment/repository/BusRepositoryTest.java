package com.assignment.repository;

import com.assignment.model.Bus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit and integration tests for BusRepository
 * These tests read the actual txt files
 */
class BusRepositoryTest {

   private static final Path BUS_FILE = Path.of("buses.txt");

   private String originalFileContent;

   @BeforeEach
   void clearBusFileBeforeTest() throws IOException {
      originalFileContent = Files.exists(BUS_FILE) ? Files.readString(BUS_FILE) : null;
      Files.writeString(BUS_FILE, "");
   }

   @AfterEach
   void restoreBusFileAfterTest() throws IOException {
      if (originalFileContent == null) {
         Files.deleteIfExists(BUS_FILE);
      } else {
         Files.writeString(BUS_FILE, originalFileContent);
      }
   }

   @Test
   void tcB201CapacityReductionShouldBeAllowed() {
      BusRepository repository = new BusRepository();
      Bus originalBus = new Bus("12345678", 60, 90.0, "Diesel");
      Bus updatedBus = new Bus("12345678", 50, 90.0, "Diesel");

      assertTrue(repository.add(originalBus));
      assertTrue(repository.update(originalBus.getBusID(), updatedBus));
      assertEquals(50, repository.retrieveById(originalBus.getBusID()).getCapacity());
   }

   @Test
   void tcB202CapacityIncreaseShouldBeRejected() {
      BusRepository repository = new BusRepository();
      Bus originalBus = new Bus("12345678", 50, 90.0, "Diesel");
      Bus updatedBus = new Bus("12345678", 60, 90.0, "Diesel");

      assertTrue(repository.add(originalBus));
      assertFalse(repository.update(originalBus.getBusID(), updatedBus));
   }

   @Test
   void tcB203UnchangedCapacityShouldBeAllowed() {
      BusRepository repository = new BusRepository();
      Bus originalBus = new Bus("12345678", 50, 90.0, "Diesel");
      Bus updatedBus = new Bus("12345678", 50, 80.0, "Diesel");

      assertTrue(repository.add(originalBus));
      assertTrue(repository.update(originalBus.getBusID(), updatedBus));
   }

   @Test
   void tcBint01ValidBusDataShouldBePersistedToTextStorage() {
      BusRepository repository = new BusRepository();
      Bus bus = new Bus("12345678", 60, 90.0, "Diesel");

      assertTrue(repository.add(bus));

      Bus savedBus = repository.retrieveById(bus.getBusID());

      assertNotNull(savedBus);
      assertEquals(bus.getBusID(), savedBus.getBusID());
   }

   @Test
   void tcBint02InvalidBusIdLengthShouldNotBePersisted() {
      BusRepository repository = new BusRepository();
      Bus invalidBus = new Bus("1234567", 60, 90.0, "Diesel");

      assertFalse(repository.add(invalidBus));
      assertEquals(0, repository.count());
   }

   @Test
   void tcBint03BusCapacityUpdateShouldPersistToTextStorage() {
      BusRepository repository = new BusRepository();
      Bus originalBus = new Bus("12345678", 60, 90.0, "Diesel");
      Bus updatedBus = new Bus("12345678", 50, 90.0, "Diesel");

      assertTrue(repository.add(originalBus));
      assertTrue(repository.update(originalBus.getBusID(), updatedBus));

      Bus savedBus = repository.retrieveById(originalBus.getBusID());

      assertNotNull(savedBus);
      assertEquals(50, savedBus.getCapacity());
   }

   @Test
   void tcBint04BusRegistryCountShouldIncreaseAfterAddingValidBus() {
      BusRepository repository = new BusRepository();
      Bus bus = new Bus("12345678", 60, 90.0, "Diesel");

      assertEquals(0, repository.count());
      assertTrue(repository.add(bus));
      assertEquals(1, repository.count());
   }
}