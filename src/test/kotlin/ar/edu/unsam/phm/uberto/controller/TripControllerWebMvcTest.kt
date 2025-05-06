package ar.edu.unsam.phm.uberto.controller


import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.PremiumDriver
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import ar.edu.unsam.phm.uberto.services.TripService
import com.fasterxml.jackson.databind.ObjectMapper
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
import java.time.LocalDateTime

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

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var jwtUtil: TokenJwtUtil

    val testFactory = TestFactory(authService, passengerService, driverService ,jwtUtil)

    @Test
    fun `Pido los trip de un pasajero que no existe - no tiene pendientes`(){
        mockMvc.perform(MockMvcRequestBuilders.get("/trip/passenger/2313213"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
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

    @Test
    fun `Se crea un trip`(){
        val driver = testFactory.createDriverPremium(1).get(0)
        val passenger = testFactory.createPassenger(1).get(0).apply { balance = 100000000.0 }
        passengerRepository.save(passenger)
        driverRepository.save(driver)
        val jsonBody = """
                        {   
                            "id": 0,
                            "userId": ${passenger.id},
                            "driverId": ${driver.id},
                            "duration": 60,
                            "numberPassengers": 2,
                            "date": "${LocalDateTime.now().plusDays(1)}",
                            "origin": "origen",
                            "destination": "destino",
                            "price": 0,
                            "driverName": "string",
                            "passengerName": "string",
                            "imgPassenger": "string",
                            "imgDriver": "string",
                            "scored": true
                        }
                        """.trimIndent()


        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.post("/trip/create")
                .contentType("application/json")
                .content(jsonBody)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Se reserva de viaje exitosamente"))
    }

    @Test
    fun `Se falla creacion de trip, pasajero sin saldo`(){
        val driver = testFactory.createDriverPremium(1).get(0)
        val passenger = testFactory.createPassenger(1).get(0).apply { balance = 1.0 }
        passengerRepository.save(passenger)
        driverRepository.save(driver)
        val jsonBody = """
                        {   
                            "id": 0,
                            "userId": ${passenger.id},
                            "driverId": ${driver.id},
                            "duration": 60,
                            "numberPassengers": 2,
                            "date": "${LocalDateTime.now().plusDays(1)}",
                            "origin": "origen",
                            "destination": "destino",
                            "price": 110,
                            "driverName": "string",
                            "passengerName": "string",
                            "imgPassenger": "string",
                            "imgDriver": "string",
                            "scored": true
                        }
                        """.trimIndent()


        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.post("/trip/create")
                .contentType("application/json")
                .content(jsonBody)
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.content().string("Insufficient balance."))
    }


    @Test
    fun `Todos los viajes de un diver`(){

        val tripPending = testFactory.createTripPending(1).get(1)
        val tripFinished = testFactory.createTripFinished(1).get(1)

        driverRepository.save(tripPending.driver)
        passengerRepository.save(tripPending.client)

        tripFinished.driver = tripPending.driver
        tripFinished.client = tripPending.client

        tripRepository.saveAll(listOf(tripPending, tripFinished))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/trip/driver/${tripPending.driver.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2)) 
    }
}