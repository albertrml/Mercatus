package com.arml.mercatus.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val primaryLight = Color(0xFF1A237E)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFBBCDFA)
val onPrimaryContainerLight = Color(0xFF000D23)
val secondaryLight = Color(0xFF01579B)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFF64B5F6)
val onSecondaryContainerLight = Color(0xFF001945)
val tertiaryLight = Color(0xFFB79104)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFFBE186)
val onTertiaryContainerLight = Color(0xFF221B00)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)
val surfaceLight = Color(0xFFFFFFFF)
val onSurfaceLight = Color(0xFF100121)
val surfaceVariantLight = Color(0xFFDAE3F9)
val onSurfaceVariantLight = Color(0xFF010626)
val surfaceContainerLowLight = Color(0xFF89CAFD)    // Card Background
val surfaceContainerLight = Color(0xFFE6EEFD)      // Bottom Bar
val backgroundLight = Color(0xFFFFFFFF)
val onBackgroundLight = Color(0xFF100121)
val outlineLight = Color(0xFF010626)
val outlineVariantLight = Color(0xFF010418)


val primaryDark = Color(0xFFB0C4FF)
val onPrimaryDark = Color(0xFF001B3F)
val primaryContainerDark = Color(0xFF2D4099)
val onPrimaryContainerDark = Color(0xFFD9E2FF)
val secondaryDark = Color(0xFF90CAF9)
val onSecondaryDark = Color(0xFF002F62)
val secondaryContainerDark = Color(0xFF00497B)
val onSecondaryContainerDark = Color(0xFFCDE5FF)
val tertiaryDark = Color(0xFFE0C970)
val onTertiaryDark = Color(0xFF3A2E00)
val tertiaryContainerDark = Color(0xFF544500)
val onTertiaryContainerDark = Color(0xFFFDE089)
val errorDark = Color(0xFFBA1A1A)
val onErrorDark = Color(0xFFFFFFFF)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val surfaceDark = Color(0xFF111318)
val onSurfaceDark = Color(0xFFE2E2E9)
val surfaceVariantDark = Color(0xFF43474E)
val onSurfaceVariantDark = Color(0xFFC3C7CE)
val surfaceContainerLowDark = Color(0xFF2D2D2D)    // Card Background
val surfaceContainerDark = Color(0xFF1F1F1F)      // Bottom Bar
val backgroundDark = Color(0xFF111318)
val onBackgroundDark = Color(0xFFE2E2E9)
val outlineDark = Color(0xFF8D9199)
val outlineVariantDark = Color(0xFF43474E)

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerLow = surfaceContainerLowDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerLow = surfaceContainerLowLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight
)

internal fun getColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) DarkColorScheme else LightColorScheme
}