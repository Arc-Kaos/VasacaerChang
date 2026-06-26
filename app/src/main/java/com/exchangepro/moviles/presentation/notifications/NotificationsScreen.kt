package com.exchangepro.moviles.presentation.notifications

import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun NotificationsScreen() {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(MockExchangeRepository.notifications) { item ->
            ExchangeCard {
                Text(item.title, fontWeight = FontWeight.Bold)
                Text(item.message, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(if (item.read) "Leida" else "Nueva", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
