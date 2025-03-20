package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate

data class TravelScore(val message:String = "", val scorePoints:Int = 0, val date:LocalDate = LocalDate.now())
