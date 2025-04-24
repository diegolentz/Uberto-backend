package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.services.DriverService
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

    val testFactory = TestFactory()

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
        val trip = testFactory.createTripFinished(1).get(0)
        val passenger = trip.client
        val driver = trip.driver

        passengerRepository.save(passenger)
        driverRepository.save(driver)
        tripRepository.save(trip)

        val tripScore = TripScoreDTO(
             trip.id!!,
             "Viaje copado",
             3,
             "30/05/2025",
             "Pica",
             "Colorado",
             "",
             "",
             false
        )

        val objectMapper = ObjectMapper()
        val createInfoJson = objectMapper.writeValueAsString(tripScore)

        mockMvc.perform(MockMvcRequestBuilders.post("/tripScore")
            .contentType("application/json")
            .content(createInfoJson))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

}