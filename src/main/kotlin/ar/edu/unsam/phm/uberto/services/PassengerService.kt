package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.PassengerNotFoundException
import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import org.springframework.stereotype.Service

@Service
class PassengerService(val passengerRepository: PassengerRepository) {

    fun getCurrentPassenger(passengerId: Int) = passengerRepository.searchByUserID(passengerId)

    fun getPassenger(passengerId: Int): Passenger {
        return passengerRepository.searchByUserID(passengerId) ?: throw PassengerNotFoundException()
    }

    fun getFriends(passengerId: Int): List<FriendDto> {
        val friends: List<Passenger> = passengerRepository.getByID(passengerId).friends
        return friends.map { it.toDTOFriend() }
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

    fun addFriend(passengerId: Int, friendId: Int): String {
        val currentPassenger = getCurrentPassenger(passengerId)
        val friend = getCurrentPassenger(friendId)
        currentPassenger!!.addFriend(friend!!)

        passengerRepository.update(currentPassenger)
        return "You have a new friend!"
    }

    fun searchFriends(passengerId: Int, filter: String): List<FriendDto> {
        val passengers = passengerRepository.instances.filter { it.id != passengerId }
        return passengers.filter {
            (it.firstName.contains(filter, true) || it.lastName.contains(
                filter,
                true
            )) && !isAlreadyfriend(passengerId, it.id)
        }
            .map { it.toDTOFriend() }
    }

    private fun isAlreadyfriend(passengerId: Int, friendId: Int): Boolean {
        val friendsIds = getCurrentPassenger(passengerId)!!.friends.map { it.id }
        return friendsIds.contains(friendId)
    }

    fun getImg(passengerId: Int): String {
        val passenger = getCurrentPassenger(passengerId)
        return passenger!!.img
    }
}
