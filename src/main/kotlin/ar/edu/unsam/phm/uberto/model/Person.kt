package ar.edu.unsam.phm.uberto.model

abstract class Person(
    var firstName: String,
    var lastName: String,
    var username: String,
    var password: String,
    var age: Int,
    var money: Double,
) {
    abstract var isDriver: Boolean
    // El tipo de las listas debería ser del tipo 'Travel' pero todavía no está creado
    val performedTrips: MutableList<Travel> = mutableListOf()
    val pendingTrips: MutableList<Travel> = mutableListOf()

}