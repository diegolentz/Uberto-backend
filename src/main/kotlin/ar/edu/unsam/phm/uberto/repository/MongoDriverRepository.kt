package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.dto.DriverAvailableDto
import ar.edu.unsam.phm.uberto.dto.Driverwithscorage
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
                "'firstName': { '\$first': '\$firstName' }, " +
                "'lastName': { '\$first': '\$lastName' }, " +
                "'balance': { '\$first': '\$balance' }, " +
                "'credentialsId': { '\$first': '\$credentialsId' }, " +
                "'img': { '\$first': '\$img' }, " +
                "'model': { '\$first': '\$model' }, " +
                "'brand': { '\$first': '\$brand' }, " +
                "'serial': { '\$first': '\$serial' }, " +
                "'basePrice': { '\$first': '\$basePrice' }, " +
                "'tripsDTO': { '\$push': '\$tripsDTO' }, " +
                "'averageScore': { '\$avg': { '\$cond': [ { '\$gt': [ '\$tripsDTO.rating', 0 ] }, '\$tripsDTO.rating', null ] } }, " +
                "'totalTrips': { '\$sum': 1 }, " +
                "'ratedTrips': { '\$sum': { '\$cond': [ { '\$gt': [ '\$tripsDTO.rating', 0 ] }, 1, 0 ] } } " +
                "} }",
        "{ '\$project': { " +
                "'_id': 1, " +
                "'firstName': 1, " +
                "'lastName': 1, " +
                "'balance': 1, " +
                "'credentialsId': 1, " +
                "'img': 1, " +
                "'model': 1, " +
                "'brand': 1, " +
                "'serial': 1, " +
                "'basePrice': 1, " +
                "'tripsDTO': 1, " +
                "'averageScore': { '\$ifNull': [ '\$averageScore', 0 ] } " +
                "} }"
    ])
    fun getAvailable(start: LocalDateTime, end: LocalDateTime): List<Driverwithscorage>


}