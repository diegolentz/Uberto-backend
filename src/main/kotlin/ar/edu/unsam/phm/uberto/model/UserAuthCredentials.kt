package ar.edu.unsam.phm.uberto.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

enum class Role{
    DRIVER,
    PASSENGER
}

@Entity
class UserAuthCredentials() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(length = 255)
    var username:String = ""

    @Column(length = 255)
    var password:String = ""

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var role: Role = Role.DRIVER
}