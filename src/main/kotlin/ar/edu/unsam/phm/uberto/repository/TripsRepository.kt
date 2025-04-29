package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

import javax.print.attribute.standard.Destination

interface TripsRepository : CrudRepository<Trip, Long> {

    @EntityGraph(attributePaths = ["client", "driver", "score"])
    fun findByClient(client: Passenger): List<Trip>

    @EntityGraph(attributePaths = ["client", "driver", "score"])
    fun findByDriver(driver: Driver): List<Trip>

    @Query(
        """
        SELECT t FROM Trip t 
        WHERE ( t.driver.id = :driverId)
        AND ( t.origin = :origin)
        AND ( t.destination = :destination)
        AND ( t.numberPassengers = :numberPassengers)
        AND (
        LOWER(t.client.firstName) LIKE LOWER(CONCAT('%', :name, '%')) 
        OR LOWER(t.client.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
    ) 
    """
    )
    @EntityGraph(attributePaths = ["driver", "client"])
    fun searchByForm(
        origin: String,
        destination: String,
        numberPassengers: Int,
        name: String,
        driverId: Long
    ): List<Trip>

    //
//@Query(nativeQuery = true,
//    value = """
//   SELECT trip.driver_id , AVG(ts.score_points)
//        FROM trip
//            JOIN trip_score ts
//                ON ts.id = trip.tripscore_id
//                    where trip.driver_id in (:id)
//                        GROUP BY trip.driver_id
//""")
//    fun getAverage(id : List<Long>): List<DriverAvgDTO>
//
    @Query(
        """
    SELECT d.id, d.serial, d.brand, d.first_name, d.model, d.img,
           COALESCE(AVG(ts.score_points), 0) AS average_score
    FROM driver d
    LEFT JOIN trip t ON t.driver_id = d.id
    LEFT JOIN trip_score ts ON t.tripscore_id = ts.id
    WHERE d.id NOT IN (
        SELECT driver_id
        FROM trip
        WHERE :date BETWEEN date AND (date + (duration || ' minutes')::interval)
    )
    GROUP BY d.id
""", nativeQuery = true
    )
    fun getAvailable(
        @Param("date") date: LocalDateTime): List<Driver>
}

