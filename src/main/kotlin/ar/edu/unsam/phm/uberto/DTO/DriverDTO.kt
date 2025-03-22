package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.TripScore

data class DriverDTO(
    val id: Int,
    val serial: String,
    val firstName: String,
    val lastName: String,
    val brand: String,
    val model: Int,
    val basePrice: Double,
    val img: String,
    val trips: List<TripDTO>
)

fun Driver.toDTO() = DriverDTO(
    id = userId,
    serial = serial,
    firstName = firstName,
    lastName = lastName,
    brand = brand,
    model = model,
    basePrice = basePrice,
    img = img,
    trips = trips.map { it.toDTO() }
)

data class DriverCardDTO(
    val id: Int,
    val serial: String,
    val name: String,
    val brand: String,
    val model: Int,
    val basePrice: Double,
    val img: String,
    val rating: Double
)


fun Driver.toCardDTO() = DriverCardDTO(
    id = userId,
    serial = serial,
    brand = brand,
    name = firstName + " " +lastName,
    model = model,
    basePrice = basePrice,
    img = img,
    rating = scoreAVG()
)

data class DriverTripConfirm(
        val id: Int,
        val name: String,
        val brand: String,
        val model: Int,
        val serial: String,
        val scoreRatingAVG: Double,
        val type: String,
        val scores: List<TripScore>
        ){}

fun Driver.toTripConfirm() = DriverTripConfirm(
    id= userId,
    name = firstName + " " + lastName,
    brand = brand,
    model = model,
    serial = serial,
    scoreRatingAVG = scoreAVG(),
    type = toString(),
    scores = getScores()
)