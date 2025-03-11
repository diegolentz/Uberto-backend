package ar.edu.unsam.phm.uberto.model

data class Recommendation(val driver: Driver, val rating: Int, val comment: String) {
}

//es el chofer, solo la creo para que no explote
//borrar cuando se implemente chofer
