package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime

interface MongoDriverRepository: MongoRepository<Driver,String> {

    fun findByCredentialsId(id: Long) : Driver

    @Query("{ 'tripsDTO': { \$elemMatch: { 'numberPassengers': 4, 'finishedDateTime': { \$lt: ?1 } } } }")
    fun findByPassengerIdFinishedTripsDTO(passengerId: Long, finisherdDate: LocalDateTime): List<Driver>

    @Query("{ 'tripsDTO': { \$elemMatch: { 'numberPassengers': 4, 'finishedDateTime': { \$gte: ?1 } } } }")
    fun findByPassengerIdPassengerTripsDTO(passengerId: Long, finisherdDate: LocalDateTime): List<Driver>
}