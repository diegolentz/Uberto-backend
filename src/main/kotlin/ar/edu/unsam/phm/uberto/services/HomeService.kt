package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.model.HomeSearch
import ar.edu.unsam.phm.uberto.repository.HomeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HomeService(
    @Autowired private val homeRepo: HomeRepository
) {

    fun saveHome(home: HomeSearch): HomeSearch {
        return homeRepo.save(home)
    }

    fun getHomeByPassengerId(id: Long): HomeSearch {
        return homeRepo.findById(id).orElse(HomeSearch(null, null, null, null, null))
    }
}
