package ar.edu.unsam.phm.uberto.services.auth

import jakarta.persistence.*

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

