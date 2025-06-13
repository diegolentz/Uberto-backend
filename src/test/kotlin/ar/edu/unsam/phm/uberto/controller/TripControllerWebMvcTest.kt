package ar.edu.unsam.phm.uberto.controller


import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.MongoDriverRepository
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Trips")
class TripControllerWebMvcTest(
    @Autowired var tripService: TripService,
    @Autowired var passengerService: PassengerService,
    @Autowired var driverService: DriverService,
    @Autowired var tripRepository: TripsRepository,
    @Autowired var passengerRepository: PassengerRepository,
    @Autowired var driverRepository: MongoDriverRepository,
    @Autowired var mockMvc: MockMvc,
    @Autowired var authService: AuthService,
    @Autowired var jwtUtil: TokenJwtUtil,
) {

    val testFactory = TestFactory(authService, passengerService, driverService, jwtUtil)
    val tokenDriver = testFactory.generateTokenDriverTest("simple")
    val tokenPassenger = testFactory.generateTokenPassengerTest("adrian")
    val invalidToken = testFactory.generateInvalidToken("simple")

    @Test
    fun `Pido los trip de un pasajero que no existe - no tiene pendientes`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/trip/passenger")
                .header("Authorization", "Bearer $invalidToken")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }


    @Test
    fun `Pido los trip de un pasajero que si tiene viajes`() {
        val user = authService.findUserByUsername("adrian")

        val passenger = passengerService.getById(user!!.id!!)
        val driver = driverRepository.findAll().first()
        val trip = Trip().apply {
            client = passenger
            this.driver = driver
            date = LocalDateTime.now().plusDays(1)
            numberPassengers = 3
            destination = "Av. 9 de Julio 500"
            origin = "Av. Juan de Garay 1200"
        }
        tripRepository.save(trip)
        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.get("/trip/profile/passenger")
                .header("Authorization", "Bearer $tokenPassenger")
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.pendingTrips[0].origin").value("Av. Juan de Garay 1200"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pendingTrips[0].destination").value("Av. 9 de Julio 500"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pendingTrips[0].numberPassengers").value(3))
    }

    @Test
    fun `Se crea un trip`() {
        val driver = testFactory.createDriverPremium(1).get(0)
        val passenger = testFactory.createPassenger(1).get(0).apply { balance = 100000000.0 }
        passengerRepository.save(passenger)
        driverRepository.save(driver)

        val tripMap = mapOf(
            "driverId" to driver.id.toString(),
            "duration" to 60,
            "numberPassengers" to 2,
            "date" to LocalDateTime.now().plusDays(1).toString(),
            "origin" to "origen",
            "destination" to "destino"
        )
        val jsonBody = ObjectMapper().writeValueAsString(tripMap)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/trip/create")
                .contentType("application/json")
                .content(jsonBody)
                .header("Authorization", "Bearer $tokenPassenger")
        )

            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Se reserva de viaje exitosamente"))
    }

    @Test
    fun `Se falla creacion de trip, pasajero sin saldo`() {
        val driver = testFactory.createDriverPremium(1).get(0)
        val passenger = passengerRepository.findById(1).get().apply { balance = 0.0 }
        passengerRepository.save(passenger)
        driverRepository.save(driver)
        println("Balance seteado en el test: ${passenger.balance}") // <-- ESTO ves en consola

        val tripMap = mapOf(
            "driverId" to driver.id.toString(),
            "duration" to 60,
            "numberPassengers" to 2,
            "date" to LocalDateTime.now().plusDays(1).toString(),
            "origin" to "origen",
            "destination" to "destino"
        )
        val jsonBody = ObjectMapper().writeValueAsString(tripMap)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/trip/create")
                .contentType("application/json")
                .content(jsonBody)
                .header("Authorization", "Bearer $tokenPassenger")
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }


    @Test
    fun `Todos los viajes de un diver`() {

        val tripPending = testFactory.createTripPending(1).get(1)
        val tripFinished = testFactory.createTripFinished(1).get(1)

        driverRepository.save(tripPending.driver)
        passengerRepository.save(tripPending.client)

        tripFinished.driver = tripPending.driver
        tripFinished.client = tripPending.client

        tripRepository.saveAll(listOf(tripPending, tripFinished))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/trip/profile/driver").header("Authorization", "Bearer $tokenDriver")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
    }
}