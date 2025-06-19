package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository("passengerJpaRepository")
interface PassengerRepository : JpaRepository<Passenger, Long> {


    @Query(
        """
    SELECT p FROM Passenger p 
    WHERE p.id = :id
"""
    )
    @EntityGraph(attributePaths = ["trips"])
    fun getByIdTrip(@Param("id") id: Long): Passenger

    fun findByCredentials_Id(id: Long): Optional<Passenger>


    @EntityGraph(attributePaths = ["trips"])
    override fun findAll(): List<Passenger>

}