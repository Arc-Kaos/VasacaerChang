package com.exchangepro.moviles.presentation.home

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
import androidx.navigation.NavController
import com.exchangepro.moviles.data.repository.MockExchangeRepository
import com.exchangepro.moviles.presentation.navigation.Route
import com.exchangepro.moviles.ui.components.ExchangeCard
import com.exchangepro.moviles.ui.components.PrimaryAction
import com.exchangepro.moviles.ui.components.SectionHeader
import com.exchangepro.moviles.ui.components.StatCard
import com.exchangepro.moviles.ui.components.StatusPill

@Composable
fun HomeScreen(navController: NavController) {
    val user = MockExchangeRepository.currentUser
    val wallet = MockExchangeRepository.wallet
    val totalPen = wallet.balances.sumOf { if (it.currency.name == "PEN") it.available else it.available * 3.72 }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Hola, ${user.fullName.split(" ").first()}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Gestiona tus intercambios P2P desde un solo lugar", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Saldo wallet", "S/ %.2f".format(totalPen), "${wallet.balances.size} monedas", Modifier.weight(1f))
                StatCard("Ofertas activas", MockExchangeRepository.offers.size.toString(), "En mercado", Modifier.weight(1f))
            }
        }
        item {
            PrimaryAction("Crear oferta", onClick = { navController.navigate(Route.CreateOffer.value) }, modifier = Modifier.fillMaxWidth())
        }
        item { SectionHeader("Ultimas ofertas") }
        items(MockExchangeRepository.offers.take(3)) { offer ->
            ExchangeCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(offer.userName, fontWeight = FontWeight.SemiBold)
                        Text("${offer.fromCurrency} -> ${offer.toCurrency}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    StatusPill(offer.operationType.name)
                }
                Spacer(Modifier.height(8.dp))
                Text("Tasa %.2f - Monto %.2f".format(offer.exchangeRate, offer.offeredAmount))
            }
        }
        item { SectionHeader("Actividad reciente") }
        items(MockExchangeRepository.transactions) { trx ->
            ExchangeCard {
                Text(trx.code, fontWeight = FontWeight.SemiBold)
                Text("${trx.status} - ${trx.operationAmount} ${trx.currency}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
