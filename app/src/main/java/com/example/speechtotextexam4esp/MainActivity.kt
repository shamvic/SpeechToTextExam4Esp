package com.example.speechtotextexam4esp


import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    // Creamos las variables para el texto y la imagen
    lateinit var outputTV: TextView
    lateinit var micIV: ImageView

    // Creamos una constante para el código de solicitud del reconocimiento de voz
    private val REQUEST_CODE_SPEECH_INPUT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos las variables del texto y la imagen
        outputTV = findViewById(R.id.idTVOutput)
        micIV = findViewById(R.id.idIVMic)

        // Agregamos un listener al botón del micrófono
        micIV.setOnClickListener {
            // Creamos un intent para el reconocimiento de voz
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // Especificamos el modelo de lenguaje y el modelo gratuito en nuestro intent
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // Configuramos el idioma para que sea español
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                // "es-ES" // Código de idioma para español de España
                "ru-RU"
            )

            // Especificamos un mensaje para indicar al usuario que hable en español
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla para convertir en texto")

            // Lanzamos el intent con un bloque try-catch para manejar excepciones
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // Mostramos un mensaje de error en caso de que algo salga mal
                Toast.makeText(
                    this@MainActivity, "Error: " + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Método que se llama cuando el reconocimiento de voz finaliza
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Verificamos si el código de solicitud es el mismo que nuestro código de reconocimiento de voz
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // Verificamos si el resultado es correcto (RESULT_OK) y si hay datos en el intent
            if (resultCode == RESULT_OK && data != null) {
                // Extraemos la lista de cadenas de resultados del intent
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // Establecemos el primer resultado en nuestro TextView de salida
                outputTV.text = Objects.requireNonNull(res)[0]
            }
        }
    }
}
