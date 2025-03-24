package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import org.springframework.stereotype.Service

@Service
class PassengerService(val passengerRepository: PassengerRepository) {

    fun getCurrentPassenger(passengerId: Int) = passengerRepository.getByID(passengerId)

    fun getPassenger(passengerId: Int): PassengerProfileDto {
        return getCurrentPassenger(passengerId).toDTOProfile()
    }

    fun getFriends(passengerId: Int): List<FriendDto> {
        val friends: List<Passenger> = passengerRepository.getByID(passengerId).friends
        return friends.map { it.toDTOFriend() }
    }


    fun addBalance(passengerId: Int, balance: Double): String {
        val currentPassenger = getCurrentPassenger(passengerId)
        currentPassenger.loadBalance(balance)
        return "Balance succesfully updated"
    }

    fun updateInfo(passengerId: Int, updatedInfo: UpdatedPassengerDTO): String {
        val passenger = getCurrentPassenger(passengerId)
        updatedInfo.firstname?.let { passenger.firstName = it }
        updatedInfo.lastname?.let { passenger.lastName = it }
        updatedInfo.cellphone?.let { passenger.cellphone = it }
        updatedInfo.img?.let { passenger.img = it }
        passengerRepository.update(passenger)
        return "Profile succesfully updated"
    }

    fun deleteFriend(passengerId: Int, friendId: Int): String {
        val currentPassenger = getCurrentPassenger(passengerId)
        val friend = getCurrentPassenger(friendId)
        currentPassenger.removeFriend(friend)

        passengerRepository.update(currentPassenger)
        return "Friend succesfully removed"
    }

    fun addFriend(passengerId: Int, friendId: Int): String {
        val currentPassenger = getCurrentPassenger(passengerId)
        val friend = getCurrentPassenger(friendId)
        currentPassenger.addFriend(friend)

        passengerRepository.update(currentPassenger)
        return "You have a new friend!"
    }

    fun searchFriends(passengerId: Int, filter: String): List<FriendDto> {
        val passengers = passengerRepository.instances.filter { it.id != passengerId }
        return passengers.filter { it.firstName.contains(filter, true) || it.lastName.contains(filter, true) }
            .map { it.toDTOFriend() }
    }
}
