package ar.edu.unsam.phm.uberto.model

class BikeDriver(override var id: Int = 0): Driver() {
    private val reference:Double = 500.0
    override fun plusBasePrice(trip: Trip): Double {
        return if(this.travelTimeCondition(trip)) reference else reference + 100.0
    }

    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }

    private fun travelTimeCondition(trip:Trip):Boolean{
        return trip.duration < 30
    }
}

class SimpleDriver(override var id: Int = 0): Driver() {
    override fun plusBasePrice(trip: Trip): Double {
        return 1000.0
    }
    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }
}

class PremiumDriver(override var id: Int = 0): Driver() {
    private val reference:Double = 1500.0

    override fun plusBasePrice(trip: Trip): Double {
        return if(this.passengerCondition(trip)) reference else reference + 500.0
    }


    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }

    private fun passengerCondition(trip:Trip):Boolean{
        return trip.numberPassengers > 1
    }
}