package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Vehicle
import ar.edu.unsam.phm.uberto.model.VehicleBrand

data class VehicleDTO (
    val id: Int,
    val brand: VehicleBrand,
    val model: Int,
    val isCar: Boolean
)

//Id 0, suponiendo que cuando se cree el repo todos van a implementar la interfaz con el id
fun Vehicle.toDTO() = VehicleDTO(brand = brand, id = 0, model = model, isCar = isCar)

