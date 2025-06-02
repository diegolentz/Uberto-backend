package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.model.HomeSearch
import ar.edu.unsam.phm.uberto.repository.HomeRepository
import exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class HomeService (
    @Autowired private val homeRepo: HomeRepository
) {

    fun saveHome(home : HomeSearch): HomeSearch {
        return homeRepo.save(home)
    }

fun getHomeByPassengerId(id: String): HomeSearch {
    return homeRepo.findById(id).orElseThrow {
        NotFoundException("HomeSearch with id $id not found")
    }
}
}