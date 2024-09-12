package com.example.notas.models

data class Nota(
    var titulo: String,
    var nota: String,
    var colorIndex: Int = 0
)