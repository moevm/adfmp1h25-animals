package AnimalsFilter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.ExtraBoldGreen
import com.example.animals.ui.theme.InputMediumGreen
import com.example.animals.ui.theme.LightBeige
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable


@Composable
fun CustomRoundCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(if (checked) DarkGreen else Color.Transparent)
            .border(BorderStroke(2.dp, DarkGreen), CircleShape)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Выбран",
                tint = LightBeige,
                modifier = Modifier.size(16.dp)
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
                    .padding(vertical = 8.dp)
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
