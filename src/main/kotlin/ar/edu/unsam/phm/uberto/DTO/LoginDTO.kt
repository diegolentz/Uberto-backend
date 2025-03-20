package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.User

data class LoginRequest(
    var username: String,
    var password: String
)

data class LoginResponse(
    var jwt: Int
)
data class LoginDTO(
    var id: Int,
    var img: String
)

fun User.toLoginDTO() = LoginDTO(
    id = id,
    img = img
)