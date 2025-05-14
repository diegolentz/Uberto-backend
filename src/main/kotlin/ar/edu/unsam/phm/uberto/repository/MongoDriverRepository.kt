package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.MongoDriver
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface MongoDriverRepository: MongoRepository<MongoDriver,String> {
    fun findByCredentialsId(id : Long): Optional<MongoDriver>

}