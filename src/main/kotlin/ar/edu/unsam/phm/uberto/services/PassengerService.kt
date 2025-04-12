package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.PassengerNotFoundException
import ar.edu.unsam.phm.uberto.dto.FriendDto
import ar.edu.unsam.phm.uberto.dto.toDTOFriend
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Transactional
class PassengerService(val passengerRepository: PassengerRepository) {
    @Transactional(readOnly = true)
    fun getPassenger(passengerId: Long): Passenger {
        return passengerRepository.findById(passengerId).get() ?: throw PassengerNotFoundException()
    }

    @Transactional(readOnly = true)
    fun getFriends(passengerId: Long): List<FriendDto> {
        return getPassenger(passengerId).friends.map { it.toDTOFriend() }
    }


    fun addBalance(passenger: Passenger, balance: Double): ResponseEntity<String> {
        passenger.loadBalance(balance)
        passengerRepository.save(passenger)
        return ResponseEntity
            .status(HttpStatus.OK).body("Balance succesfully updated")
    }

    fun updateInfo(
        passenger: Passenger,
        firstName: String?,
        lastName: String?,
        cellphone: Int?
    ): ResponseEntity<String> {
        firstName?.let { passenger.firstName = it }
        lastName?.let { passenger.lastName = it }
        cellphone?.let { passenger.cellphone = it }
        passengerRepository.save(passenger)
        return ResponseEntity
            .status(HttpStatus.OK).body("Profile succesfully updated")
    }

    fun deleteFriend(passengerId: Long, friendId: Long): ResponseEntity<String> {
        val currentPassenger = getPassenger(passengerId)
        val friend = getPassenger(friendId)
        currentPassenger.removeFriend(friend)
        friend.removeFriend(currentPassenger)
        passengerRepository.save(currentPassenger)
        passengerRepository.save(friend)
        return ResponseEntity
            .status(HttpStatus.OK).body("Friend succesfully removed")
    }

    fun addFriend(passengerId: Long, friendId: Long): ResponseEntity<String> {
        val currentPassenger = getPassenger(passengerId)
        val friend = getPassenger(friendId)
        currentPassenger.addFriend(friend)
        friend.addFriend(currentPassenger)
        passengerRepository.save(currentPassenger)
        passengerRepository.save(friend)
        return ResponseEntity
            .status(HttpStatus.OK).body("You have a new friend!")
    }

    @Transactional(readOnly = true)
    fun searchNonFriends(passengerId: Long, filter: String): List<Passenger> =
        passengerRepository.findPossibleFriends(passengerId, filter.lowercase())

    private fun passengerMatch(passenger: Passenger, textFilter: String): Boolean {
        val ignoreCase: Boolean = true
        return passenger.firstName.contains(textFilter, ignoreCase) || passenger.lastName.contains(
            textFilter,
            ignoreCase
        )
    }

    fun getImg(passengerId: Long): String {
        val passenger = getPassenger(passengerId)
        return passenger.img
    }
}