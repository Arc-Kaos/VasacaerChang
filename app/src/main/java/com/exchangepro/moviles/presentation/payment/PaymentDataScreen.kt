package com.exchangepro.moviles.presentation.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exchangepro.moviles.data.repository.MockExchangeRepository
import com.exchangepro.moviles.ui.components.ExchangeCard
import com.exchangepro.moviles.ui.components.PrimaryAction

@Composable
fun PaymentDataScreen() {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(MockExchangeRepository.paymentData) { payment ->
            ExchangeCard {
                Text(payment.alias, fontWeight = FontWeight.Bold)
                Text(payment.methodName, color = MaterialTheme.colorScheme.primary)
                Text(payment.bankName ?: "Billetera digital", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(payment.accountNumber ?: "", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            ExchangeCard {
                Text("Agregar metodo", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                listOf("Metodo", "Banco", "Cuenta o telefono", "Alias").forEach { label ->
                    OutlinedTextField("", {}, modifier = Modifier.fillMaxWidth(), label = { Text(label) })
                    Spacer(Modifier.height(10.dp))
                }
                PrimaryAction("Guardar", {}, Modifier.fillMaxWidth())
            }
        }
    }
}
