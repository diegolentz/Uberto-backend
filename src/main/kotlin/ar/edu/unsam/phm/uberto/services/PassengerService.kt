package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.PassengerNotFoundException
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PassengerService(val passengerRepository: PassengerRepository) {
    fun getPassenger(passengerId: Long): Passenger {
        return passengerRepository.findById(passengerId).get() ?: throw PassengerNotFoundException()
    }

    fun getFriends(passengerId: Long): List<Passenger> {
        val friends: List<Passenger> = passengerRepository.findById(passengerId).get().friends.toList()
        return friends
    }


    @Transactional
    fun addBalance(passenger: Passenger, balance: Double): ResponseEntity<String> {
        passenger.loadBalance(balance)
        return ResponseEntity
            .status(HttpStatus.OK).body("Balance succesfully updated")
    }

    @Transactional
    fun updateInfo(passenger: Passenger, firstName: String?, lastName: String?, cellphone: Int?): ResponseEntity<String> {
        firstName?.let { passenger.firstName = it }
        lastName?.let { passenger.lastName = it }
        cellphone?.let { passenger.cellphone = it }
        return ResponseEntity
            .status(HttpStatus.OK).body("Profile succesfully updated")
    }

    @Transactional
    fun deleteFriend(passenger: Passenger, friend: Passenger): ResponseEntity<String> {
        passenger.removeFriend(friend)
        return ResponseEntity
            .status(HttpStatus.OK).body("Friend succesfully removed")
    }

    @Transactional
    fun addFriend(passenger: Passenger, friend: Passenger): ResponseEntity<String> {
        passenger.addFriend(friend)
        return ResponseEntity
            .status(HttpStatus.OK).body("You have a new friend!")
    }

    fun searchNonFriends(passengerId: Long, filter: String): List<Passenger> = passengerRepository.findPossibleFriends(passengerId, filter)

    private fun passengerMatch(passenger: Passenger, textFilter: String): Boolean {
        val ignoreCase:Boolean = true
        return passenger.firstName.contains(textFilter, ignoreCase) || passenger.lastName.contains(textFilter, ignoreCase)
    }

    fun getImg(passengerId: Long): String {
        val passenger = getPassenger(passengerId)
        return passenger.img
    }
}