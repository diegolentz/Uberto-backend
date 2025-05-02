package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.factory.TestFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class DriverJPATest {

    @Autowired
    lateinit var driverRepository: DriverRepository

    val factory = TestFactory()

    @Test
    fun `dado un id, obtengo el driver con sus trips`() {
        // Arrange
        val driver = factory.createDriverPremium(1).get(0)
        val trip = factory.createTripFinished(1).get(0)

        // Act
        trip.driver = driver
        driver.trips.add(trip)
        driverRepository.save(driver)

        val driverQuery = driverRepository.getByIdTrip(driver.id!!)

        // Assert
        assert(driverQuery.get().trips != null)
    }
}