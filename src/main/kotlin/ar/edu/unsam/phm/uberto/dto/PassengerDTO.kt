package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Passenger

data class PassengerProfileDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val money: Double,
    val cellphone: Int
)

fun Passenger.toDTOProfile() = PassengerProfileDto(
    id = userId,
    firstName = firstName,
    lastName = lastName,
    age = age,
    money = balance,
    cellphone = cellphone
)

data class UpdatedFriends(
    val friends: List<Int>,
    val addFriends: Boolean
)

data class BalanceDTO(
    val currentBalance: Double
)

fun Passenger.balanceDTO() = BalanceDTO(
    currentBalance = balance
)

data class UpdatedPassengerDTO(
    val firstName: String?,
    val lastName: String?,
    val cellphone: Int?
)