package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.factory.TestFactory
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
class PassengerRepositorySpec {

    @Autowired
    lateinit var passengerRepository: PassengerRepository

    val factory = TestFactory()

    @Test
    fun `La busqueda de posibles amigos no trae personas que ya lo son`() {
        val passenger = PassengerBuilder().build()
        val friend = PassengerBuilder().build()
        val notFriend = PassengerBuilder().build()

        passengerRepository.saveAll(listOf(friend, notFriend))
        passenger.addFriend(friend)
        passengerRepository.save(passenger)

        passengerRepository.findPossibleFriends(passenger.id!!, "") shouldNotContain friend
    }

    @Test
    fun `La busqueda no es case-sensitive`() {
        val passenger = PassengerBuilder().build()
        val notFriend = PassengerBuilder().lastName("JUAN").build()

        passengerRepository.saveAll(listOf(notFriend, passenger))

        passengerRepository.findPossibleFriends(passenger.id!!, notFriend.lastName.lowercase()) shouldContain notFriend
    }
}
