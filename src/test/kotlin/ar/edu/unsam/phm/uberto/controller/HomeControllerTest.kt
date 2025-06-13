package ar.edu.unsam.phm.uberto.controller


import ar.edu.unsam.phm.uberto.dto.DriverCardAndTimeDTO
import ar.edu.unsam.phm.uberto.dto.DriverCardDTO
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.model.HomeSearch
import ar.edu.unsam.phm.uberto.repository.MongoDriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripScoreRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.HomeService
import ar.edu.unsam.phm.uberto.services.PassengerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Home")
class HomeControllerTest(
    @Autowired var mockMvc: MockMvc,
    @Autowired var passengerRepository: PassengerRepository,
    @Autowired var authService: AuthService,
    @Autowired var passengerService: PassengerService,
    @Autowired var jwtUtil: TokenJwtUtil,
    @Autowired var driverService: DriverService,
    @Autowired val homeService: HomeService
) {

    val testFactory = TestFactory(authService, passengerService, driverService, jwtUtil)
    val tokenDriver = testFactory.generateTokenDriverTest("simple")
    val tokenPassenger = testFactory.generateTokenPassengerTest("adrian")
    val invalidToken = testFactory.generateInvalidToken("simple")

    val mapper = ObjectMapper()

    @Test
    fun `No persisto nada en redis`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/home").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenPassenger")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Cuando consulto por una consulta persistida`() {

        val driverCardAndTimeDTO = DriverCardAndTimeDTO(
            0,
            listOf(
                DriverCardDTO(
                    "driverId",
                    "serial",
                    "name",
                    "brand",
                    1990,
                    0.0,
                    "img",
                    1.0,
                    "simple"
                )
            )
        )


        val home = HomeSearch(
            numberPassengers = 1,
            date = LocalDateTime.now(),
            origin = "origin",
            destination = "destination",
            driversPlusTime =  driverCardAndTimeDTO
        ).apply { passengerId = 1 }

        homeService.saveHome(home)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/home").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $tokenPassenger")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.numberPassengers")
                .value(1)
            )
    }
}

