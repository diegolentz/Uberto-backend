package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.model.HomeSearch
import org.springframework.data.repository.CrudRepository

interface  HomeRepository : CrudRepository<HomeSearch , String>{


    fun findByPassengerId(passengerId: Long): HomeSearch?


}