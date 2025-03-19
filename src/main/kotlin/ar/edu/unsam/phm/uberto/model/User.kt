package ar.edu.unsam.phm.uberto.model

interface User {
    var username:String
    var password:String
    var firstName:String
    var lastName:String
    var balance:Double
    val trips:MutableList<Trip>
    fun getScores():List<TravelScore>
}