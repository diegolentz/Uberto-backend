package ar.edu.unsam.phm.uberto.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:4200", "http://localhost:5173"])
@RestController
class UserController() {

    @GetMapping("/mock")
    fun dashboardData() : String{
        return "mock"
    }

}