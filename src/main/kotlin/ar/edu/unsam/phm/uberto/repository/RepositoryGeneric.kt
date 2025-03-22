package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.services.auth.UserAuthCredentials
import exceptions.BusinessException
import org.springframework.stereotype.Component


//Hace referencia a los objetos los cuales van a ser instancias en cada repositorio
//Esto es para no tener un repositorio de entidades no deseadas

//TODO Se necesita un metodo upsert??? la tiro no mas

interface AvaliableInstance {
    var id: Int

}

@Component
abstract class Repository<T : AvaliableInstance> {
    var instances: MutableSet<T> = mutableSetOf()

    fun create(objeto: T): Boolean {
        this.asignarID(objeto)
        return instances.add(objeto) //SI es Set devuelve true o false, si es list siempre devuelve true
    }

    fun delete(objeto: T) {
        exist(objeto.id)
        instances.removeIf { objeto.id == it.id }//
    }

    private fun exist(id: Int) {
        if (!existeElemento(id)) {
            throw BusinessException("no se encuentra")
        }
    }

    private fun existeElemento(id: Int) = instances.any { it.id == id }

    fun update(objeto: T) {
        delete(objeto)
        instances.add(objeto)
    }

    fun getByID(objectID: Int): T {
        exist(objectID)
        return instances.first { it.id == objectID } //devuelve elemento o null
    }


    private fun asignarID(objeto: T) {
        val lastId = instances.maxOfOrNull { it.id }
        objeto.id = if(lastId != null) lastId + 1 else 1
    }

}

@Component
class DriverRepository(): Repository<Driver>() {

    fun searchByUserID(userID:Int): Driver?{
        return instances.find { it.userId == userID }
    }
}
@Component
class PassengerRepository(): Repository<Passenger>() {

    fun searchByUserID(userID:Int): Passenger?{
        return instances.find { it.userId == userID}
    }
}

@Component
class TripsRepository(): Repository<Trip>() {
}

@Component
class TripScoreRepository(): Repository<Trip>() {
}

@Component
class AuthCredentialsRepository(): Repository<UserAuthCredentials>() {
}