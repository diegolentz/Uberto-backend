package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.MongoDriver
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface MongoDriverRepository: MongoRepository<MongoDriver,String> {
    fun findByCredentialsId(id : String): Optional<MongoDriver>

}