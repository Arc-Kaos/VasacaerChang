package com.tuapp.p2pdivisas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Brand colors ──────────────────────────────────────────────────────────────
val YellowPrimary  = Color(0xFFF5A623)
val YellowDark     = Color(0xFFE0941A)
val YellowLight    = Color(0xFFFFF3E0)
val DarkBg         = Color(0xFF1A1A2E)
val DarkSurface    = Color(0xFF2A2A3E)
val TextPrimary    = Color(0xFF1A1A1A)
val TextSecondary  = Color(0xFF666666)
val TextHint       = Color(0xFFBDBDBD)
val StrokeDefault  = Color(0xFFE0E0E0)
val GreenPositive  = Color(0xFF27AE60)
val RedNegative    = Color(0xFFE74C3C)
val OrangeRetained = Color(0xFFE67E22)

private val P2PColorScheme = lightColorScheme(
    primary        = YellowPrimary,
    onPrimary      = Color.White,
    secondary      = DarkBg,
    onSecondary    = Color.White,
    background     = Color(0xFFF7F7F7),
    surface        = Color.White,
    onBackground   = TextPrimary,
    onSurface      = TextPrimary,
)

@Composable
fun P2PDivisasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = P2PColorScheme,
        content     = content
    )
}
