package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.data.repository.CrudRepository

interface PassengerRepository: CrudRepository<Passenger, Long> {
}