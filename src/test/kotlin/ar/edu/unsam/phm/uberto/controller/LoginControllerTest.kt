package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.InvalidCredentialsException
import ar.edu.unsam.phm.uberto.dto.LoginDTO
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Role
import ar.edu.unsam.phm.uberto.repository.AuthRepository
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.core.spec.style.AnnotationSpec
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals
import kotlin.test.assertSame


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Trips")
class LoginControllerTest(
    @Autowired var authRepo: AuthRepository,
    @Autowired var authService: AuthService,
    @Autowired var passengerService: PassengerService,
    @Autowired var jwtUtil: TokenJwtUtil,
    @Autowired var driverRepo: DriverRepository,
    @Autowired var passengerRepo: PassengerRepository,
    @Autowired var mockMvc: MockMvc,
    @Autowired var driverService: DriverService,
) {

    val testFactory = TestFactory(authService, passengerService, driverService ,jwtUtil)
    val tokenDriver = testFactory.generateTokenDriverTest("simple")
    val tokenPassenger = testFactory.generateTokenPassengerTest("adrian")

    private fun perform(mockMvcRequestBuilder: MockHttpServletRequestBuilder): ResultActions{
        return mockMvc.perform(mockMvcRequestBuilder)
    }

    private fun mockPost(uriPath:String): MockHttpServletRequestBuilder{
        return MockMvcRequestBuilders.post(uriPath)
    }

    private fun toJson(objectToParse:Any):String{
        return ObjectMapper().writeValueAsString(objectToParse)
    }

    private fun validCredentials():Map<String, String>{
        return mapOf("username" to "adrian", "password" to "adrian")
    }

    private fun invalidCredentials():Map<String, String>{
        return mapOf("username" to "invalid_username", "password" to "invalid_password")
    }

    private fun parseToLoginResponse(json:String): LoginDTO{
        val mapper = ObjectMapper().registerKotlinModule()
        return mapper.readValue(json, LoginDTO::class.java)
    }

//    Me da duplicado cuando creo. Ya toma los datos del bootstrap
//    @BeforeEach
//    fun createAccount(){
//        authRepo.save(UserAuthCredentials().apply {
//            username = "adrian"
//            password = "adrian"
//            role = Role.PASSENGER
//        })
//    }

    //Si los elimino, hay problema de referencia con FK. Deberia aplicarse en el CASCADE en la Class
//    @AfterEach
//    fun clearAll(){
//        authRepo.deleteAll()
//        driverRepo.deleteAll()
//        passengerRepo.deleteAll()
//    }

    @Test
    fun loginWithoutRequestBody(){
        this.perform( mockMvcRequestBuilder =
            this.mockPost("/login")
        ).andExpect {
            assertEquals(expected = HttpStatus.BAD_REQUEST.value(), actual = it.response.status)
            assertEquals(expected = HttpMessageNotReadableException::class, actual = it.resolvedException!!::class)
        }
    }

    @Test
    fun loginWithInvalidUsername(){
        val loginRequest: LoginRequest = LoginRequest(
            username = this.invalidCredentials()["username"]!!,
            password = "" // No me interesa el password en este caso, ya que no pasa del username
        )
        this.perform( mockMvcRequestBuilder =
            this.mockPost("/login")
                .contentType("application/json")
                .content(this.toJson(objectToParse = loginRequest))
        ).andExpect {
            assertEquals(expected = HttpStatus.UNAUTHORIZED.value(), actual = it.response.status)
            assertSame(expected = InvalidCredentialsException()::class, actual = it.resolvedException!!::class)
        }
    }

    @Test
    fun loginWithInvalidPassword(){
        val loginRequest: LoginRequest = LoginRequest(
            username = this.validCredentials()["username"]!!,
            password = this.invalidCredentials()["password"]!!
        )
        this.perform( mockMvcRequestBuilder =
            this.mockPost("/login")
                .contentType("application/json")
                .content(this.toJson(objectToParse = loginRequest))
        ).andExpect {
            assertEquals(expected = HttpStatus.UNAUTHORIZED.value(), actual = it.response.status)
            assertSame(expected = InvalidCredentialsException()::class, actual = it.resolvedException!!::class)
        }
    }

    @Test
    fun sucessfullLoginDriver(){
        val loginRequest: LoginRequest = LoginRequest(username="premium", password="premium")
        val loginResponse: LoginDTO = LoginDTO(id=1, rol= Role.DRIVER, token = "token")
        this.perform( mockMvcRequestBuilder =
            this.mockPost("/login")
                .contentType("application/json")
                .content(this.toJson(objectToParse = loginRequest))
        ).andExpect {
            assertEquals(expected = HttpStatus.OK.value(), actual = it.response.status)
            assertEquals(expected = this.toJson(objectToParse = loginResponse), actual = it.response.contentAsString)

        }
    }
//
    @Test
    fun sucessfullLoginPassenger(){
        val loginRequest: LoginRequest = LoginRequest(
            username = this.validCredentials()["username"]!!,
            password = this.validCredentials()["password"]!!
        )
        val loginResponse: LoginDTO = LoginDTO(id=1, rol=Role.PASSENGER, token = "token")
        this.perform( mockMvcRequestBuilder =
            this.mockPost("/login")
                .contentType("application/json")
                .content(this.toJson(objectToParse = loginRequest))
        ).andExpect {
            assertEquals(expected = HttpStatus.OK.value(), actual = it.response.status)
            assertEquals(expected = this.toJson(objectToParse = loginResponse), actual = it.response.contentAsString)
        }
    }

}