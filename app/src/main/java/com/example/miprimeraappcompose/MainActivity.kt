package com.example.miprimeraappcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.miprimeraappcompose.ui.theme.MiPrimeraAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPrimeraAppComposeTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val databaseHelper = remember { DatabaseHelper() }
    var mensajeConexion by remember { mutableStateOf("Sin probar conexión") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        Text(
            text = "Mi App con PostgreSQL",
            fontSize = 24.sp
        )

        Text(
            text = mensajeConexion,
            fontSize = 16.sp
        )

        Button(
            onClick = {
                Toast.makeText(context, "¡Hola Onichan!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Botón Original")
        }

        Button(
            onClick = {
                scope.launch {
                    mensajeConexion = "Probando conexión..."
                    val resultado = databaseHelper.probarConexion()
                    mensajeConexion = resultado
                    Toast.makeText(context, resultado, Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text("🗄️ Probar Conexión DB")
        }
    }
}