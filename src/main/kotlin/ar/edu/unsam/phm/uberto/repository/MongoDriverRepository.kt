package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface MongoDriverRepository: MongoRepository<Driver,String> {

    fun findByCredentialsId(id: Long) : Driver

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

}
