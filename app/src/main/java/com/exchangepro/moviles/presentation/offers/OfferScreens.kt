package com.exchangepro.moviles.presentation.offers

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
import androidx.compose.material3.OutlinedTextField
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
import com.exchangepro.moviles.ui.components.SecondaryAction
import com.exchangepro.moviles.ui.components.StatusPill

@Composable
fun OffersScreen(navController: NavController) {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryAction("Nueva", { navController.navigate(Route.CreateOffer.value) }, Modifier.weight(1f))
                SecondaryAction("Mis ofertas", { navController.navigate(Route.MyOffers.value) }, Modifier.weight(1f))
            }
        }
        items(MockExchangeRepository.offers) { offer ->
            OfferCard(
                title = offer.userName,
                subtitle = "${offer.fromCurrency} por ${offer.toCurrency}",
                rate = offer.exchangeRate,
                amount = offer.offeredAmount,
                status = offer.operationType.name
            )
        }
    }
}

@Composable
fun MyOffersScreen() {
    val userId = MockExchangeRepository.currentUser.id
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(MockExchangeRepository.offers.filter { it.userId == userId }) { offer ->
            OfferCard(
                title = "Mi oferta",
                subtitle = "${offer.fromCurrency} por ${offer.toCurrency}",
                rate = offer.exchangeRate,
                amount = offer.offeredAmount,
                status = offer.status.name
            )
        }
    }
}

@Composable
fun CreateOfferScreen() {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Publicar oferta", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Los datos se guardaran luego en Firestore: offers/{offerId}", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            ExchangeCard {
                listOf("Tipo operacion", "Moneda entrega", "Moneda recibe", "Tasa cambio", "Monto ofertado", "Monto minimo").forEach { label ->
                    OutlinedTextField("", {}, modifier = Modifier.fillMaxWidth(), label = { Text(label) })
                    Spacer(Modifier.height(10.dp))
                }
                PrimaryAction("Publicar oferta", {}, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun OfferCard(title: String, subtitle: String, rate: Double, amount: Double, status: String) {
    ExchangeCard {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            StatusPill(status)
        }
        Spacer(Modifier.height(10.dp))
        Text("Tasa: %.2f".format(rate), style = MaterialTheme.typography.titleMedium)
        Text("Disponible: %.2f".format(amount), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
