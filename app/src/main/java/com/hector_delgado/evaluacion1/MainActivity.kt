package com.hector_delgado.evaluacion1

import android.annotation.SuppressLint // Ignora la advertencia del Switch estándar.
import android.os.Bundle  // Guarda el estado de la actividad.
import android.text.Editable // Maneja cambios en el texto.
import android.text.TextWatcher // 	Observa cambios en los EditText.
import android.widget.EditText // Permite editar texto.
import android.widget.Switch // Interruptor para activar/desactivar propina.
import android.widget.TextView // Muestra los resultados (subtotal, propina, total).
import androidx.activity.enableEdgeToEdge // Habilita el modo de bordes redondeados.
import androidx.appcompat.app.AppCompatActivity // Actividad principal.
import java.text.NumberFormat // Da formato a números como moneda.
import java.util.Locale // Define la configuración regional (Chile).

class MainActivity : AppCompatActivity() {

    // Declaración de componentes de la interfaz
    private lateinit var etCantidadPastel: EditText //
    private lateinit var etCantidadCazuela: EditText
    private lateinit var etPreTotalChoclo: EditText
    private lateinit var etPreTotalCazuela: EditText
    private lateinit var tvSubtotal: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvTotal: TextView

   //SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchPropina: Switch

    private var incluirPropina = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicialización de vistas
        etCantidadPastel = findViewById(R.id.etCantidadPastel)
        etCantidadCazuela = findViewById(R.id.etCantidadCazuela)
        etPreTotalChoclo = findViewById(R.id.etnPreTotalChoclo)
        etPreTotalCazuela = findViewById(R.id.etnPreTotalCazuela)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvPropina = findViewById(R.id.tvTotalPropina)
        tvTotal = findViewById(R.id.tvTotal)
        switchPropina = findViewById(R.id.switch1)

        // Actualización dinámica con TextWatcher
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                actualizarValores()
            }
        }
        etCantidadPastel.addTextChangedListener(textWatcher)
        etCantidadCazuela.addTextChangedListener(textWatcher)

        // Actualización al cambiar el estado del Switch
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            incluirPropina = isChecked
            actualizarValores()
        }
    }

    // Metodo para actualizar y calcualr todos los valores.
    private fun actualizarValores() {
        val cantidadPastel = etCantidadPastel.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = etCantidadCazuela.text.toString().toIntOrNull() ?: 0

        // Precios unitarios de los platillos
        val precioPastel = 12000
        val precioCazuela = 10000

        // Formateador de moneda
        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        // Mostrar monto unitario como hint si cantidad es 0 o vacía
        if (cantidadPastel == 0) {
            etPreTotalChoclo.hint = formatoMoneda.format(precioPastel)
            etPreTotalChoclo.setText("") // Dejar vacío el valor si no hay cantidad
        } else {
            val preTotalPastel = cantidadPastel * precioPastel
            etPreTotalChoclo.setText(formatoMoneda.format(preTotalPastel))
        }

        if (cantidadCazuela == 0) {
            etPreTotalCazuela.hint = formatoMoneda.format(precioCazuela)
            etPreTotalCazuela.setText("") // Dejar vacío el valor si no hay cantidad
        } else {
            val preTotalCazuela = cantidadCazuela * precioCazuela
            etPreTotalCazuela.setText(formatoMoneda.format(preTotalCazuela))
        }

        // Calcular subtotal
        val subtotal = (cantidadPastel * precioPastel) + (cantidadCazuela * precioCazuela)
        tvSubtotal.text = "Subtotal: ${formatoMoneda.format(subtotal)}"

        // Calcular propina
        val propina = if (incluirPropina) (subtotal * 0.10).toInt() else 0
        tvPropina.text = "Propina: ${formatoMoneda.format(propina)}"

        // Calcular total
        val total = subtotal + propina
        tvTotal.text = "Total: ${formatoMoneda.format(total)}"
    }

}


