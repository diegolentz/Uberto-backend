package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

import javax.print.attribute.standard.Destination

interface TripsRepository : CrudRepository<Trip, Long> {

    @EntityGraph(attributePaths = ["client", "driver", "score"])
    fun findByClient(client: Passenger): List<Trip>

    @EntityGraph(attributePaths = ["client", "driver", "score"])
    fun findByDriver(driver: Driver): List<Trip>

@Query("""
        SELECT t FROM Trip t 
        WHERE ( t.driver.id = :driverId)
        AND ( t.origin = :origin)
        AND ( t.destination = :destination)
        AND ( t.numberPassengers = :numberPassengers)
        AND (
        LOWER(t.client.firstName) LIKE LOWER(CONCAT('%', :name, '%')) 
        OR LOWER(t.client.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
    ) 
    """)
@EntityGraph(attributePaths = ["driver", "client"])
    fun searchByForm(
        origin: String,
        destination: String,
        numberPassengers: Int,
        name: String,
        driverId: Long
    ): List<Trip>

}