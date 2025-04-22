package ar.edu.unsam.phm.uberto.factory

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.PremiumDriver
import ar.edu.unsam.phm.uberto.model.Trip

class TestFactory {

    fun createPassenger(amount : Int): List<Passenger>{
        val listPassenger: MutableList<Passenger> = mutableListOf()
        for(i in 0..amount){
            val passenger = Passenger().apply {
                firstName = "Pasajero ${i}"
                lastName = "Test ${i}"
            }
            listPassenger.add(passenger)
        }
        return  listPassenger
    }

    fun createDriverPremium(amount : Int): List<Driver>{
        val listDriver: MutableList<Driver> = mutableListOf()
        for(i in 0..amount){
            val driver = PremiumDriver().apply {
                firstName = "Driver Premium ${i}"
                lastName = "Test Premium ${i}"
                img = ""
            }
            listDriver.add(driver)
        }
        return  listDriver
    }

    fun createTripFinished(amount: Int): List<Trip>{
        val listDriver = createDriverPremium(amount)
        val listPassenger = createPassenger(amount)
        val listTrip: MutableList<Trip> = mutableListOf()

        for(i in 0..amount){
            val trip = Trip().apply {
                client = listPassenger.get(i)
                driver = listDriver.get(i)
            }
            listTrip.add(trip)
        }
        return listTrip
    }

    fun createTrip(passenger: Passenger, driver: Driver): Trip{
        return Trip().apply {
            client = passenger
            this.driver = driver
        }
    }
}