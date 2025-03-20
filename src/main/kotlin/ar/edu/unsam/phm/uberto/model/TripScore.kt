package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate

data class TripScore(val message:String = "", val scorePoints:Int = 0, val date:LocalDate = LocalDate.now(), val passenger: Passenger)
