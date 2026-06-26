package com.exchangepro.moviles.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exchangepro.moviles.data.repository.MockExchangeRepository
import com.exchangepro.moviles.ui.components.ExchangeCard
import com.exchangepro.moviles.ui.components.PrimaryAction
import com.exchangepro.moviles.ui.components.StatCard

@Composable
fun ProfileScreen() {
    val user = MockExchangeRepository.currentUser
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            ExchangeCard {
                Text(user.fullName, fontWeight = FontWeight.Bold)
                Text(user.email)
                Text("DNI ${user.documentNumber}")
                Spacer(Modifier.height(10.dp))
                StatCard("Reputacion", "%.1f".format(user.reputation), "${user.totalRatings} calificaciones")
            }
        }
        item {
            ExchangeCard {
                listOf(user.fullName, user.phone, user.email).forEachIndexed { index, value ->
                    val label = listOf("Nombre completo", "Telefono", "Correo")[index]
                    OutlinedTextField(value, {}, modifier = Modifier.fillMaxWidth(), label = { Text(label) })
                    Spacer(Modifier.height(10.dp))
                }
                PrimaryAction("Actualizar perfil", {}, Modifier.fillMaxWidth())
            }
        }
    }
}
