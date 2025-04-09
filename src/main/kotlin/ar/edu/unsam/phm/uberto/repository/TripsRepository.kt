package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Trip
import org.springframework.data.repository.CrudRepository

interface TripsRepository : CrudRepository<Trip, Long> {

    fun findByClient_Id(clientId: Long): List<Trip>

    fun findByDriver_Id(driverId: Long): List<Trip>
}