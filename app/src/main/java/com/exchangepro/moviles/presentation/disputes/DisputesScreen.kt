package com.exchangepro.moviles.presentation.disputes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exchangepro.moviles.data.repository.MockExchangeRepository
import com.exchangepro.moviles.ui.components.ExchangeCard
import com.exchangepro.moviles.ui.components.PrimaryAction
import com.exchangepro.moviles.ui.components.StatusPill

@Composable
fun DisputesScreen() {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(MockExchangeRepository.disputes) { dispute ->
            ExchangeCard {
                Text(dispute.transactionCode, fontWeight = FontWeight.Bold)
                Text(dispute.reason)
                Spacer(Modifier.height(8.dp))
                StatusPill(dispute.status)
            }
        }
        item {
            ExchangeCard {
                Text("Abrir disputa", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                OutlinedTextField("", {}, modifier = Modifier.fillMaxWidth(), label = { Text("Codigo de transaccion") })
                Spacer(Modifier.height(10.dp))
                OutlinedTextField("", {}, modifier = Modifier.fillMaxWidth(), label = { Text("Motivo") })
                Spacer(Modifier.height(10.dp))
                PrimaryAction("Enviar disputa", {}, Modifier.fillMaxWidth())
            }
        }
    }
}
