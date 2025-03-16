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
    val performedTravels: MutableList<Travel> = mutableListOf()
    val pendingTravels: MutableList<Travel> = mutableListOf()
    val ratings: MutableList<Double> = mutableListOf()

    fun addPerformedTravel(travel: Travel) {performedTravels.add(travel)}

    fun addPendingTravel(travel: Travel) {pendingTravels.add(travel)}

    fun removePendingTravel(travel: Travel) {pendingTravels.remove(travel)}
}