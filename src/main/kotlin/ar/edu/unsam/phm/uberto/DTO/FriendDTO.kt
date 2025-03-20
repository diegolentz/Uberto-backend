package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Passenger

data class FriendDTO(
    val firstName: String,
    val lastName: String,
    val cellphone: Int
)

fun Passenger.toFriendDTO(): FriendDTO {
    return FriendDTO(
        firstName = firstName,
        lastName = lastName,
        cellphone = cellphone
    )
}