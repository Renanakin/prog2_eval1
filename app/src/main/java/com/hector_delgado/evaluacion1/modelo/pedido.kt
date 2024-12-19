package com.hector_delgado.evaluacion1.modelo

data class Platillo(val nombre: String, val precio: Int)
class Pedido {
    private val platillos = mutableListOf<Pair<Platillo, Int>>() // Lista de platillos y cantidades

    // Agregar un platillo al pedido
    fun agregarPlatillo(platillo: Platillo, cantidad: Int) {
        platillos.add(Pair(platillo, cantidad))
    }

    // Calcular el subtotal
    fun calcularSubtotal(): Int {
        return platillos.sumOf { it.first.precio * it.second }
    }

    // Calcular el monto de la propina
    fun calcularPropina(): Int {
        return (calcularSubtotal() * 0.10).toInt()
    }

    // Calcular el total con propina
    fun calcularTotalConPropina(): Int {
        return calcularSubtotal() + calcularPropina()
    }
}