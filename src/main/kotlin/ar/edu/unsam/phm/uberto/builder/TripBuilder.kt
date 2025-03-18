package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Trip


class TripBuilder(val newTrip: Trip = Trip()) {

    fun driver(driver: Driver): TripBuilder = apply {
        newTrip.driver = driver
    }

    fun passenger(client: Passenger): TripBuilder = apply {
        newTrip.client = client
    }

    fun origin(origin: String): TripBuilder = apply {
        newTrip.origin = origin
    }

    fun duration(duration: Double): TripBuilder = apply {
        newTrip.duration = duration
    }

    fun destination(destination: String): TripBuilder = apply {
        newTrip.destination = destination
    }

    fun passengerAmmount(ammount: Int): TripBuilder = apply {
        newTrip.numberPassengers = ammount
    }

    fun build(): Trip = newTrip

}