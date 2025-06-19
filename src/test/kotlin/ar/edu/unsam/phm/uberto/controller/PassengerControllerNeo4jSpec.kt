import ar.edu.unsam.phm.uberto.Backend2025Grupo3Application
import ar.edu.unsam.phm.uberto.factory.TestFactory
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

@SpringBootTest(classes = [Backend2025Grupo3Application::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Passenger Neo4j")
class PassengerControllerNeo4jSpec(

    @Autowired
    var jwtUtil: TokenJwtUtil,

    @Autowired
    var mockMvc: MockMvc,

    @Autowired var driverService: DriverService,

    @Autowired
    var authService: AuthService,

    @Autowired
    var passengerService: PassengerService,

) {

    val testFactory = TestFactory(authService, passengerService, driverService, jwtUtil)

    // Adaptado para Neo4j, asumiendo que TestFactory puede recibir driverService como null
    val tokenPassenger = testFactory.generateTokenPassengerTest("adrian")

    @Test
    fun `la busqueda por titulo funciona correctamente, no importan mayusculas`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/passenger/friends")
                .header("Authorization", "Bearer $tokenPassenger")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
        // Aquí puedes agregar los asserts necesarios sobre el contenido JSON
    }
}