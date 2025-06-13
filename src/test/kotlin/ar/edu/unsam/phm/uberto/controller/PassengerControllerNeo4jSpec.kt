package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.repository.AuthRepository
import ar.edu.unsam.phm.uberto.repository.MongoDriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.neo4j.harness.Neo4j
import org.neo4j.harness.Neo4jBuilders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableAutoConfiguration
class PassengerControllerNeo4jSpec(
//    @Autowired var authService: AuthService,
//    @Autowired var passengerService: PassengerService,
    @Autowired var jwtUtil: TokenJwtUtil,
    @Autowired var mockMvc: MockMvc,
//    @Autowired var driverService: DriverService,
) {
    companion object {
        var mapper = ObjectMapper()
        lateinit var embeddedDatabaseServer: Neo4j

        @BeforeAll
        @JvmStatic
        fun initializeNeo4j() {
            embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .build()
        }

        @AfterAll
        @JvmStatic
        fun stopNeo4j() {
            embeddedDatabaseServer.close()
        }

        @DynamicPropertySource
        @JvmStatic
        fun neo4jProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.neo4j.uri", embeddedDatabaseServer::boltURI)
            registry.add("spring.neo4j.authentication.username") { "neo4j" }
            registry.add("spring.neo4j.authentication.password") { null }
        }
    }

//    val testFactory = TestFactory(authService, passengerService, driverService, jwtUtil)
//    val tokenDriver = testFactory.generateTokenDriverTest("simple")
//    val tokenPassenger = testFactory.generateTokenPassengerTest("adrian")
//    val invalidToken = testFactory.generateInvalidToken("simple")

    @Test
    fun `la busqueda por titulo funciona correctamente, no importan mayusculas`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/passenger/friends")
                .header("Authorization", "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
//            .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Nueve reinas"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$[0].personajes.length()").value(0))
    }
}