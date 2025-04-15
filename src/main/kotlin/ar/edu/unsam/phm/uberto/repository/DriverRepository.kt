package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.dto.FormTripDTO
import ar.edu.unsam.phm.uberto.model.Driver // ✅ Importa la entidad correcta
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.services.auth.UserAuthCredentials
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface DriverRepository : CrudRepository<Driver, Long> {

    @EntityGraph(attributePaths = ["trips","id"])
    override fun findAll(): List<Driver>

//    fun searchByForm(form: FormTripDTO, driverId: Int): List<Trip>{
//        val tripFromDriver = instances.filter { it.driver.userId == driverId }
//        return tripFromDriver.filter{ trip ->
//            (form.origin == trip.origin || form.origin == null || form.origin == "") &&
//            (form.destination == trip.destination || form.destination == null || form.destination == "") &&
//            (form.numberPassengers == trip.numberPassengers || form.numberPassengers == null) &&
//            (form.name == trip.client.firstName || form.name == null || form.name == "")
//        }
//    }
}
