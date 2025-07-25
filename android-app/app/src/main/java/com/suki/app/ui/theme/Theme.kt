package com.suki.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SukiLightColors = lightColorScheme(
    primary = Color(0xFFFF69B4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE5F1),
    onPrimaryContainer = Color(0xFF8B0040),
    secondary = Color(0xFF98FB98),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFE8F5E8),
    onSecondaryContainer = Color(0xFF2E7D32),
    tertiary = Color(0xFFFFD700),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFFFF8DC),
    onTertiaryContainer = Color(0xFF8B7500),
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

private val SukiDarkColors = darkColorScheme(
    primary = Color(0xFFFFB3DA),
    onPrimary = Color(0xFF5D1049),
    primaryContainer = Color(0xFF7D2D5F),
    onPrimaryContainer = Color(0xFFFFD8E8),
    secondary = Color(0xFFB8E6B8),
    onSecondary = Color(0xFF1F4A1F),
    secondaryContainer = Color(0xFF355F35),
    onSecondaryContainer = Color(0xFFD4F2D4),
    tertiary = Color(0xFFFFE066),
    onTertiary = Color(0xFF5D5900),
    tertiaryContainer = Color(0xFF7A7400),
    onTertiaryContainer = Color(0xFFFFEF7A),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun SukiTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        SukiDarkColors
    } else {
        SukiLightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
