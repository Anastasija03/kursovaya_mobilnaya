package com.example.converterapp.ui

import androidx.compose.foundation.text.KeyboardOptions

data class ConverterUiState(
    val fromUnit: String = "inch",
    val toUnit: String = "cm",
    val options: List<String> = listOf("inch", "cm", "foot","meter","yard","mile", "km"),
    val result: String = ""
)