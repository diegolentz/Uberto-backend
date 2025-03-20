package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.User
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
        objeto.id = instances.size + 1
    }

}

// User
@Component
class UserRepository(): Repository<User>() {
    fun search(username: String): User?{
        if(!exisUsername(username)){
            throw BusinessException("Credenciales invalidas")
        }
        return instances.find { it.username == username }
    }

    fun exisUsername(username: String): Boolean {
        return instances.any {
            it.username == username
        }
    }
}
@Component
class DriverRepository(): Repository<Driver>() {

    fun search(loginRequest: LoginRequest): Driver?{
        if(!exisUsername(loginRequest.username)){
            throw BusinessException("Credenciales invalidas")
        }
        return instances.find { it.username == loginRequest.username && it.password == loginRequest.password}
    }

    fun exisUsername(username: String): Boolean {
        return instances.any {
            it.username == username
        }
    }
}
@Component
class PassengerRepository(): Repository<Passenger>() {

    fun search(loginRequest: LoginRequest): Passenger?{
        if(!exisUsername(loginRequest.username)){
            throw BusinessException("Credenciales invalidas")
        }
        return instances.find { it.username == loginRequest.username && it.password == loginRequest.password}
    }

    fun exisUsername(username: String): Boolean {
        return instances.any {
            it.username == username
        }
    }
}
@Component
class TripsRepository(): Repository<Trip>() {
}