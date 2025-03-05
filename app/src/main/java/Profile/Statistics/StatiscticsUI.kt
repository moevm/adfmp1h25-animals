package Profile.Statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.animals.ui.theme.DarkGreen
import com.example.animals.ui.theme.LightBeige
import com.example.animals.ui.theme.LightGreen
import com.example.animals.ui.theme.SemiBoldGreen


@Composable
fun SegmentedPicker(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,

    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(50))
            .background(LightGreen),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option == selectedOption
            val isFirst = index == 0
            val isLast = index == options.lastIndex

            Button(
                onClick = { onOptionSelected(option) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSelected) LightBeige else Color.Transparent,
                    contentColor = if (isSelected) LightBeige else DarkGreen
                ),
                shape = RoundedCornerShape(50),
                elevation = ButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = if (isSelected && isFirst) 4.dp else 0.dp,
                        end = if (isSelected && isLast) 4.dp else 0.dp
                    )
            ) {
                Text(
                    text = option,
                    style = SemiBoldGreen,
                )
            }
        }
    }
}
