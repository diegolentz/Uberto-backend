package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.dto.LoginResponse
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.Repository
import exceptions.NotFoundException
import exceptions.loginErrorMessageMock
import org.springframework.stereotype.Service

@Service
object UserService {
    val passengerRepo: Repository<Passenger> = Repository()
    val tripRepo: Repository<Trip> = Repository()
    val driverRepo: Repository<Driver> = Repository()

    fun getAllUsers(): List<Passenger> {
        return passengerRepo.instances.toMutableList()
    }

    fun getProfile(userId: Int): PassengerProfileDto = passengerRepo.getByID(userId).toDTOProfile()

    fun getAllDrivers(): List<Driver> {
        return driverRepo.instances.toMutableList()
    }

    fun getFriends(userId: Int): List<FriendDTO> {
        val userFriends = passengerRepo.getByID(userId).friends
        return userFriends.map { it.toFriendDTO() }
    }

    fun updateFriends(friendsList: UpdatedFriends): List<FriendDTO> {
        val friendsIds = friendsList.friends.map { friendId -> passengerRepo.getByID(friendId) }
        val user = passengerRepo.getByID(friendsList.userId)

        if (friendsList.addFriends) friendsIds.map { friendId -> user.addFriend(friendId) }
        else friendsIds.map { friendId -> user.removeFriend(friendId) }

        return getFriends(user.id)
    }



}