package ar.edu.unsam.phm.uberto.dto

data class TripScoreDTO(
    val message: String,
    val scorePoints: Int,
    val date: String,
    val passengerName: String,
    val driverName: String,
    val avatarUrlPassenger: String,
    val avatarUrlDriver: String
)


//fun TripScore.toDTO() = TripScoreDTO(
//    message = message,
//    scorePoints = scorePoints,
//    date = date.toString(),
//    passengerId= passenger.userId,
//    avatarUrl = passenger.img
//)

