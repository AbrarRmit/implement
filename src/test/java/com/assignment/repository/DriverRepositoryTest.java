package com.assignment.repository;

import com.assignment.model.Driver;
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
 * Unit and integration tests for DriverRepository
 * These tests read the actual txt files
 */
class DriverRepositoryTest {

   private static final Path DRIVER_FILE = Path.of("drivers.txt");

   private String originalFileContent;

   @BeforeEach
   void clearDriverFileBeforeTest() throws IOException {
      originalFileContent = Files.exists(DRIVER_FILE) ? Files.readString(DRIVER_FILE) : null;
      Files.writeString(DRIVER_FILE, "");
   }

   @AfterEach
   void restoreDriverFileAfterTest() throws IOException {
      if (originalFileContent == null) {
         Files.deleteIfExists(DRIVER_FILE);
      } else {
         Files.writeString(DRIVER_FILE, originalFileContent);
      }
   }

   private Driver createDriver(String driverID, String name, int experienceYears,
         String licenseType, String address, String birthdate) {
      return new Driver(driverID, name, experienceYears, licenseType, address, birthdate);
   }

   private Driver createValidDriver(String driverID) {
      return createDriver(
            driverID,
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");
   }

   @Test
   void tcD401LicenceUpgradeWithLessThanTenYearsExperienceShouldBeAllowed() {
      DriverRepository repository = new DriverRepository();
      Driver originalDriver = createValidDriver("45@#abCDXY");
      Driver updatedDriver = createDriver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertTrue(repository.add(originalDriver));
      assertTrue(repository.update(originalDriver.getDriverID(), updatedDriver));
   }

   @Test
   void tcD402LicenceUpgradeWithMoreThanTenYearsExperienceShouldBeRejected() {
      DriverRepository repository = new DriverRepository();
      Driver originalDriver = createDriver(
            "45@#abCDXY",
            "John Smith",
            12,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1990");
      Driver updatedDriver = createDriver(
            "45@#abCDXY",
            "John Smith",
            12,
            "Heavy",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1990");

      assertTrue(repository.add(originalDriver));
      assertFalse(repository.update(originalDriver.getDriverID(), updatedDriver));
   }

   @Test
   void tcD403NonLicenceUpdateWithMoreThanTenYearsExperienceShouldBeAllowed() {
      DriverRepository repository = new DriverRepository();
      Driver originalDriver = createDriver(
            "45@#abCDXY",
            "John Smith",
            12,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1990");
      Driver updatedDriver = createDriver(
            "45@#abCDXY",
            "John Smith",
            12,
            "Medium",
            "50|Queen Street|Melbourne|VIC|Australia",
            "01-01-1990");

      assertTrue(repository.add(originalDriver));
      assertTrue(repository.update(originalDriver.getDriverID(), updatedDriver));
   }

   @Test
   void tcD501AttemptingToChangeDriverIdShouldBeRejected() {
      DriverRepository repository = new DriverRepository();
      Driver originalDriver = createValidDriver("45@#abCDXY");
      Driver updatedDriver = createDriver(
            "46@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertTrue(repository.add(originalDriver));
      assertFalse(repository.update(originalDriver.getDriverID(), updatedDriver));
   }

   @Test
   void tcD502AttemptingToChangeDriverNameShouldBeRejected() {
      DriverRepository repository = new DriverRepository();
      Driver originalDriver = createValidDriver("45@#abCDXY");
      Driver updatedDriver = createDriver(
            "45@#abCDXY",
            "Jane Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertTrue(repository.add(originalDriver));
      assertFalse(repository.update(originalDriver.getDriverID(), updatedDriver));
   }

   @Test
   void tcD503UpdatingMutableDriverFieldsShouldBeAllowed() {
      DriverRepository repository = new DriverRepository();
      Driver originalDriver = createValidDriver("45@#abCDXY");
      Driver updatedDriver = createDriver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "50|Queen Street|Melbourne|VIC|Australia",
            "02-02-1995");

      assertTrue(repository.add(originalDriver));
      assertTrue(repository.update(originalDriver.getDriverID(), updatedDriver));
   }

   @Test
   void tcDint01ValidDriverDataShouldBePersistedToTextStorage() {
      DriverRepository repository = new DriverRepository();
      Driver driver = createValidDriver("45@#abCDXY");

      assertTrue(repository.add(driver));

      DriverRepository reloadedRepository = new DriverRepository();
      Driver savedDriver = reloadedRepository.retrieveByID(driver.getDriverID());

      assertNotNull(savedDriver);
      assertEquals(driver.getDriverID(), savedDriver.getDriverID());
   }

   @Test
   void tcDint02InvalidDriverIdShouldNotBePersisted() {
      DriverRepository repository = new DriverRepository();
      Driver invalidDriver = createDriver(
            "15@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertFalse(repository.add(invalidDriver));
      assertEquals(0, repository.count());
   }

   @Test
   void tcDint03DriverUpdatesShouldPersistAcrossRepositoryReloads() {
      DriverRepository repository = new DriverRepository();
      Driver originalDriver = createValidDriver("45@#abCDXY");
      Driver updatedDriver = createDriver(
            "45@#abCDXY",
            "John Smith",
            8,
            "Medium",
            "50|Queen Street|Melbourne|VIC|Australia",
            "01-01-1995");

      assertTrue(repository.add(originalDriver));
      assertTrue(repository.update(originalDriver.getDriverID(), updatedDriver));

      DriverRepository reloadedRepository = new DriverRepository();
      Driver savedDriver = reloadedRepository.retrieveByID(originalDriver.getDriverID());

      assertNotNull(savedDriver);
      assertEquals("50|Queen Street|Melbourne|VIC|Australia", savedDriver.getAddress());
   }

   @Test
   void tcDint04DriverRegistryCountShouldIncreaseAfterAddingValidDriver() {
      DriverRepository repository = new DriverRepository();
      Driver driver = createValidDriver("45@#abCDXY");

      assertEquals(0, repository.count());
      assertTrue(repository.add(driver));
      assertEquals(1, repository.count());
   }
}