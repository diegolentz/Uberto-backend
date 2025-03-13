package ar.edu.unsam.phm.uberto.repository

import exceptions.BusinessException
import org.springframework.stereotype.Component


//Hace referencia a los objetos los cuales van a ser instancias en cada repositorio
//Esto es para no tener un repositorio de entidades no deseadas

interface AvaliableInstance {
    var id: Int

    fun cumpleCriterioBusqueda(texto: String): Boolean
}

@Component
class Repository<T : AvaliableInstance> {
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

    fun search(texto: String): List<T> = instances.filter { it.cumpleCriterioBusqueda(texto) }

    private fun asignarID(objeto: T) {
        objeto.id = instances.size + 1
    }

}