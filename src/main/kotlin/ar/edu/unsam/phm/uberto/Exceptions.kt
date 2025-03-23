package ar.edu.unsam.phm.uberto

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class BusinessException(msg: String) : Exception(msg)

const val invalidCredentialsMessage = "Incorrect Username or Password"
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class InvalidCredentialsException(msg: String = invalidCredentialsMessage) : Exception(msg)
