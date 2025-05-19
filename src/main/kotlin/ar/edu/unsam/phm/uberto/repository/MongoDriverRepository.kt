package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoDriverRepository: MongoRepository<Driver,String> {
//    @Query("""
//        {}
//    """)
    fun findByCredentialsId(id: Long) : Driver

//    @Query("{ estado : 'D', activo: true, titulo: {'$regex': :#{#valorABuscar}, '$options': 'i'} }")
//    fun getLibrosPrestables(@Param("valorABuscar") valorABuscar: String): Collection<Libro>

}