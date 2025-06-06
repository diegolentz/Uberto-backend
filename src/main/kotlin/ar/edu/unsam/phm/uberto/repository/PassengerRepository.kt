package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
@Repository("passengerJpaRepository")
interface PassengerRepository : JpaRepository<Passenger, Long> {

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

    @Query(
        """
    SELECT p FROM Passenger p 
    WHERE p.id = :id
"""
    )
    @EntityGraph(attributePaths = ["trips"])
    fun getByIdTrip(@Param("id") id: Long): Passenger

    fun findByCredentials_Id(id: Long): Optional<Passenger>

    @EntityGraph(attributePaths = ["friends", "trips.driver"])
    @Query("SELECT p FROM Passenger p")
    fun findAllWithFriendsTripsAndDrivers(): List<Passenger>

    @EntityGraph(attributePaths = ["friends", "trips"])
    override fun findAll() : List<Passenger>

    @Query("SELECT f FROM Passenger p JOIN p.friends f WHERE p.id = :passengerId")
    fun getFriends(@Param("passengerId") passengerId: Long): List<Passenger>
}