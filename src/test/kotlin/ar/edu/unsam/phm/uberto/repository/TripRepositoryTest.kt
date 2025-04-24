package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.PremiumDriver
import ar.edu.unsam.phm.uberto.model.Trip
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@DataJpaTest
class TripRepositoryTest {
    @Autowired
    lateinit var tripRepository: TripsRepository
    @Autowired
    lateinit var passengerRepository: PassengerRepository
    @Autowired
    lateinit var driverRepository: DriverRepository

    val testFactory = TestFactory()

    @Test
    fun `buscar un trip asociado a un passenger`(){
        //Arrange
        val passenger = testFactory.createPassenger(1).get(1)
        passengerRepository.save(passenger)

        val driver = testFactory.createDriverPremium(1).get(1)
        driverRepository.save(driver)

        val trip = testFactory.createTrip(passenger, driver)
        tripRepository.save(trip)

        //Act
        val client = tripRepository.findByClient_Id(passenger.id!!)

        //Assert
        assertFalse(client.isEmpty())
    }

    @Test
    fun `buscar un trip asociado a un formulario de driver`(){
        //Arrange
        val trip = testFactory.createTripFinished(1).get(0)
        trip.apply {
            numberPassengers = 2
            destination = "destino"
            origin = "origen"
        }
        val passenger = trip.client.apply {
            firstName = "Mandarina"
            }
        passengerRepository.save(passenger)

        val driver = trip.driver
        driverRepository.save(trip.driver)

        tripRepository.save(trip)

        //Act
        val foundTrip = tripRepository.searchByForm(
            origin = "origen",
            destination = "destino",
            numberPassengers = 2,
            name = "mandarina",
            driverId = driver.id!!)

        //Assert
        assertEquals("destino", foundTrip.get(0).destination, "Los destinos no son iguales")
    }

    @Test
    fun `buscar un trip asociado a un formulario de driver con nombres similares`(){
        //Arrange
        val listTrips = testFactory.createTripFinished(2)

        val driver = listTrips.get(0).driver
        driverRepository.save(driver)

        val passenger = listTrips.get(0).client.apply {
            firstName = "Mandarina"
            lastName = "Solution"}
        val passenger2 = listTrips.get(1).client.apply {
            firstName = "Mandarina"
            lastName = "Manzana"}
        passengerRepository.save(passenger)
        passengerRepository.save(passenger2)


        val trip = listTrips.get(0).apply {
            client = passenger
            numberPassengers = 2
            destination = "destino"
            origin = "origen"
        }
        val trip2 = listTrips.get(1).apply {
            client = passenger2
            this.driver = driver
            numberPassengers = 2
            destination = "destino"
            origin = "origen"
        }

        tripRepository.save(trip)
        tripRepository.save(trip2)

        //Act
        val foundTrip = tripRepository.searchByForm(
            origin = "origen",
            destination = "destino",
            numberPassengers = 2,
            name = "darina",
            driverId = driver.id!!)

        //Assert
        assertEquals(2, foundTrip.size, "Deberia tener 2 viajes")
    }

}