package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime

interface MongoDriverRepository: MongoRepository<Driver,String> {
//    @Query("""
//        {}
//    """)
    fun findByCredentialsId(id: Long) : Driver

//    @Query("{ estado : 'D', activo: true, titulo: {'$regex': :#{#valorABuscar}, '$options': 'i'} }")
//    fun getLibrosPrestables(@Param("valorABuscar") valorABuscar: String): Collection<Libro>

    @Query(value = "{ '_id': ?0, 'tripsDTO': { '\$elemMatch': { 'finishedDateTime': { '\$lt': ?1 } } } }",
        fields = "{ 'firstName': 1, 'lastName': 1, 'image': 1 }")
    fun findByFinishedTrips(driverId: String, finisherdDate: LocalDateTime): Driver
}