package data

import android.net.Uri
import com.example.animals.R

sealed class ImageSource {
    data class Drawable(val id: Int) : ImageSource()
    data class UriSource(val uri: Uri) : ImageSource()
}

data class AnimalType(
    val name: String,
    val mainImage: ImageSource,
    val repostCount: Int,
    val date: String,
    val time: String,
    val author: String,
    val location: String,
    val category: String,
    val size: String,
    val geoLocation: List<Double>,
    val description: String,
    val images: List<ImageSource>
)


val animalList = mutableListOf(
    AnimalType(
        name = "Малиновка",
        mainImage = ImageSource.Drawable(R.drawable.robin),
        repostCount = 10,
        date = "14.02.2025",
        time = "15:10",
        author = "Алексей",
        location = "Парк",
        category = "Птицы",
        size = "Маленький",
        geoLocation = listOf(55.751244, 37.618423),
        description = "Малиновка — небольшая, но очень заметная птица с ярко-оранжевой грудкой. Часто встречается в парках и садах, где радует людей своим звонким пением.",
        images = listOf(ImageSource.Drawable(R.drawable.robin),
            ImageSource.Drawable(R.drawable.robin2),
            ImageSource.Drawable(R.drawable.robin3))
    ),
    AnimalType(
        name = "Синица",
        mainImage = ImageSource.Drawable(R.drawable.tit),
        repostCount = 5,
        date = "15.02.2025",
        time = "12:00",
        author = "Максим",
        location = "Лес/Роща",
        category = "Птицы",
        size = "Маленький",
        geoLocation = listOf(102.751244, 37.618423),
        description = "Синица — активная и любознательная птица, известная своей ярко-жёлтой грудкой и черной «маской» на голове. Любит леса и парки, часто прилетает к кормушкам.",
        images = listOf(ImageSource.Drawable(R.drawable.tit),
            ImageSource.Drawable(R.drawable.tit2),
            ImageSource.Drawable(R.drawable.tit3))
    ),
    AnimalType(
        name = "Голубь",
        mainImage = ImageSource.Drawable(R.drawable.pigeon),
        repostCount = 8,
        date = "16.02.2025",
        time = "01:10",
        author = "Федор",
        location = "Парк",
        category = "Птицы",
        size = "Средний",
        geoLocation = listOf(45.751244, 37.618423),
        description = "Голуби — одни из самых распространенных птиц в городах. Умные и социальные, они легко привыкают к людям и часто ищут еду в оживленных местах.",
        images = listOf(ImageSource.Drawable(R.drawable.pigeon),
            ImageSource.Drawable(R.drawable.pigeon2),
            ImageSource.Drawable(R.drawable.pigeon3))
    ),
    AnimalType(
        name = "Лиса",
        mainImage = ImageSource.Drawable(R.drawable.fox),
        repostCount = 12,
        date = "17.02.2025",
        time = "19:23",
        author = "Лилия",
        location = "Лес/Роща",
        category = "Млекопитающие",
        size = "Большой",
        geoLocation = listOf(15.751244, 37.618423),
        description = "Лиса — грациозный и хитрый хищник, обитающий в лесах и полях. Известна своим рыжим мехом и пушистым хвостом. Очень умна и осторожна.",
        images = listOf(ImageSource.Drawable(R.drawable.fox),
            ImageSource.Drawable(R.drawable.fox2),
            ImageSource.Drawable(R.drawable.fox3))
    ),
    AnimalType(
        name = "Черепаха",
        mainImage = ImageSource.Drawable(R.drawable.turtle),
        repostCount = 3,
        date = "28.02.2025",
        time = "22:10",
        author = "Алексей",
        location = "Водоем",
        category = "Рептилии",
        size = "Средний",
        geoLocation = listOf(55.751244, 37.618423),
        description = "Черепахи — древние и выносливые рептилии, обитающие как в воде, так и на суше. Их панцирь надежно защищает от хищников, а их спокойный характер завораживает.",
        images = listOf(ImageSource.Drawable(R.drawable.turtle),
            ImageSource.Drawable(R.drawable.turtle2),
            ImageSource.Drawable(R.drawable.turtle3))
    )
)
