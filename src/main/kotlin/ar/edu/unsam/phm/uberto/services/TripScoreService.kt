package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.model.TripScore
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.TripScoreRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Service
class TripScoreService(private val tripScoreRepo: TripScoreRepository, private val driverRepo: DriverRepository) {
    fun getAllFromDriver(userId: Int): List<TripScore?>{
        val user = driverRepo.searchByUserID(userId)
        if(user == null ) throw Exception("ERROR id")
        return user.trips.filter{ it.score != null}.map { it.score}
    }
}