package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class DriverJPATest {

    @Autowired
    lateinit var driverRepository: DriverRepository

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var passengerService: PassengerService

    @Autowired
    lateinit var jwtUtil: TokenJwtUtil

    @Autowired
    lateinit var driverService: DriverService

    val factory = TestFactory(authService, passengerService, driverService ,jwtUtil)

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