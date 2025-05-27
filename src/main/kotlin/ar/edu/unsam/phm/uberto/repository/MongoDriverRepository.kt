package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.dto.DriverAvailableDto
import ar.edu.unsam.phm.uberto.dto.Driverwithscorage
import ar.edu.unsam.phm.uberto.dto.TripScoreDTOMongo
import ar.edu.unsam.phm.uberto.model.Driver
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime
import org.bson.Document
import org.springframework.cglib.core.Local
import org.springframework.data.mongodb.repository.Query
import java.time.Instant
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
        "{ '\$match': { '\$or': [ " +
                "{ 'tripsDTO': { '\$exists': false } }, " +                    // No tiene el campo tripsDTO
                "{ 'tripsDTO': { '\$size': 0 } }, " +                         // tripsDTO está vacío
                "{ 'tripsDTO': { '\$not': { '\$elemMatch': { " +
                "'date': { '\$lt': ?1 }, " +                              // El viaje comienza antes del fin del rango solicitado
                "'finishedDateTime': { '\$gt': ?0 } " +                  // El viaje termina después del inicio del rango solicitado
                "} } } }" +                                                  // Excluye viajes que se solapen
                "] } }",

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
                "'averageScore': { '\$ifNull': [ '\$averageScore', 0 ] }, " +
                "'_class': 1 " +
                "} }"
    ])
    fun getAvailable(start: Instant, end: Instant): List<Driverwithscorage>

//    @Aggregation(
//        pipeline = [
//            "{ '\$match': { 'tripsDTO.passengerId': ?0 } }",
//            "{ '\$addFields': { " +
//                    "'tripsDTO': { '\$filter': { " +
//                    "'input': '\$tripsDTO', 'as': 'trip', " +
//                    "'cond': { '\$and': [ " +
//                    "{ '\$eq': ['\$\$trip.passengerId', ?0] }, " +
//                    "{ '\$lt': ['\$\$trip.finishedDateTime', ?1] } " +
//                    "] } } } } }"
//        ])
//    fun getScoreByDriverID(id : String): List<TripScoreDTOMongo>

    @Query(value = "{ '_id': ?0 }", fields = "{ 'tripsScoreDTO': 1, '_id': 0 }")
    fun getScoreByDriverID(driverId: String): DriverTripScoresProjection?


}

data class DriverTripScoresProjection(
    val tripsScoreDTO: List<TripScoreDTOMongo>
)