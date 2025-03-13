package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.repository.AvaliableInstance

abstract class Person(
    var firstName: String,
    var lastName: String,
    var username: String,
    var password: String,
    var age: Int,
    var money: Double,
): AvaliableInstance {
    abstract var isDriver: Boolean
    // El tipo de las listas debería ser del tipo 'Travel' pero todavía no está creado
    val performedTrips: MutableList<Travel> = mutableListOf()
    val pendingTrips: MutableList<Travel> = mutableListOf()

}