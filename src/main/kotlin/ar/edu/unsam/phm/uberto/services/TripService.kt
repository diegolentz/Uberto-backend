package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.dto.FormTripDTO
import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Service
class TripService(val passengerRepo: PassengerRepository, val driverRepo: DriverRepository, val tripRepo: TripsRepository) {

    @Transactional
    fun createTrip(trip: TripDTO): ResponseEntity<String> {

        val client = passengerRepo.findById(trip.userId!!).get()
        val driver = driverRepo.findById(trip.driverId!!).get()

        val newTrip = Trip().apply {
            duration = trip.duration
            numberPassengers = trip.numberPassengers
            date = trip.date
            origin = trip.origin
            destination = trip.destination
            this.client = client
            this.driver = driver
        }

        client.requestTrip(newTrip)
        driver.responseTrip(newTrip, trip.duration)

        try{
            passengerRepo.save(client)
            driverRepo.save(driver)
            tripRepo.save(newTrip)
        }catch (e: SQLException){
            throw RuntimeException("Error en la creación del viaje")
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Se reserva de viaje exitosamente")

    }

//    fun getAllTrips(): List<Trip> {
//        return tripRepo.instances.toMutableList()
//    }

    fun getById(id: Long, rol: String): List<Trip> {
        if(rol == "PASSENGER"){
            return tripRepo.findByClient_Id(id)
        }else{
            return tripRepo.findByDriver_Id(id)
        }
    }

//
//    fun getPending(id: Int, rol: String): List<Trip> {
//        if(rol == "passenger"){
//            val passenger = passengerRepo.searchByUserID(id) ?: throw Exception("Pasajero no encontrado")
//            return passenger.pendingTrips()
//        }else{
//            val driver = driverRepo.searchByUserID(id) ?: throw Exception("Chofer no encontrado")
//            return driver.pendingTrips()
//        }
//    }
//
//    fun getFinished(id: Int, rol: String): List<Trip> {
//        if(rol == "passenger"){
//            val passenger = passengerRepo.searchByUserID(id) ?: throw Exception("Pasajero no encontrado")
//            return passenger.finishedTrips()
//        }else{
//            val driver = driverRepo.searchByUserID(id) ?: throw Exception("Chofer no encontrado")
//            return driver.finishedTrips()
//        }
//    }
//
//
    fun getTripsPendingFromDriver(
        origin: String,
        destination: String,
        numberPassenger: Int,
        name: String,
        driverId: Long): List<Trip> {
        return tripRepo.searchByForm(origin, destination, numberPassenger, name, driverId)
    }
}