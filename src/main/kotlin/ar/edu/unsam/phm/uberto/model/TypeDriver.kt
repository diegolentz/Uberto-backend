package ar.edu.unsam.phm.uberto.model

interface TypeDriver {
    var BASE_PRICE: Double
    //time [min]
    fun calculatePlus(time: Int, numberPassengers: Int): Double
}

class SimpleDriver : TypeDriver {
    override var BASE_PRICE = 1000.00
    override fun calculatePlus(time: Int, numberPassengers: Int): Double = BASE_PRICE * time
}

class  PremiumDrive : TypeDriver {
    override var  BASE_PRICE = 2000.00
    var PRICE_MORE_PASSENGERS = 1500.00

    override fun calculatePlus(time: Int, numberPassengers: Int): Double = if ( numberPassengers > 1) PRICE_MORE_PASSENGERS * time else BASE_PRICE * time
}

class  MotorbikeDriver : TypeDriver {
    override var  BASE_PRICE = 500.00
    var PRICE_MORE_TIME = 600.00
    var K_TIME = 30
    override fun calculatePlus(time: Int, numberPassengers: Int): Double = if ( time <= K_TIME) BASE_PRICE * time else PRICE_MORE_TIME * time
}