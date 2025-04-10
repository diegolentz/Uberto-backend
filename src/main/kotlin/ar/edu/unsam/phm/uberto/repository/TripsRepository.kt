package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Trip
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import javax.print.attribute.standard.Destination

interface TripsRepository : CrudRepository<Trip, Long> {

    fun findByClient_Id(clientId: Long): List<Trip>

    fun findByDriver_Id(driverId: Long): List<Trip>

@Query("""
        SELECT t FROM Trip t 
        WHERE ( t.driver.id = :driverId)
        AND ( t.origin = :origin)
        AND ( t.destination = :destination)
        AND ( t.numberPassengers = :numberPassengers)
        AND (t.client.firstName LIKE %:name%) 
    """) //TODO arreglar tema de nombre completo
    fun searchByForm(
        origin: String,
        destination: String,
        numberPassengers: Int,
        name: String,
        driverId: Long
    ): List<Trip>

}