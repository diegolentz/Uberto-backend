package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de TripsScore")
class TripScoreSpringTest {
    @Autowired
    private lateinit var passengerRepository: PassengerRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var driverService: DriverService

    @Autowired
    lateinit var driverRepository: DriverRepository

    @Autowired
    lateinit var tripRepository: TripsRepository

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var passengerService: PassengerService

    @Autowired
    lateinit var jwtUtil: TokenJwtUtil

    val testFactory = TestFactory(authService, passengerService, driverService ,jwtUtil)

    @Test
    fun `busco las calificaciones de un pasajero que no realizo ninguna calificacion`(){
        mockMvc.perform(MockMvcRequestBuilders.get("/tripScore/passenger/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
    }

    @Test
    fun `busco las calificaciones de un conductor que no tiene calificaciones`(){
        mockMvc.perform(MockMvcRequestBuilders.get("/tripScore/driver/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
    }

    @Test
    fun `Si creo una calificacion tanto el pasajero como el conductor podran ver la misma`() {
        val trip      = testFactory.createTripFinished(1).get(0)
        val passenger = trip.client
        val driver0    = trip.driver

        passenger.apply {
            firstName = "Pica"
            lastName = ""
        }
        driver0.apply {
            firstName = "Colorado"
            lastName  = ""
        }

        trip.apply {
            client = passenger
            driver = driver0
        }
        passengerRepository.save(passenger)
        driverRepository.save(driver0)
        tripRepository.save(trip)

        val tripScore = TripScoreDTO(
             trip.id!!,
             "Viaje copado",
             3,
             "30/05/2025",
             passenger.firstName,
             driver0.firstName,
             "",
             "",
             true
        )

        val objectMapper = ObjectMapper()
        val createInfoJson = objectMapper.writeValueAsString(tripScore)

        mockMvc.perform(MockMvcRequestBuilders.post("/tripScore")
            .contentType("application/json")
            .content(createInfoJson))
            .andExpect(MockMvcResultMatchers.status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.get("/tripScore/passenger/${passenger.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tripId").value(trip.id!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value(tripScore.message))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].scorePoints").value(tripScore.scorePoints))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].passengerName").value(tripScore.passengerName))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].driverName").value(tripScore.driverName))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].avatarUrlPassenger").value(tripScore.avatarUrlPassenger))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].avatarUrlDriver").value(tripScore.avatarUrlDriver))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].delete").value(tripScore.delete))

    }

}