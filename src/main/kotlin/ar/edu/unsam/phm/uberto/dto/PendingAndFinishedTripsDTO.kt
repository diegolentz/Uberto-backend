package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Trip

data class PendingAndFinishedTripsDTO(
    val pendingTrips: List<TripDTO>,
    val finishedTrips: List<TripDTO>
)

