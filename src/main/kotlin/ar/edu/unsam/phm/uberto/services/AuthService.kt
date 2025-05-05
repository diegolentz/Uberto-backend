package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.InvalidCredentialsException
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.repository.AuthRepository
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.model.UserAuthCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authRepository: AuthRepository,
    val passwordEncoder: PasswordEncoder
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        if(username == null) throw InvalidCredentialsException("Usuario o Contraseña Incorrecta 0")
        val user = this.findUserByUsername(username=username)
            ?: throw InvalidCredentialsException("Usuario o Contraseña Incorrecta 1")
    return  user
    //return user.mapToUserDetails()
    }

//    private fun UserAuthCredentials.mapToUserDetails(): UserDetails {
//        return User.builder()
//            .username(this.username)
//            .password(this.password)
//            .roles(this.role.name)
//            .build()
//    }

    private fun findUserByUsername(username: String): UserAuthCredentials? {
        return authRepository.findByUsername(username)
    }

    fun validPassword(password: String, user: UserDetails){
        if(!passwordEncoder.matches(password, user.password)){
            throw InvalidCredentialsException("Usuario o contraseña incorrecta 2")
        }
    }
}