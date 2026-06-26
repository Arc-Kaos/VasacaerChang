package com.exchangepro.moviles.presentation.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
fun TransactionsScreen() {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(MockExchangeRepository.transactions) { trx ->
            ExchangeCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(trx.code, fontWeight = FontWeight.Bold)
                        Text("${trx.buyerName} / ${trx.sellerName}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    StatusPill(trx.status.name)
                }
                Spacer(Modifier.height(10.dp))
                Text("Monto: %.2f ${trx.currency}".format(trx.operationAmount))
                Text("Total a pagar: S/ %.2f".format(trx.totalToPay), color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(10.dp))
                PrimaryAction("Subir comprobante", {}, Modifier.fillMaxWidth())
            }
        }
    }
}
