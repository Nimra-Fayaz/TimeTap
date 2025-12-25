package com.example.timecalculator.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

// Mustard and Black Color Palette
val MustardYellow = Color(0xFFE5B80B)
val DarkMustard = Color(0xFFC49B0B)
val LightMustard = Color(0xFFFFC107)
val PaleMustard = Color(0xFFFFF8DC)
val DeepBlack = Color(0xFF0A0A0A)
val SoftBlack = Color(0xFF1A1A1A)
val CharcoalGray = Color(0xFF2A2A2A)
val LightGray = Color(0xFFE0E0E0)
val OffWhite = Color(0xFFFAFAFA)

private val LightColorScheme = lightColorScheme(
    primary = MustardYellow,
    onPrimary = DeepBlack,
    primaryContainer = PaleMustard,
    onPrimaryContainer = DeepBlack,
    secondary = DarkMustard,
    onSecondary = Color.White,
    secondaryContainer = LightMustard,
    onSecondaryContainer = DeepBlack,
    tertiary = CharcoalGray,
    onTertiary = Color.White,
    background = OffWhite,
    onBackground = DeepBlack,
    surface = Color.White,
    onSurface = DeepBlack,
    surfaceVariant = PaleMustard,
    onSurfaceVariant = DeepBlack,
    error = Color(0xFFB3261E),
    onError = Color.White,
)

private val DarkColorScheme = darkColorScheme(
    primary = MustardYellow,
    onPrimary = DeepBlack,
    primaryContainer = DarkMustard,
    onPrimaryContainer = Color.White,
    secondary = LightMustard,
    onSecondary = DeepBlack,
    secondaryContainer = DarkMustard,
    onSecondaryContainer = Color.White,
    tertiary = LightGray,
    onTertiary = DeepBlack,
    background = DeepBlack,
    onBackground = LightGray,
    surface = SoftBlack,
    onSurface = LightGray,
    surfaceVariant = CharcoalGray,
    onSurfaceVariant = LightGray,
    error = Color(0xFFF2B8B5),
    onError = DeepBlack,
)

// Custom Typography with Bold Headings
private val AppTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
)

@Composable
fun TimeCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled to use custom colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
