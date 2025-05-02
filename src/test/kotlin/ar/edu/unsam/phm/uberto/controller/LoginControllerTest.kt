package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.LoginDTO
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.services.auth.AuthRepository
import ar.edu.unsam.phm.uberto.services.auth.Role
import ar.edu.unsam.phm.uberto.services.auth.UserAuthCredentials
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.AnnotationSpec
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Trips")
class LoginControllerTest {

    @Autowired
    lateinit var authRepo: AuthRepository

    @Autowired
    lateinit var driverRepo: DriverRepository

    @Autowired
    lateinit var passengerRepo: PassengerRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    val testFactory = TestFactory()

    private fun perform(mockMvcRequestBuilder: MockHttpServletRequestBuilder): ResultActions{
        return mockMvc.perform(mockMvcRequestBuilder)
    }

    private fun mockPost(uriPath:String): MockHttpServletRequestBuilder{
        return MockMvcRequestBuilders.post(uriPath)
    }

    private fun toJson(objectToParse:Any):String{
        return ObjectMapper().writeValueAsString(objectToParse)
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
    fun sucessfullLogin(){
        val loginRequest: LoginRequest = LoginRequest(username = "adrian", password = "adrian")
        val loginResponse: LoginDTO = LoginDTO(id=1, rol=Role.PASSENGER)
        this.perform( mockMvcRequestBuilder =
            this.mockPost("/login")
                .contentType("application/json")
                .content(this.toJson(objectToParse = loginRequest))
        ).andExpect {
                assertEquals(expected = 200, actual = it.response.status)
                assertEquals(expected = this.toJson(objectToParse = loginResponse), actual = it.response.contentAsString)
//                assertEquals(expected = "Request body is empty", actual = it.response.contentAsString)
            }

    }

//    @Test
//    fun loginWithInvalidUsername(){}
//
//    @Test
//    fun loginWithInvalidPassword(){}
//
//    @Test
//    fun validLogin(){}
//
//    @Test
//    fun validLoginForPassenger(){}
//
//    @Test
//    fun validLoginForDriver(){}
}