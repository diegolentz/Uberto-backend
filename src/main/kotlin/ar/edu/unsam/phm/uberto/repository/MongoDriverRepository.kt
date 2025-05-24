package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.dto.DriverAvailableDto
import ar.edu.unsam.phm.uberto.dto.Driverwithscorage
import ar.edu.unsam.phm.uberto.model.Driver
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime
import org.bson.Document
import org.springframework.cglib.core.Local
import java.util.Date



interface MongoDriverRepository: MongoRepository<Driver,String> {


    fun findByCredentialsId(id: Long): Driver

    @Aggregation(
        pipeline = [
            "{ '\$match': { 'tripsDTO.passengerId': ?0 } }",
            "{ '\$addFields': { " +
                    "'tripsDTO': { '\$filter': { " +
                    "'input': '\$tripsDTO', 'as': 'trip', " +
                    "'cond': { '\$and': [ " +
                    "{ '\$eq': ['\$\$trip.passengerId', ?0] }, " +
                    "{ '\$gte': ['\$\$trip.finishedDateTime', ?1] } " +
                    "] } } } } }"
        ]
    )
    fun findByPassengerIdPendingTripsDTO(passengerId: Long, finisherdDate: LocalDateTime): List<Driver>
  
    @Aggregation(
        pipeline = [
            "{ '\$match': { 'tripsDTO.passengerId': ?0 } }",
            "{ '\$addFields': { " +
                    "'tripsDTO': { '\$filter': { " +
                    "'input': '\$tripsDTO', 'as': 'trip', " +
                    "'cond': { '\$and': [ " +
                    "{ '\$eq': ['\$\$trip.passengerId', ?0] }, " +
                    "{ '\$lt': ['\$\$trip.finishedDateTime', ?1] } " +
                    "] } } } } }"
        ]
    )
    fun findByPassengerIdFinishedTripsDTO(passengerId: Long, date: LocalDateTime): List<Driver>




    @Aggregation(pipeline = [
        "{ '\$match': { 'tripsDTO': { '\$not': { '\$elemMatch': { 'date': { '\$lt': ?1 }, 'finishedDateTime': { '\$gt': ?0 } } } } } }",
        "{ '\$addFields': { " +
                "'averageScore': { " +
                "'\$avg': { " +
                "'\$map': { " +
                "'input': { '\$filter': { 'input': '\$tripsDTO', 'as': 't', 'cond': { '\$gt': ['\$\$t.rating', 0] } } }, " +
                "'as': 't', " +
                "'in': '\$\$t.rating' " +
                "} " +
                "} " +
                "} " +
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
                "'averageScore': { '\$ifNull': [ '\$averageScore', 0 ] } ," +
                "'_class': 1 " +
                "} }"
    ])
    fun getAvailable(start: LocalDateTime, end: LocalDateTime): List<Driverwithscorage>

}
  
