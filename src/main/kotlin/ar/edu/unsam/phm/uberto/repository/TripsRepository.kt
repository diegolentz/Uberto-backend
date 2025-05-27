package ar.edu.unsam.phm.uberto.repository

//import ar.edu.unsam.phm.uberto.dto.DriverAvailableDto
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*

import javax.print.attribute.standard.Destination

interface TripsRepository : CrudRepository<Trip, Long> {

    @EntityGraph(attributePaths = ["client", "score"])
    fun findByClient(client: Passenger): List<Trip>

    @EntityGraph(attributePaths = ["client", "score"])
    fun findByDriverId(driverId: String): List<Trip>

    @Query(
        """
        SELECT t FROM Trip t 
        WHERE ( t.driverId = :driverId)
        AND ( t.origin = :origin)
        AND ( t.destination = :destination)
        AND ( t.numberPassengers = :numberPassengers)
        AND (
        LOWER(t.client.firstName) LIKE LOWER(CONCAT('%', :name, '%')) 
        OR LOWER(t.client.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
    ) 
    """
    )
    @EntityGraph(attributePaths = ["client"])
    fun searchByForm(
        origin: String,
        destination: String,
        numberPassengers: Int,
        name: String,
        driverId: String
    ): List<Trip>

    @Query("""
    SELECT t FROM Trip t 
    WHERE t.driverId = :driverId 
    AND t.finishedDateTime < LOCAL DATETIME 
""")
    @EntityGraph(attributePaths = ["client"])
    fun findByDriverIdFinishedTrips(@Param("driverId") driverId: String): List<Trip>

    @Query("""
        SELECT t FROM Trip t 
            WHERE t.finishedDateTime > :date AND t.date < :endDate  AND t.driverId = :driverId
    """)
    fun checkAvailableDriver(
        @Param("driverId") driverId: String,
        @Param("date") date: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime) : List<Trip>

    @EntityGraph(attributePaths = ["client", "score"]) // Para el Boostrap
    override fun findAll(): List<Trip>

    @EntityGraph(attributePaths = ["client", "score"])
    fun findTripByIdAndClient_Id(tripID: Long, passengerId:Long): Optional<Trip>

//    @Query("""
//SELECT
//    new ar.edu.unsam.phm.uberto.dto.DriverAvailableDto(
//        d,
//        COALESCE(AVG(ts.scorePoints), 0)
//    )
//FROM Driver d
//LEFT JOIN Trip t ON t.driver.id = d.id
//LEFT JOIN t.score ts
//WHERE d.id NOT IN (
//    SELECT t2.driver.id
//    FROM Trip t2
//    WHERE t2.date < :endDate AND t2.finishedDateTime > :date
//)
//GROUP BY d.id
//""")
//    fun getAvailable(
//        @Param("date") date: LocalDateTime,
//        @Param("endDate") endDate: LocalDateTime
//    ): List<DriverAvailableDto>

}

