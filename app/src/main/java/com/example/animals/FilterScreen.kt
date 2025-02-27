package com.example.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.Brown
import com.example.animals.ui.theme.ButtonsExtraBold
import com.example.animals.ui.theme.DarkBeige
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.ExtraBoldGreen
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import com.example.animals.ui.theme.NormalLightBeige
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

import androidx.compose.material3.Icon
//import androidx.compose.material3.icons.Icons
//import androidx.compose.material3.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun FilterScreen(
    onCancel: () -> Unit = {},
    onApply: () -> Unit = {}
) {
    // Состояния для чекбоксов
    val animalTypes = listOf("Птицы", "Млекопитающие", "Рептилии", "Земноводные")
    val sizes = listOf("Маленький", "Средний", "Большой")
    val locations = listOf("Парк", "Лес/Роща", "Водоем", "Двор/Крыша")

    val animalTypeState = remember { mutableStateMapOf<String, Boolean>().apply { animalTypes.forEach { put(it, false) } } }
    val sizeState = remember { mutableStateMapOf<String, Boolean>().apply { sizes.forEach { put(it, false) } } }
    val locationState = remember { mutableStateMapOf<String, Boolean>().apply { locations.forEach { put(it, false) } } }

    val insets = WindowInsets.systemBars
    val topInset = with(LocalDensity.current) {
        insets.getTop(this).toDp()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBeige)
    ) {
        // Используем LazyColumn для добавления прокрутки
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .padding(top = topInset)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(48.dp)) // Пустое место для симметрии
                        Text(
                            text = "Фильтры",
                            style = ExtraBoldGreen,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        TextButton(
                            onClick = onCancel,
                            modifier = Modifier.height(40.dp),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = Brown, // Коричневый цвет (Brown)
                                contentColor = LightBeige // Цвет текста
                            )
                        ) {
                            Text("Отмена", style = NormalLightBeige)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Группа чекбоксов для типа животных
            item {
                FilterCategory(title = "Тип животных", options = animalTypeState)
            }

            // Группа чекбоксов для размера
            item {
                FilterCategory(title = "Размер", options = sizeState)
            }

            // Группа чекбоксов для места находки
            item {
                FilterCategory(title = "Место находки", options = locationState)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Кнопка "Применить"
            item {
                Button(
                    onClick = onApply, // Переход на CatalogScreen
                    modifier = Modifier
                        .padding(17.dp)
                        .fillMaxWidth(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
                ) {
                    Text(
                        text = "Применить",
                        style = ButtonsExtraBold,
                        fontWeight = FontWeight.ExtraBold,
                        color = LightBeige,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomRoundCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(20.dp) // Размер чекбокса
            .clip(CircleShape) // Круглая форма
            .background(if (checked) DarkGreen else Color.Transparent) // Заливка при выборе
            .border(BorderStroke(2.dp, DarkGreen), CircleShape) // Граница
            .clickable { onCheckedChange(!checked) }, // Обработка клика
        contentAlignment = Alignment.Center // Центрирование иконки
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = LightBeige, // Цвет галочки
                modifier = Modifier.size(16.dp) // Размер галочки
            )
        }
    }
}

@Composable
fun FilterCategory(title: String, options: MutableMap<String, Boolean>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = ExtraBoldGreen,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        options.forEach { (option, isChecked) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Добавляем вертикальный отступ между опциями
                    .toggleable(
                        value = isChecked,
                        onValueChange = { options[option] = it }
                    )
            ) {
                CustomRoundCheckbox(
                    checked = isChecked,
                    onCheckedChange = { options[option] = it }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp),
                    style = InputMediumGreen,
                    fontSize = 18.sp
                )
            }
        }
    }
}
