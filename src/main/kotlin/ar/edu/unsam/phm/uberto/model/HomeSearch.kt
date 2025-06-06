package ar.edu.unsam.phm.uberto.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime



@RedisHash("registro", timeToLive = 180000)
data class HomeSearch (
    var numberPassengers: Int?,
    var date: LocalDateTime?,
    var origin: String?,
    var destination: String?,
)
{
    @Id
    var passengerId: Long? = null
}
