package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.model.Analytics
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.AnalyticsRepository
import org.springframework.stereotype.Service

@Service
class AnalyticsService(val analyticsRepository: AnalyticsRepository) {
    fun logClick(_passenger: Passenger, _driver: Driver) {
        val analitycData = Analytics().apply {
            passengerId = _passenger.id
            driverId = _driver.id
            passenger = _passenger.firstName + " " + _passenger.lastName
            driver = _driver.firstName + " " + _driver.lastName
        }
        analyticsRepository.save(analitycData)
    }
}