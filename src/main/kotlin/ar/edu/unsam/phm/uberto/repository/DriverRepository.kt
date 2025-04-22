package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface DriverRepository : CrudRepository<Driver, Long> {

    @EntityGraph(attributePaths = ["trips"])
    override fun findAll(): List<Driver>

    @Query(
        """
    SELECT d FROM Driver d 
    WHERE d.id = :id
    """
    )
    @EntityGraph(attributePaths = ["trips"])
    fun getByIdTrip(@Param("id") id: Long): Driver
}
