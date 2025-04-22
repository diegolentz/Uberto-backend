package ar.edu.unsam.phm.uberto.controller


import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.PremiumDriver
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import ar.edu.unsam.phm.uberto.services.TripService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Trips")
class TripControllerWebMvcTest {
    @Autowired
    lateinit var  tripService: TripService

    @Autowired
    lateinit var passengerService: PassengerService

    @Autowired
    lateinit var driverService: DriverService

    @Autowired
    lateinit var tripRepository: TripsRepository

    @Autowired
    lateinit var passengerRepository: PassengerRepository

    @Autowired
    lateinit var driverRepository: DriverRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    val testFactory = TestFactory()

    @Test
    fun `Pido los trip de un pasajero que no existe - no tiene pendientes`(){
        mockMvc.perform(MockMvcRequestBuilders.get("/trip/passenger/2313213"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun `Pido los trip de un pasajero que si tiene viajes`(){
        val trip = testFactory.createTripFinished(1).get(0)

        val passenger = trip.client.apply {
            firstName = "Mandarina"
            lastName = "Solution"}
        passengerRepository.save(passenger)

        val driver = trip.driver
        driverRepository.save(trip.driver)

        driverRepository.save(driver)
        driverRepository.save(driver)
        tripRepository.save(trip)

        trip.apply {
            client = passenger
            this.driver = driver
            numberPassengers = 2
            destination = "destino"
            origin = "origen"
        }
        tripRepository.save(trip)


        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/trip/passenger/${passenger.id}"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].origin").value("origen"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination").value("destino"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberPassengers").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].passengerName").value("Mandarina Solution"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].driverName").value("Driver Premium 0 Test Premium 0"))
    }

}