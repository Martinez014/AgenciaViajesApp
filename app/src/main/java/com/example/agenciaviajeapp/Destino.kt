package com.example.agenciaviajeapp

import java.io.Serializable

data class Destino(
    var id: String = "",
    var nombre: String = "",
    var pais: String = "",
    var precio: Double = 0.0,
    var descripcion: String = "",
    var imagenUrl: String = ""
) : Serializable