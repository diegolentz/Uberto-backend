package ar.edu.unsam.phm.uberto.services.auth

import org.springframework.data.jpa.repository.JpaRepository

interface AuthRepository: JpaRepository<UserAuthCredentials, Int> {

    fun findByUsername(username:String):UserAuthCredentials?

    fun findByRole(role: Role): List<UserAuthCredentials>
}