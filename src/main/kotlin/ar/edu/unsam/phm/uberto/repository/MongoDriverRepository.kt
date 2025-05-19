package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.MongoDriver
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface MongoDriverRepository: MongoRepository<MongoDriver,String> {
//    @Query("""
//        {}
//    """)
    fun findByCredentialsId(id: Long) : MongoDriver

//    @Query("{ estado : 'D', activo: true, titulo: {'$regex': :#{#valorABuscar}, '$options': 'i'} }")
//    fun getLibrosPrestables(@Param("valorABuscar") valorABuscar: String): Collection<Libro>

}