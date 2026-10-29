package com.mmc.lab04_mm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = MielClaro,
    onPrimary = MarronOscuro,
    secondary = VerdeClaro,
    onSecondary = MarronOscuro,
    background = MarronOscuro,
    onBackground = Crema,
    surface = MarronOscuro,
    onSurface = Crema
)

private val LightColorScheme = lightColorScheme(
    primary = Miel,
    onPrimary = MarronOscuro,
    secondary = VerdeOliva,
    onSecondary = Blanco,
    background = Crema,
    onBackground = MarronOscuro,
    surface = Crema,
    onSurface = MarronOscuro,
    tertiary = VerdeOliva
)

@Composable
fun Lab04_mmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}