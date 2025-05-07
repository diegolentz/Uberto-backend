package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
class PassengerRepositorySpec {

    @Autowired
    lateinit var passengerRepository: PassengerRepository

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var passengerService: PassengerService

    @Autowired
    lateinit var jwtUtil: TokenJwtUtil

    @Autowired
    lateinit var driverService: DriverService

    val factory = TestFactory(authService, passengerService, driverService ,jwtUtil)
    val passenger = PassengerBuilder().build()
    val notFriend = PassengerBuilder().lastName("JUAN").build()


    @Test
    fun `La busqueda de posibles amigos no trae personas que ya lo son`() {
        val friend = PassengerBuilder().build()

        passengerRepository.saveAll(listOf(friend, notFriend))
        passenger.addFriend(friend)
        passengerRepository.save(passenger)

        passengerRepository.findPossibleFriends(passenger.id!!, "") shouldNotContain friend
    }

    @Test
    fun `Me trae una lista de no-amigos`() {
        val notFriends = factory.createPassenger(5)

        passengerRepository.saveAll(notFriends)
        passengerRepository.save(passenger)

        passengerRepository.findPossibleFriends(passenger.id!!, "") shouldBe notFriends
    }

    @Test
    fun `La busqueda no es case-sensitive`() {
        passengerRepository.saveAll(listOf(notFriend, passenger))

        passengerRepository.findPossibleFriends(passenger.id!!, notFriend.lastName.lowercase()) shouldContain notFriend
    }
}
