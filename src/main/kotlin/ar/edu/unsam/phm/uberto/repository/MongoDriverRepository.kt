package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.dto.DriverAvailableDto
import ar.edu.unsam.phm.uberto.model.Driver
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime
import org.bson.Document
import org.springframework.cglib.core.Local
import java.util.Date



interface MongoDriverRepository: MongoRepository<Driver,String> {


    fun findByCredentialsId(id: Long): Driver

    @Query("{ 'tripsDTO': { \$elemMatch: { 'numberPassengers': 4, 'finishedDateTime': { \$lt: ?1 } } } }")
    fun findByPassengerIdFinishedTripsDTO(passengerId: Long, finisherdDate: LocalDateTime): List<Driver>

    @Aggregation(pipeline = [
        "{ '\$match': { 'tripsDTO': { '\$not': { '\$elemMatch': { 'date': { '\$lt': ?1 }, 'finishedDateTime': { '\$gt': ?0 } } } } } }",
        "{ '\$unwind': { 'path': '\$tripsDTO', 'preserveNullAndEmptyArrays': true } }",
        "{ '\$match': { 'tripsDTO': { '\$ne': null } } }",
        "{ '\$group': { " +
                "'_id': '\$_id', " +
                "'driver': { '\$first': '\$\$ROOT' }, " +
                "'averageScore': { " +
                "'\$avg': { " +
                "'\$cond': [ { '\$gt': [ '\$tripsDTO.rating', 0 ] }, '\$tripsDTO.rating', null ] " +
                "} " +
                "}, " +
                "'totalTrips': { '\$sum': 1 }, " +
                "'ratedTrips': { '\$sum': { '\$cond': [ { '\$gt': [ '\$tripsDTO.rating', 0 ] }, 1, 0 ] } } " +
                "} }",
        "{ '\$project': { " +
                "'_id': 1, " +
                "'driver': 1, " +  // devuelve el driver completo
                "'averageScore': { '\$ifNull': [ '\$averageScore', 0 ] } " +
                "} }"
    ])
    fun getAvailable(start: LocalDateTime, end: LocalDateTime): List<DriverAvailableDto>


}