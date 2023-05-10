package com.example.converterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.converterapp.ui.ConverterViewModel
import com.example.converterapp.ui.theme.ConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PreviewConversionScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConversionScreen(converterVM: ConverterViewModel = viewModel()) {
    val inputState by converterVM.inputState.observeAsState()
    val uiState by converterVM.uiState.observeAsState()

    val rowModifier: Modifier = Modifier.padding(top = 16.dp)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row(modifier = rowModifier) {
            SpinnerList(options = uiState!!.options, selectedOptionText = uiState!!.fromUnit, onItemSelect = {
                converterVM.setFromUnit(it)
                    }, label = "Из единицы измерения")
                }

        Row(modifier = rowModifier) {
            OutlinedTextField(
                label = { Text(text = "Число для конвертации") },
                value = inputState.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    converterVM.setInput(it)
                })
        }
        Row(modifier = rowModifier.padding(top = 10.dp)) {
            SpinnerList(options = uiState!!.options, selectedOptionText = uiState!!.toUnit, onItemSelect = {
                        converterVM.setToUnit(it)
                    }, label = "В единицу измерения")
                }

                Row(modifier = rowModifier) {
                    Button(
                        onClick = { converterVM.convertUnits() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp),
                    ) {
                        Text(text = "=", fontSize = 128.sp, modifier = Modifier.padding(bottom = 12.dp))
                    }
                }
                
                Row(modifier = rowModifier) {
                    Text(text = uiState!!.result, fontSize = 32.sp)
                }
            }

}

@Preview
@Composable
fun PreviewConversionScreen() {
    ConversionScreen()
}

@OptIn(ExperimentalMaterialApi::class) // Без этой штуки не работает ExposedDropdownMenuBox, что-то там про тестовые функции
@Composable
fun SpinnerList(options: List<String>, selectedOptionText: String, onItemSelect: (String) -> Unit, label: String) { // Выпадающий список (select как в HTML)
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelect(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}