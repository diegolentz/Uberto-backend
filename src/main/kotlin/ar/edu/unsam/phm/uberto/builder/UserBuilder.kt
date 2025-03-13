package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.User


class UserBuilder(val newUser: User = User()) {

    fun firstName(name: String): UserBuilder = apply {
        newUser.firstName = name
    }

    fun lastName(name: String): UserBuilder = apply {
        newUser.lastName = name
    }

    fun age(age: Int): UserBuilder = apply {
        newUser.age = age
    }

    fun balance(moneyBalance: Double): UserBuilder = apply {
        newUser.money = moneyBalance
    }

    fun build(): User = newUser

}