package com.exchangepro.moviles.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.exchangepro.moviles.presentation.navigation.Route
import com.exchangepro.moviles.ui.components.ExchangeCard
import com.exchangepro.moviles.ui.components.PrimaryAction
import com.exchangepro.moviles.ui.components.SecondaryAction

@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("gustavo@exchangepro.pe") }
    val password = remember { mutableStateOf("123456") }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("ExchangePro", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("Intercambio P2P seguro desde tu movil", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(24.dp))
        ExchangeCard {
            OutlinedTextField(email.value, { email.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Correo") })
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                password.value,
                { password.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(18.dp))
            PrimaryAction("Ingresar", onClick = { navController.navigate(Route.Home.value) }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            SecondaryAction("Crear cuenta", onClick = { navController.navigate(Route.Register.value) }, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("Luego este flujo usara Firebase Auth", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(24.dp))
        ExchangeCard {
            listOf("Nombres", "Apellidos", "Correo", "Telefono", "Documento").forEach { label ->
                OutlinedTextField("", {}, modifier = Modifier.fillMaxWidth(), label = { Text(label) })
                Spacer(Modifier.height(10.dp))
            }
            PrimaryAction("Registrarme", onClick = { navController.navigate(Route.Home.value) }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            SecondaryAction("Ya tengo cuenta", onClick = { navController.navigate(Route.Login.value) }, modifier = Modifier.fillMaxWidth())
        }
    }
}
