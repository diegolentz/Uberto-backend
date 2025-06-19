import ar.edu.unsam.phm.uberto.Backend2025Grupo3Application
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(classes = [Backend2025Grupo3Application::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Passenger Neo4j")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PassengerControllerNeo4jSpec(
    @Autowired var jwtUtil: TokenJwtUtil,
    @Autowired var mockMvc: MockMvc,
    @Autowired var driverService: DriverService,
    @Autowired var authService: AuthService,
    @Autowired var passengerService: PassengerService,
) {
    val testFactory = TestFactory(authService, passengerService, driverService, jwtUtil)
    val tokenPassenger = testFactory.generateTokenPassengerTest("adrian")

    @Test
    @Order(1)
    fun `se buscan amigos de adrian`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/passenger/friends")
                .header("Authorization", "Bearer $tokenPassenger")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[*].firstname", Matchers.hasItem("Diego")))
    }

    @Test
    @Order(2)
    fun `se elimina un amigo a adrian`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/passenger/friends")
                .header("Authorization", "Bearer $tokenPassenger").param("friendId", "5")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `se agrega un amigo a adrian`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/passenger/friends")
                .header("Authorization", "Bearer $tokenPassenger").param("friendId", "2")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect { MockMvcResultMatchers.jsonPath("$[0].firstname").value("Diego") }
    }
}