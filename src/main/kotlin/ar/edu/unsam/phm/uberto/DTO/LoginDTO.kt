package ar.edu.unsam.phm.uberto.DTO

data class LoginRequest(
    var username: String = "",
    var password: String = ""
)

data class LoginResponse(
    var jwt: Int
)
