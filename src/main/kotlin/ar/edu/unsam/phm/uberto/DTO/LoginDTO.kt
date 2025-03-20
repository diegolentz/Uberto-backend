package ar.edu.unsam.phm.uberto.dto

data class LoginRequest(
    var username: String,
    var password: String
)

data class LoginResponse(
    var jwt: Int
)

data class LoginDTO(
    var id: Int,
    var rol: String,
    var img: String
)