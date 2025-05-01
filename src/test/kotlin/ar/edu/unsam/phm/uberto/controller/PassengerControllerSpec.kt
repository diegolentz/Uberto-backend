package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.dto.UpdatedPassengerDTO
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.services.PassengerService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jayway.jsonpath.JsonPath
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.InternalPlatformDsl.toStr
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de Passenger")
class PassengerControllerSpec {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var passengerRepository: PassengerRepository

    @Test
    fun `al buscar un usuario que no existe devuelve error`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/passenger/999999"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `al buscar un usuario lo devuelve con los atributos correctos`() {
        val passenger = PassengerBuilder().build()
        passengerRepository.save(passenger)

        mockMvc.perform(MockMvcRequestBuilders.get("/passenger/${passenger.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(passenger.id))
    }

    @Test
    fun `al buscar un usuario devuelvo todos los atributos del DTO`() {
        val passenger = PassengerBuilder().build()
        passengerRepository.save(passenger)

        val response = mockMvc.perform(MockMvcRequestBuilders.get("/passenger/${passenger.id}"))
            .andReturn()
            .response
        val json: Map<String, Any> = JsonPath.parse(response.contentAsString).read("\$")
        val expectedFields = setOf("id", "firstname", "lastname", "age", "money", "cellphone", "img")

        json.keys shouldContainExactly expectedFields
    }

    @Test
    fun `el dinero se agrega correctamente`() {
        val passenger = PassengerBuilder().build()
        val originalBalance = passenger.balance
        val money = 100.0
        passengerRepository.save(passenger)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/passenger/addBalance")
                .param("id", passenger.id.toString())
                .param("balance", money.toString())
        ).andExpect(MockMvcResultMatchers.status().isOk)

        val updatedBalance = passengerRepository.findById(passenger.id!!).get().balance

        updatedBalance shouldBe originalBalance + money
    }

    @Test
    fun `el dinero no puede ser negativo`() {
        val passenger = PassengerBuilder().build()
        val money = -1
        passengerRepository.save(passenger)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/passenger/addBalance")
                .param("id", passenger.id.toString())
                .param("balance", money.toString())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `se puede actualizar el perfil de un pasajero`() {
        val passenger = PassengerBuilder().build()
        val newInfo = UpdatedPassengerDTO("TEST", "TEST", 1)
        val mapper = ObjectMapper().registerKotlinModule()
        val json = mapper.writeValueAsString(newInfo)

        passengerRepository.save(passenger)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/passenger")
                .param("id", passenger.id.toString())
                .content(json)
                .contentType("application/json")
        ).andExpect(MockMvcResultMatchers.status().isOk)

        val updatedPassenger = passengerRepository.findById(passenger.id!!).get()

        updatedPassenger.firstName shouldBe newInfo.firstName
        updatedPassenger.lastName shouldBe newInfo.lastName
        updatedPassenger.cellphone shouldBe newInfo.phone
    }
}