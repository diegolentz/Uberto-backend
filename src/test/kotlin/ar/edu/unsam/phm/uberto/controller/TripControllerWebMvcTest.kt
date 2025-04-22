package ar.edu.unsam.phm.uberto.controller


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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Trips")
class TripControllerWebMvcTest {
    @MockitoBean
    lateinit var  tripService: TripService

    @MockitoBean
    lateinit var passengerService: PassengerService

    @MockitoBean
    lateinit var driverService: DriverService

    @Autowired
    lateinit var tripRepository: TripsRepository

    @Autowired
    lateinit var passengerRepository: PassengerRepository

    @Autowired
    lateinit var driverRepository: DriverRepository

    //@Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Pido los trip de un pasajero que no existe - no tiene pendientes`(){
        Mockito.`when`(tripService.getPendingTripPassenger(1)).thenReturn(emptyList())
        mockMvc.perform(MockMvcRequestBuilders.get("/trip/passenger/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun `Pido los trip de un pasajero que si tiene viajes`(){
        val passenger = Passenger().apply {
            firstName = "Mandarina"
            lastName = "Solution"}
        passengerRepository.save(passenger)

        val driver = PremiumDriver()
        driverRepository.save(driver)

        val trip = Trip().apply {
            client = passenger
            this.driver = driver
            numberPassengers = 2
            destination = "destino"
            origin = "origen"
        }
        tripRepository.save(trip)

        //Act

        Mockito.`when`(tripService.getPendingTripPassenger(1)).thenReturn(emptyList())
        mockMvc.perform(MockMvcRequestBuilders.get("/trip/passenger/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

}