package ar.edu.unsam.phm.uberto.repository

//@Component
//class TripsRepository(): Repository<Trip>() {
//
//    fun searchByForm(form: FormTripDTO, driverId: Int): List<Trip>{
//        val tripFromDriver = instances.filter { it.driver.userId == driverId }
//        return tripFromDriver.filter{ trip ->
//            (form.origin == trip.origin || form.origin == null || form.origin == "") &&
//            (form.destination == trip.destination || form.destination == null || form.destination == "") &&
//            (form.numberPassengers == trip.numberPassengers || form.numberPassengers == null) &&
//            (form.name == trip.client.firstName || form.name == null || form.name == "")
//        }
//    }
//}
