package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface PassengerRepository : CrudRepository<Passenger, Long> {

    @Query(
        """
    SELECT p FROM Passenger p 
    WHERE p.id != :id 
      AND p NOT IN (
        SELECT friend FROM Passenger passenger 
        JOIN passenger.friends friend 
        WHERE passenger.id = :id
      )
      AND (
        LOWER(p.firstName) LIKE CONCAT('%', :pattern, '%') 
        OR LOWER(p.lastName) LIKE CONCAT('%', :pattern, '%')
      )
"""
    )
    fun findPossibleFriends(@Param("id") id: Long, @Param("pattern") pattern: String): List<Passenger>
}