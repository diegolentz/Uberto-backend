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


    fun addBalance(passengerId: Int, balance: Double): BalanceDTO {
        val currentPassenger = getCurrentPassenger(passengerId)
        currentPassenger.loadBalance(balance)
        return currentPassenger.balanceDTO()
    }

    fun updateInfo(passengerId: Int, updatedInfo: UpdatedPassengerDTO): PassengerProfileDto {
        val passenger = getCurrentPassenger(passengerId)
        updatedInfo.firstname?.let { passenger.firstName = it }
        updatedInfo.lastname?.let { passenger.lastName = it }
        updatedInfo.cellphone?.let { passenger.cellphone = it }
        updatedInfo.img?.let { passenger.img = it }
        passengerRepository.update(passenger)
        return passenger.toDTOProfile()
    }

    fun deleteFriend(passengerId: Int, friendId: Int): List<FriendDto> {
        val currentPassenger = getCurrentPassenger(passengerId)
        val friend = getCurrentPassenger(friendId)
        currentPassenger.removeFriend(friend)

        passengerRepository.update(currentPassenger)
        return getFriends(passengerId)
    }

    fun addFriend(passengerId: Int, friendId: Int): List<FriendDto> {
        val currentPassenger = getCurrentPassenger(passengerId)
        val friend = getCurrentPassenger(friendId)
        currentPassenger.addFriend(friend)

        passengerRepository.update(currentPassenger)
        return getFriends(passengerId)
    }
}
