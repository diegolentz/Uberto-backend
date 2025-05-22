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
        // ...tus stages previos...
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
                "'averageScore': { '\$ifNull': [ '\$averageScore', 0 ] }, " +
                "'_class': 1 " +         // <--- AGREGAR ESTO!
                "} }"
    ])
    fun getAvailable(start: LocalDateTime, end: LocalDateTime): List<Driverwithscorage>
}