package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Trip
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface TripsRepository : CrudRepository<Trip, Long> {


    @Query(nativeQuery = true, value = """
        SELECT id  FROM driver d
        WHERE d.id NOT IN (
            SELECT driver_id FROM trip t
            WHERE t.date = :startDate

)
""")
    fun findAvailableDrivers(
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: Int
    ): List<Long>


}