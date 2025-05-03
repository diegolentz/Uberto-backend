package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.dto.UpdatedPassengerDTO
import ar.edu.unsam.phm.uberto.dto.toDTOFriend
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.services.PassengerService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jayway.jsonpath.JsonPath
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.mockk.InternalPlatformDsl.toStr
import jakarta.transaction.Transactional
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

    @Test
    fun `se traen los amigos que existen`() {
        val passenger = PassengerBuilder().build()
        val friend = PassengerBuilder().build()

        passengerRepository.save(friend)
        passenger.addFriend(friend)
        passengerRepository.save(passenger)

        mockMvc.perform(MockMvcRequestBuilders.get("/passenger/${passenger.id}/friends"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(friend.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value(friend.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value(friend.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].img").value(friend.img))
    }

    @Test
    fun `si no hay amigos es una lista vacia`() {
        val passenger = PassengerBuilder().build()
        passengerRepository.save(passenger)

        mockMvc.perform(MockMvcRequestBuilders.get("/passenger/${passenger.id}/friends"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty)
    }

    @Test
    @Transactional
    fun `agregar un amigo a un pasajero`() {
        val passenger = PassengerBuilder().build()
        val friend = PassengerBuilder().build()

        passengerRepository.save(friend)
        passengerRepository.save(passenger)

        val response = mockMvc.perform(
            MockMvcRequestBuilders.post("/passenger/friends")
                .param("passengerId", passenger.id.toString())
                .param("friendId", friend.id.toString())
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val updatedPassengerFriends = passengerRepository.findById(passenger.id!!).get().friends
        updatedPassengerFriends shouldContain friend
    }

    @Test
    @Transactional
    fun `eliminar un amigo a un pasajero`() {
        val passenger = PassengerBuilder().build()
        val friend = PassengerBuilder().build()

        passenger.addFriend(friend)
        friend.addFriend(passenger)
        passengerRepository.save(friend)
        passengerRepository.save(passenger)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/passenger/friends")
                .param("passengerId", passenger.id.toString())
                .param("friendId", friend.id.toString())
        ).andExpect(MockMvcResultMatchers.status().isOk)

        val updatedPassengerFriends = passengerRepository.findById(passenger.id!!).get().friends
        updatedPassengerFriends shouldNotContain friend
    }

    @Test
    fun `no se puede agregar un amigo que ya lo es`() {
        val passenger = PassengerBuilder().build()
        val friend = PassengerBuilder().build()

        passengerRepository.save(friend)
        passenger.addFriend(friend)
        passengerRepository.save(passenger)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/passenger/friends")
                .param("passengerId", passenger.id.toString())
                .param("friendId", friend.id.toString())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `el filtro busca correctamente`() {
        val passenger = PassengerBuilder().build()
        val wantedPassenger = PassengerBuilder().firstName("testotesto").build()

        passengerRepository.saveAll(listOf(passenger, wantedPassenger))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/passenger/${passenger.id}/friends/search")
                .param("filter", "testotesto")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(wantedPassenger.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value(wantedPassenger.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value(wantedPassenger.lastName))
    }

    @Test
    fun `si no hay resultados devuelve un array vacio`() {
        val passenger = PassengerBuilder().build()

        passengerRepository.save(passenger)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/passenger/${passenger.id}/friends/search")
                .param("filter", "asdadasdsdadsadasdsa")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty)
    }
}