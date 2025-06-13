package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.repository.MongoDriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
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
class TripScoreSpringTest(
    @Autowired var passengerRepository: PassengerRepository,
    @Autowired var mockMvc: MockMvc,
    @Autowired var driverService: DriverService,
    @Autowired var driverRepository: MongoDriverRepository,
    @Autowired var tripRepository: TripsRepository,
    @Autowired var authService: AuthService,
    @Autowired var passengerService: PassengerService,
    @Autowired var jwtUtil: TokenJwtUtil
) {

    val testFactory = TestFactory(authService, passengerService, driverService, jwtUtil)
    val tokenDriver = testFactory.generateTokenDriverTest("simple")
    val tokenPassenger = testFactory.generateTokenPassengerTest("adrian")

    @Test
    fun `busco las calificaciones de un pasajero que no realizo ninguna calificacion`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/tripScore/passenger")
                .header("Authorization", "Bearer $tokenPassenger")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
    }

    @Test
    fun `busco las calificaciones de un conductor que no tiene calificaciones`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/tripScore/driver")
                .header("Authorization", "Bearer $tokenDriver")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4))
    }

}