package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.services.auth.Role

data class LoginRequest(
    var username: String,
    var password: String
)

data class LoginResponse(
    var jwt: Int
)
data class LoginDTO(
    var id: Int,
    var rol: Role
)

//fun User.toLoginDTO() = LoginDTO(
//    id = id,
//    rol = role
//)