package ar.edu.unsam.phm.uberto.model

data class Vehicle(val brand: VehicleBrand, val model: Int, val isCar: Boolean) {
    fun typeOf(): String = if (isCar) {
        if (model <= 10) "Premium" else "Simple"
    } else {
        "Moto"
    }
}
