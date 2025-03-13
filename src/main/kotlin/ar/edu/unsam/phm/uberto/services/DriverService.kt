package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.Repository
import org.springframework.stereotype.Service

@Service
object DriverService {
    val driverRepository: Repository<Driver> = Repository()

    fun getAllDrivers(): List<Driver> {
        return driverRepository.instances.toMutableList()
    }

}