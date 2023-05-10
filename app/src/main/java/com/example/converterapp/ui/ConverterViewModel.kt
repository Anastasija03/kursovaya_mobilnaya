package com.example.converterapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.truncate

class ConverterViewModel : ViewModel() {
    val inputState: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    val answerState: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    val uiState: MutableLiveData<ConverterUiState> by lazy {
        MutableLiveData<ConverterUiState>(ConverterUiState())
    }

    fun convertUnits() {

        if (inputState.value === "") return

        val sourceUnit: String = uiState.value!!.fromUnit
        val targetUnit: String = uiState.value!!.toUnit
        val value: Float = inputState.value!!.toFloat()

        var convertResult = when {
            sourceUnit.equals("inch", ignoreCase = true) -> {
                when {
                    targetUnit.equals("cm", ignoreCase = true) -> value * 2.54
                    targetUnit.equals("foot", ignoreCase = true) -> value / 12.0
                    targetUnit.equals("meter", ignoreCase = true) -> value * 0.0254
                    targetUnit.equals("mile", ignoreCase = true) -> value / 63360.0
                    targetUnit.equals("km", ignoreCase = true) -> value * 0.0000254
                    targetUnit.equals("yard", ignoreCase = true) -> value / 36.0
                    else -> {
                        println("Error: Unsupported conversion.")
                        Double.NaN
                    }
                }
            }
            sourceUnit.equals("cm", ignoreCase = true) -> {
                when {
                    targetUnit.equals("inch", ignoreCase = true) -> value / 2.54
                    targetUnit.equals("foot", ignoreCase = true) -> value / 30.48
                    targetUnit.equals("meter", ignoreCase = true) -> value / 100.0
                    targetUnit.equals("mile", ignoreCase = true) -> value / 160934.0
                    targetUnit.equals("km", ignoreCase = true) -> value / 100000.0
                    targetUnit.equals("yard", ignoreCase = true) -> value / 91.44
                    else -> {
                        println("Error: Unsupported conversion.")
                        Double.NaN
                    }
                }
            }
            sourceUnit.equals("foot", ignoreCase = true) -> {
                when {
                    targetUnit.equals("inch", ignoreCase = true) -> value * 12.0
                    targetUnit.equals("cm", ignoreCase = true) -> value * 30.48
                    targetUnit.equals("meter", ignoreCase = true) -> value * 0.3048
                    targetUnit.equals("mile", ignoreCase = true) -> value / 5280.0
                    targetUnit.equals("km", ignoreCase = true) -> value * 0.0003048
                    targetUnit.equals("yard", ignoreCase = true) -> value / 3.0
                    else -> {
                        println("Error: Unsupported conversion.")
                        Double.NaN
                    }
                }
            }
            sourceUnit.equals("meter", ignoreCase = true) -> {
                when {
                    targetUnit.equals("inch", ignoreCase = true) -> value * 39.37
                    targetUnit.equals("cm", ignoreCase = true) -> value * 100.0
                    targetUnit.equals("foot", ignoreCase = true) -> value * 3.28084
                    targetUnit.equals("mile", ignoreCase = true) -> value / 1609.34
                    targetUnit.equals("km", ignoreCase = true) -> value / 1000.0
                    targetUnit.equals("yard", ignoreCase = true) -> value * 1.09361
                    else -> {
                        println("Error: Unsupported conversion.")
                        Double.NaN
                    }
                }
            }
            sourceUnit.equals("mile", ignoreCase = true) -> {
                when {
                    targetUnit.equals("inch", ignoreCase = true) -> value * 63360.0
                    targetUnit.equals("cm", ignoreCase = true) -> value * 160934.0
                    targetUnit.equals("foot", ignoreCase = true) -> value * 5280.0
                    targetUnit.equals("meter", ignoreCase = true) -> value * 1609.34
                    targetUnit.equals("km", ignoreCase = true) -> value * 1.60934
                    targetUnit.equals("yard", ignoreCase = true) -> value * 1760.0
                    else -> {
                        println("Error: Unsupported conversion.")
                        Double.NaN
                    }
                }
            }
            sourceUnit.equals("km", ignoreCase = true) -> {
                when {
                    targetUnit.equals("inch", ignoreCase = true) -> value * 39370.1
                    targetUnit.equals("cm", ignoreCase = true) -> value * 100000.0
                    targetUnit.equals("foot", ignoreCase = true) -> value * 3280.84
                    targetUnit.equals("meter", ignoreCase = true) -> value * 1000.0
                    targetUnit.equals("mile", ignoreCase = true) -> value / 1.60934
                    targetUnit.equals("yard", ignoreCase = true) -> value * 1093.61
                    else -> {
                        println("Error: Unsupported conversion.")
                        Double.NaN
                    }
                }
            }
            sourceUnit.equals("yard", ignoreCase = true) -> {
                when {
                    targetUnit.equals("inch", ignoreCase = true) -> value * 36.0
                    targetUnit.equals("cm", ignoreCase = true) -> value * 91.44
                    targetUnit.equals("foot", ignoreCase = true) -> value * 3.0
                    targetUnit.equals("meter", ignoreCase = true) -> value * 0.9144
                    targetUnit.equals("mile", ignoreCase = true) -> value / 1760.0
                    targetUnit.equals("km", ignoreCase = true) -> value * 0.0009144
                    else -> {
                        println("Error: Unsupported conversion.")
                        Double.NaN
                    }
                }
            }
            else -> {
                println("Error: Unsupported conversion.")
                Double.NaN
            }
        }


        uiState.value = ConverterUiState(
            fromUnit = uiState.value!!.fromUnit,
            options = uiState.value!!.options,
            toUnit = uiState.value!!.toUnit,
            result = "${if (convertResult.isNaN()) "Ошибка: неверные единицы измерения" else (convertResult * 100.0).roundToInt() / 100.0} ${if (convertResult.isNaN()) "" else "(${uiState.value!!.toUnit}"})")
    }

    fun setAnswer(text: String) {
        answerState.value = text
    }

    fun setToUnit(value: String) {
        uiState.value = ConverterUiState(fromUnit = uiState.value!!.fromUnit, options = uiState.value!!.options, toUnit = value, result = uiState.value!!.result)
    }

    fun setFromUnit(value: String) {
        uiState.value = ConverterUiState(toUnit = uiState.value!!.toUnit, options = uiState.value!!.options, fromUnit = value, result = uiState.value!!.result)
    }

    fun setInput(text: String) {
        inputState.value = text
    }
}