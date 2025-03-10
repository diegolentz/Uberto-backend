package ar.edu.unsam.phm.uberto.model

interface  Person {
    var firstName: String
    var lastName: String
    var username: String
    var password: String
    // El tipo de las listas deberia ser del tipo 'Viajes' pero todavia no esta creado
    val performedTrips: MutableList<String>
    val pendingTrips: MutableList<String>
}