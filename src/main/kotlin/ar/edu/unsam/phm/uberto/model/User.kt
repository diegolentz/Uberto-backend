package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.repository.AvaliableInstance

interface User : AvaliableInstance {
    var username:String
    var password:String
    var firstName:String
    var lastName:String
    var balance:Double
    val trips:MutableList<Trip>
    val img:String
    override var id:Int
    fun getScores():List<TripScore>
}