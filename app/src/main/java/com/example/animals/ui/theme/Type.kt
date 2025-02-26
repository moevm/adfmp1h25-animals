package com.example.animals.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.animals.R

val Manrope = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold, FontWeight.Bold),
    Font(R.font.manrope_bold, FontWeight.ExtraBold)
)

// Устанавливаем Manrope как основной шрифт
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

)
val ButtonsExtraBold = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 19.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.5.sp
)

val InputMediumBrown = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.Medium,
    color = Brown,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.15.sp
)

val InputMediumGreen = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.Medium,
    color = DarkGreen,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.15.sp
)

val SemiBoldGreen = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.SemiBold,
    color = DarkGreen,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.15.sp
)

val ExtraBoldGreen = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 30.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.5.sp,
    color = DarkGreen
)

val ExtraBoldBrown = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 30.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.5.sp,
    color = Brown
)

val ExtraBoldLightBeige = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.5.sp,
    color = LightBeige
)

val BoldGreen = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.5.sp,
    color = DarkGreen
)

val NormalLightBeige = TextStyle(
    fontFamily = Manrope,
    fontWeight = FontWeight.Normal,
    color = LightBeige,
    fontSize = 15.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.15.sp
)