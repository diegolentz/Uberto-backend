package ar.edu.unsam.phm.uberto.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime



@RedisHash("registro", timeToLive = 180)
data class HomeSearch (
    var numberPassengers: Int,
    var date: LocalDateTime,
    var origin: String,
    var destination: String,
    var passengerId: Long
)
{
    @Id
    var id: String? = null
}
