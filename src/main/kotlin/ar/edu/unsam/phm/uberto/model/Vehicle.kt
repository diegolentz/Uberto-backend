package ar.edu.unsam.phm.uberto.model

data class Vehicle(val brand: VehicleBrand, val model: Int, val isCar: Boolean) {
    fun typeOf(): TypeDriver = if (isCar) {
        if (model <= 10) PremiumDrive() else SimpleDriver()
    } else {
        MotorbikeDriver()
    }
}
