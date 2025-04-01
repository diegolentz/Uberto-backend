package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.NoFriendsFoundException
import ar.edu.unsam.phm.uberto.PassengerNotFoundException
import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import exceptions.BusinessException
import exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException.NotFound

@Service
class PassengerService(val passengerRepository: PassengerRepository) {

    fun getCurrentPassenger(passengerId: Int) = passengerRepository.searchByUserID(passengerId)

    fun getPassenger(passengerId: Int): Passenger {
        return passengerRepository.searchByUserID(passengerId) ?: throw PassengerNotFoundException()
    }

    fun getFriends(passengerId: Int): List<Passenger> {
        val friends: List<Passenger> = passengerRepository.getByID(passengerId).friends
        return friends
    }


    fun addBalance(passengerId: Int, balance: Double): String {
        val currentPassenger = getCurrentPassenger(passengerId)
        currentPassenger!!.loadBalance(balance)
        return "Balance succesfully updated"
    }

    fun updateInfo(passengerId: Int, updatedInfo: UpdatedPassengerDTO): String {
        val passenger = getCurrentPassenger(passengerId)
        updatedInfo.firstName?.let { passenger!!.firstName = it }
        updatedInfo.lastName?.let { passenger!!.lastName = it }
        updatedInfo.phone?.let { passenger!!.cellphone = it }
        passengerRepository.update(passenger!!)
        return "Profile succesfully updated"
    }

    fun deleteFriend(passengerId: Int, friendId: Int): String {
        val currentPassenger = getCurrentPassenger(passengerId)
        val friend = getCurrentPassenger(friendId)
        currentPassenger!!.removeFriend(friend!!)

        passengerRepository.update(currentPassenger)
        return "Friend succesfully removed"
    }

    fun addFriend(passengerId: Int, friendId: Int): ResponseEntity<String> {
        val currentPassenger = getCurrentPassenger(passengerId) ?: throw NotFoundException("Passenger not found")
        val friend = getCurrentPassenger(friendId) ?: throw NotFoundException("Passenger not found")
        currentPassenger.addFriend(friend)
        passengerRepository.update(currentPassenger)
        return ResponseEntity
            .status(HttpStatus.OK).body("You have a new friend!")
    }

    fun searchNonFriends(passengerId: Int, filter: String): List<Passenger> {
        val passengers:List<Passenger> = passengerRepository.instances.filter { it.id != passengerId }
        if(filter.isEmpty()) return passengers
        val passengerUser:Passenger = passengerRepository.getByID(passengerId)
        val nonFriendsPassenger:List<Passenger> = passengers.filter { passenger:Passenger->
            passengerMatch(passenger, filter) && !passengerUser.isFriendOf(passenger)
        }
        if(nonFriendsPassenger.isEmpty()) throw NoFriendsFoundException()
        return nonFriendsPassenger
    }

    private fun passengerMatch(passenger: Passenger, textFilter: String): Boolean {
        val ignoreCase:Boolean = true
        return passenger.firstName.contains(textFilter, ignoreCase) || passenger.lastName.contains(textFilter, ignoreCase)
    }

    fun getImg(passengerId: Int): String {
        val passenger = getCurrentPassenger(passengerId)
        return passenger!!.img
    }
}
