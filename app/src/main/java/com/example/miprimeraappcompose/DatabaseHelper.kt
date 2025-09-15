package com.example.miprimeraappcompose

import kotlinx.coroutines.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseHelper {

    companion object {
        private const val HOST = "10.0.2.2"  // IP especial para emulador Android
        private const val PORT = "5432"
        private const val DATABASE_NAME = "tienda-emprendedor"
        private const val USERNAME = "postgres"
        private const val PASSWORD = "123456"

        private const val URL = "jdbc:postgresql://$HOST:$PORT/$DATABASE_NAME"
    }

    // Función para conectarse a la base de datos
    suspend fun conectarBaseDatos(): Connection? = withContext(Dispatchers.IO) {
        try {
            Class.forName("org.postgresql.Driver")
            val connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)
            println("✅ Conexión exitosa a PostgreSQL")
            connection
        } catch (e: SQLException) {
            println("❌ Error de conexión SQL: ${e.message}")
            null
        } catch (e: ClassNotFoundException) {
            println("❌ Driver PostgreSQL no encontrado: ${e.message}")
            null
        }
    }

    // Función para probar la conexión
    suspend fun probarConexion(): String = withContext(Dispatchers.IO) {
        val connection = conectarBaseDatos()
        connection?.use { conn ->
            val statement = conn.createStatement()
            val resultSet = statement.executeQuery("SELECT version();")

            if (resultSet.next()) {
                val version = resultSet.getString(1)
                return@withContext "Conectado: $version"
            }
        }
        return@withContext "Error: No se pudo conectar"
    }
}