package com.exchangepro.moviles.presentation.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exchangepro.moviles.data.repository.MockExchangeRepository
import com.exchangepro.moviles.domain.model.CurrencyCode
import com.exchangepro.moviles.domain.model.WalletBalance
import com.exchangepro.moviles.ui.components.ExchangeCard
import com.exchangepro.moviles.ui.components.PrimaryAction
import com.exchangepro.moviles.ui.components.SecondaryAction

@Composable
fun WalletScreen() {
    val balances = remember {
        mutableStateListOf<WalletBalance>().apply {
            addAll(MockExchangeRepository.wallet.balances)
        }
    }
    val activeAction = remember { mutableStateOf<WalletAction?>(null) }

    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(balances, key = { it.currency.name }) { balance ->
            ExchangeCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(balance.currency.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Text("%.2f".format(balance.available), fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(6.dp))
                Text("Retenido: %.2f".format(balance.retained), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryAction("Recargar", { activeAction.value = WalletAction.TOP_UP }, Modifier.weight(1f))
                SecondaryAction("Retirar", { activeAction.value = WalletAction.WITHDRAW }, Modifier.weight(1f))
            }
        }
    }

    activeAction.value?.let { action ->
        WalletOperationDialog(
            action = action,
            onDismiss = { activeAction.value = null },
            onConfirm = { currency, amount ->
                val index = balances.indexOfFirst { it.currency == currency }
                if (index >= 0) {
                    val current = balances[index]
                    val nextAvailable = when (action) {
                        WalletAction.TOP_UP -> current.available + amount
                        WalletAction.WITHDRAW -> (current.available - amount).coerceAtLeast(0.0)
                    }
                    balances[index] = current.copy(available = nextAvailable)
                }
                activeAction.value = null
            }
        )
    }
}

private enum class WalletAction {
    TOP_UP,
    WITHDRAW
}

@Composable
private fun WalletOperationDialog(
    action: WalletAction,
    onDismiss: () -> Unit,
    onConfirm: (CurrencyCode, Double) -> Unit
) {
    val amountText = remember { mutableStateOf("") }
    val currencyText = remember { mutableStateOf(CurrencyCode.PEN.name) }
    val title = if (action == WalletAction.TOP_UP) "Recargar wallet" else "Retirar saldo"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = currencyText.value,
                    onValueChange = { currencyText.value = it.uppercase() },
                    label = { Text("Moneda: PEN o USD") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = amountText.value,
                    onValueChange = { amountText.value = it },
                    label = { Text("Monto") },
                    singleLine = true
                )
                Text(
                    "Temporal: esta operacion solo cambia el saldo en pantalla. Luego ira a Firestore.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val currency = runCatching { CurrencyCode.valueOf(currencyText.value.trim().uppercase()) }.getOrNull()
                    val amount = amountText.value.toDoubleOrNull()
                    if (currency != null && amount != null && amount > 0) {
                        onConfirm(currency, amount)
                    }
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
