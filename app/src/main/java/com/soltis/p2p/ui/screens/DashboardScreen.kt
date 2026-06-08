package com.soltis.p2p.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soltis.p2p.ui.components.P2PSectionCard
import com.soltis.p2p.ui.components.BadgeChip
import com.soltis.p2p.ui.theme.*

// ── Data ──────────────────────────────────────────────────────────────────────

data class CurrencyBalance(
    val flag: String,
    val code: String,
    val name: String,
    val amount: String,
    val retained: String,
    val buyRate: String,
    val sellRate: String
)

data class RecentMovement(
    val icon: ImageVector,
    val iconColor: Color,
    val type: String,
    val detail: String,
    val date: String,
    val amount: String,
    val isPositive: Boolean
)

// ── Screen ────────────────────────────────────────────────────────────────────

@Composable
fun DashboardScreen(
    onNavigateToDeposit: () -> Unit,
    onNavigateToMovements: () -> Unit,
    onNavigateToPublish: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(3) } // 3 = Wallet por defecto

    val tabs = listOf("Inicio", "Mercado", "Ofertas", "Wallet", "Perfil")
    val tabIcons = listOf(
        Icons.Default.Home,
        Icons.Default.BarChart,
        Icons.Default.LocalOffer,
        Icons.Default.AccountBalanceWallet,
        Icons.Default.Person
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 4.dp
            ) {
                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            when (index) {
                                2 -> onNavigateToPublish()
                                else -> {}
                            }
                        },
                        icon = {
                            Icon(tabIcons[index], contentDescription = label)
                        },
                        label = { Text(label, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor   = YellowPrimary,
                            selectedTextColor   = YellowPrimary,
                            unselectedIconColor = Color(0xFFBDBDBD),
                            unselectedTextColor = Color(0xFFBDBDBD),
                            indicatorColor      = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF7F7F7))
        ) {
            // ── Dark header ───────────────────────────────────────────────────
            WalletHeader(
                onDeposit    = onNavigateToDeposit,
                onWithdraw   = { /* TODO */ },
                onTransfer   = { /* TODO */ },
                onProfile    = { /* TODO */ }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── Currencies ────────────────────────────────────────────────────
            CurrenciesSection()

            Spacer(modifier = Modifier.height(12.dp))

            // ── Recent movements ──────────────────────────────────────────────
            RecentMovementsSection(onVerTodos = onNavigateToMovements)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ── Dark header composable ────────────────────────────────────────────────────

@Composable
fun WalletHeader(
    onDeposit: () -> Unit,
    onWithdraw: () -> Unit,
    onTransfer: () -> Unit,
    onProfile: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = DarkBg,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .padding(horizontal = 20.dp)
            .padding(top = 52.dp, bottom = 28.dp)
    ) {
        Column {
            // Top row: profile + title + language
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile button
                IconButton(
                    onClick = onProfile,
                    modifier = Modifier
                        .size(40.dp)
                        .background(DarkSurface, CircleShape)
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Perfil",
                        tint = Color.White, modifier = Modifier.size(20.dp))
                }

                // Title centered
                Text(
                    text = "Wallet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                // Language pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = DarkSurface,
                    modifier = Modifier.clickable { }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🌐 ES", fontSize = 12.sp, color = Color.White)
                        Icon(Icons.Default.KeyboardArrowDown, null,
                            modifier = Modifier.size(14.dp), tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Balance
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Saldo total estimado 👁",
                        fontSize = 13.sp,
                        color = Color(0xFFAAAAAA)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "S/ 12,456.78",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "≈ \$3,201.06 USD",
                        fontSize = 13.sp,
                        color = Color(0xFFAAAAAA)
                    )
                }

                // Coins icon placeholder
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(DarkSurface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("💰", fontSize = 28.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton("Depositar",  Icons.Default.Add,            onDeposit)
                ActionButton("Retirar",    Icons.Default.ArrowUpward,    onWithdraw)
                ActionButton("Transferir", Icons.Default.SwapHoriz,      onTransfer)
            }
        }
    }
}

@Composable
fun ActionButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(DarkSurface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label,
                tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, fontSize = 12.sp, color = Color.White)
    }
}

