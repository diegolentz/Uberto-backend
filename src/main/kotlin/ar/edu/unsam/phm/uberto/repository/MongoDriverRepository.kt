package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.MongoDriver
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoDriverRepository: MongoRepository<MongoDriver,String> {
}