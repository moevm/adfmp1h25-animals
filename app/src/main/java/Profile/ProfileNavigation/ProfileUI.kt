package Profile.ProfileNavigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.animals.ui.theme.*
import com.example.animals.R
import androidx.compose.runtime.Composable


@Composable
fun BottomNavigationBar(
    sections: List<String>,
    activeSection: String,
    onSectionSelected: (String) -> Unit
) {
    BottomNavigation(
        backgroundColor = Brown,
        modifier = Modifier.height(80.dp)
    ) {
        sections.forEach { section ->
            val isActive = section == activeSection
            val iconRes = when (section) {
                Section.Statistics.title -> if (isActive) R.drawable.statistics_beige else R.drawable.statistics_green
                Section.Posts.title -> if (isActive) R.drawable.pics_beige else R.drawable.pics_green
                Section.NewPost.title -> if (isActive) R.drawable.add_beige else R.drawable.add_green
                Section.Chats.title -> if (isActive) R.drawable.message_beige else R.drawable.message_green
                else -> R.drawable.statistics_beige
            }

            BottomNavigationItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = section,
                            modifier = Modifier.size(40.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                },
                selected = isActive,
                onClick = { onSectionSelected(section) },
                selectedContentColor = LightBeige,
                unselectedContentColor = LightGreen
            )
        }
    }
}