// ── Currencies section ────────────────────────────────────────────────────────

@Composable
fun CurrenciesSection() {
    val currencies = listOf(
        CurrencyBalance("🇵🇪", "PEN", "Sol peruano",          "S/ 8,240.50", "S/ 390.00",  "S/ 7,850.50", "S/ 390.00"),
        CurrencyBalance("🇺🇸", "USD", "Dólar estadounidense", "$ 2,150.00",  "$ 50.00",    "$ 2,100.00",  "$ 50.00"),
        CurrencyBalance("🇪🇺", "EUR", "Euro",                 "€ 66.28",     "€ 6.00",     "€ 60.28",     "€ 6.00")
    )

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            currencies.forEachIndexed { index, currency ->
                CurrencyRow(currency = currency)
                if (index < currencies.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 68.dp),
                        color = Color(0xFFF0F0F0)
                    )
                }
            }
        }
    }
}

@Composable
fun CurrencyRow(currency: CurrencyBalance) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Flag
        Text(currency.flag, fontSize = 28.sp, modifier = Modifier.size(40.dp))

        Spacer(modifier = Modifier.width(12.dp))

        // Name + rates
        Column(modifier = Modifier.weight(1f)) {
            Text(currency.code, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(currency.name, fontSize = 12.sp, color = TextSecondary)
            Text("Compra: ${currency.buyRate}", fontSize = 11.sp, color = Color(0xFFAAAAAA))
            Text("Venta: ${currency.sellRate}", fontSize = 11.sp, color = Color(0xFFAAAAAA))
        }

        // Amount
        Column(horizontalAlignment = Alignment.End) {
            Text(currency.amount, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("Retenido: ${currency.retained}", fontSize = 11.sp, color = TextSecondary)
        }

        Spacer(modifier = Modifier.width(4.dp))

        Icon(Icons.Default.ChevronRight, contentDescription = null,
            tint = Color(0xFFCCCCCC), modifier = Modifier.size(20.dp))
    }
}

// ── Recent movements section ──────────────────────────────────────────────────

@Composable
fun RecentMovementsSection(onVerTodos: () -> Unit) {
    val movements = listOf(
        RecentMovement(Icons.Default.Add,        GreenPositive,  "Depósito",               "PEN - BCP",    "06 may 2024, 11:12", "+S/ 500.00",  true),
        RecentMovement(Icons.Default.ArrowUpward, RedNegative,   "Retiro",                 "USD - PayPal", "23 may 2024, 11:45", "-\$ 150.00",   false),
        RecentMovement(Icons.Default.SwapHoriz,  RedNegative,   "Transferencia enviada",  "PEN - Giuseppina", "22 may 2024, 12:18", "-S/ 300.00", false)
    )

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Movimientos recientes",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Ver todos",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = YellowPrimary,
                    modifier = Modifier.clickable { onVerTodos() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            movements.forEach { mov ->
                MovementRow(movement = mov)
                HorizontalDivider(color = Color(0xFFF5F5F5))
            }
        }
    }
}

@Composable
fun MovementRow(movement: RecentMovement) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFFFF3E0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(movement.icon, contentDescription = null,
                tint = movement.iconColor, modifier = Modifier.size(20.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Info
        Column(modifier = Modifier.weight(1f)) {
            Text(movement.type, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(movement.detail, fontSize = 11.sp, color = TextSecondary)
            Text(movement.date, fontSize = 11.sp, color = Color(0xFFAAAAAA))
        }

        // Amount
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = movement.amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (movement.isPositive) GreenPositive else RedNegative
            )
            Text(
                text = if (movement.isPositive) "Completado" else "Completado",
                fontSize = 11.sp,
                color = if (movement.isPositive) GreenPositive else RedNegative
            )
        }
    }
}
