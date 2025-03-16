package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate

//Tipos de vehiculos
val simpleDriver = SimpleVehicle()
val premiumDriver = PremiumVehicle()
val motorbikeDriver = MotorbikeVehicle()

//Vehiculos
val carPremium = PremiumVehicle(VehicleBrand.FIAT, LocalDate.now().year, true, "ABC123")
val carSimple = SimpleVehicle(VehicleBrand.FIAT, 1910, true, "ABC123")
val moto = MotorbikeVehicle(VehicleBrand.FIAT, 1910, false, "ABC123")

//Chofer
val driverPremium = Driver("ChoferPremium","Premium","test1","pass123",18,0.0, carPremium,100.0)
val driverSimple = Driver("ChoferSimple","Simple","test2","pass123",18,0.0, carSimple,100.0)
val driverMotor = Driver("ChoferMoto","Moto","test3","pass123",18,0.0, moto,100.0)

//usuario
val usuario1 = User("nombre1", "apellido1", "usuario1", "1234", 18, 1234, 100.00)

//viaje
val viaje10min1pasajero = Travel(10,1, LocalDate.now(), "origen", "destino", usuario1)
val viaje10min2pasajero = Travel(10,2, LocalDate.now(), "origen", "destino", usuario1)
val viaje30min1pasajero = Travel(30,1, LocalDate.now(), "origen", "destino", usuario1)
val viaje31min1pasajero = Travel(31,1, LocalDate.now(), "origen", "destino", usuario1)
