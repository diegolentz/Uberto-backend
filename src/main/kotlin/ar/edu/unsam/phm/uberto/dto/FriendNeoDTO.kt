package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.neo4j.PassNeo

data class FriendNeoDTO(
    val id: Long,
    val lastname: String,
    val firstname: String,
    val img: String
) {
}

fun PassNeo.toFriendNeoDTO() = FriendNeoDTO(
    id = passengerId!!,
    lastname = lastName,
    firstname = firstName,
    img = img
)