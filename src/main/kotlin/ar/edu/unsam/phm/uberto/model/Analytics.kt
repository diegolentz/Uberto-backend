package ar.edu.unsam.phm.uberto.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import org.springframework.data.annotation.Id

@Document(collection = "analytics")
class Analytics () {
    @Id
    var id: ObjectId? = null
    var passengerId: Long? = null
    var passenger: String? = null
    var driverId: String? = null
    var driver: String? = null
    val timestamp: LocalDateTime = LocalDateTime.now()

}