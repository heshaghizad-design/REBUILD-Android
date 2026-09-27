package com.rebuild.hamed

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val AppBg = Color(0xFFF6F8FB)
private val Surface = Color.White
private val Primary = Color(0xFF1685F8)
private val PrimarySoft = Color(0xFFEAF4FF)
private val Ink = Color(0xFF10213D)
private val Muted = Color(0xFF718096)
private val Success = Color(0xFF16B879)
private val SuccessSoft = Color(0xFFE9FBF4)
private val WarningSoft = Color(0xFFFFF7DB)
private val Danger = Color(0xFFF04438)
private val DangerSoft = Color(0xFFFFEEEC)
private val Border = Color(0xFFE8EDF3)

private val RebuildColors = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimarySoft,
    onPrimaryContainer = Ink,
    background = AppBg,
    surface = Surface,
    onSurface = Ink,
    outline = Border,
    error = Danger
)

data class Exercise(
    val id: String,
    val en: String,
    val fa: String,
    val sets: Int,
    val reps: String,
    val rest: Int,
    val cue: String,
    val safety: String,
    val animated: Boolean = false
)

data class Session(
    val id: String,
    val day: String,
    val title: String,
    val subtitle: String,
    val exercises: List<Exercise>
)

private val chest = Exercise(
    id = "chest_press",
    en = "Chest Press Machine",
    fa = "پرس سینه دستگاه",
    sets = 2,
    reps = "10–12",
    rest = 90,
    cue = "سر ثابت · شانه‌ها پایین · پرس کنترل‌شده",
    safety = "سر و پشت روی تکیه‌گاه بماند. اگر برای کامل کردن تکرار چانه جلو می‌آید یا گردن سفت می‌شود، وزنه را کم کن.",
    animated = true
)

private val seatedRow = Exercise(
    "seated_row",
    "Seated Row",
    "زیر بغل نشسته",
    2,
    "10–12",
    90,
    "سینه باز · آرنج‌ها عقب · حرکت بدون تاب",
    "گردن خنثی بماند و تنه را برای کشیدن وزنه عقب پرت نکن."
)

private val legPress = Exercise(
    "leg_press",
    "Seated Leg Press",
    "پرس پا",
    2,
    "10–12",
    90,
    "زانو هم‌جهت پنجه · فشار کنترل‌شده",
    "زانو را قفل نکن. دامنه را فقط تا جایی ادامه بده که زانوی چپ بدون درد بماند."
)

private val hamCurl = Exercise(
    "ham_curl",
    "Hamstring Curl",
    "پشت پا دستگاه",
    2,
    "10–12",
    75,
    "لگن ثابت · پشت ران کار کند",
    "برای بالا بردن وزنه گردن یا کمر را قوس نده."
)

private val gluteBridge = Exercise(
    "glute_bridge",
    "Glute Bridge",
    "پل باسن",
    2,
    "10–12",
    60,
    "فشار از باسن و پاشنه‌ها",
    "وزن روی شانه‌ها باشد، نه روی گردن."
)

private val deadBug = Exercise(
    "dead_bug",
    "Dead Bug",
    "ددباگ",
    2,
    "6 هر سمت",
    60,
    "آرام · تنه ثابت · دامنه کنترل‌شده",
    "اگر گردن منقبض می‌شود دامنه را کمتر کن."
)

private val calfRaise = Exercise(
    "calf_raise",
    "Calf Raise",
    "ساق پا",
    2,
    "12–15",
    60,
    "بالا و پایین رفتن آرام",
    "برای تعادل از تکیه‌گاه استفاده کن."
)

private val latPulldown = Exercise(
    "lat_pulldown",
    "Lat Pulldown — Front",
    "لت از جلو",
    2,
    "10–12",
    90,
    "میله جلوی سینه · سینه باز",
    "میله را پشت گردن نبر و چانه را جلو نده."
)

private val boxSquat = Exercise(
    "box_squat",
    "Sit-to-Stand / Box Squat",
    "نشستن و بلند شدن",
    2,
    "8–10",
    75,
    "لگن عقب · زانو هم‌جهت پنجه",
    "عمق فقط تا جایی که زانو درد نگیرد."
)

private val hipAbduction = Exercise(
    "hip_abduction",
    "Hip Abduction",
    "دورکننده ران",
    2,
    "12–15",
    60,
    "تنه ثابت · حرکت بدون ضربه",
    "دامنه را کنترل‌شده نگه دار."
)

private val birdDog = Exercise(
    "bird_dog",
    "Bird Dog",
    "برد داگ",
    2,
    "5 هر سمت",
    60,
    "بدن ثابت · دست و پای مخالف",
    "نگاه به زمین و گردن در امتداد ستون فقرات."
)

private val biceps = Exercise(
    "biceps",
    "Cable Biceps Curl",
    "جلو بازو سیم‌کش",
    2,
    "10–12",
    60,
    "آرنج کنار بدن · بدون تاب",
    "شانه‌ها را بالا نبر."
)

private val sessions = listOf(
    Session(
        "A",
        "شنبه",
        "Upper Body + Legs A",
        "پایه · عضله‌سازی کنترل‌شده",
        listOf(chest, seatedRow, legPress, hamCurl, gluteBridge, deadBug, calfRaise)
    ),
    Session(
        "B",
        "دوشنبه",
        "Upper Body + Control B",
        "پشت · وضعیت بدن · کنترل زانو",
        listOf(latPulldown, chest, boxSquat, hipAbduction, seatedRow, birdDog, biceps)
    ),
    Session(
        "C",
        "چهارشنبه",
        "Full Body C",
        "تمرین کامل بدن · تکنیک تمیز",
        listOf(legPress, hamCurl, chest, seatedRow, latPulldown, gluteBridge, deadBug)
    )
)

class RebuildStore(context: Context) {
    private val prefs =
        context.getSharedPreferences("rebuild_local", Context.MODE_PRIVATE)

    fun completed(sessionId: String): Boolean =
        prefs.getBoolean("done_$sessionId", false)

    fun setCompleted(sessionId: String, value: Boolean) =
        prefs.edit().putBoolean("done_$sessionId", value).apply()

    fun weight(exerciseId: String): String =
        prefs.getString("weight_$exerciseId", "") ?: ""

    fun reps(exerciseId: String): String =
        prefs.getString("reps_$exerciseId", "") ?: ""

    fun rir(exerciseId: String): String =
        prefs.getString("rir_$exerciseId", "2") ?: "2"

    fun setLog(
        exerciseId: String,
        weight: String,
        reps: String,
        rir: String
    ) = prefs.edit()
        .putString("weight_$exerciseId", weight)
        .putString("reps_$exerciseId", reps)
        .putString("rir_$exerciseId", rir)
        .apply()
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MaterialTheme(
                colorScheme = RebuildColors
            ) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Rtl
                ) {
                    RebuildApp()
                }
            }
        }
    }
}

enum class RootTab {
    Home,
    Program,
    Progress,
    Safety
}

@Composable
fun RebuildApp() {

    val context = LocalContext.current
    val store = remember { RebuildStore(context) }

    var tab by rememberSaveable {
        mutableStateOf(RootTab.Home)
    }

    var openSession by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var openExercise by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val allExercises = remember {
        sessions
            .flatMap { it.exercises }
            .associateBy { it.id }
    }

    Scaffold(
        containerColor = AppBg,
        bottomBar = {

            if (openExercise == null && openSession == null) {

                NavigationBar(
                    containerColor = Surface,
                    tonalElevation = 0.dp
                ) {

                    BottomItem(
                        RootTab.Home,
                        tab,
                        "خانه",
                        Icons.Default.Home
                    ) {
                        tab = RootTab.Home
                    }

                    BottomItem(
                        RootTab.Program,
                        tab,
                        "برنامه",
                        Icons.Default.CalendarMonth
                    ) {
                        tab = RootTab.Program
                    }

                    BottomItem(
                        RootTab.Progress,
                        tab,
                        "پیشرفت",
                        Icons.Default.BarChart
                    ) {
                        tab = RootTab.Progress
                    }

                    BottomItem(
                        RootTab.Safety,
                        tab,
                        "ایمنی",
                        Icons.Default.HealthAndSafety
                    ) {
                        tab = RootTab.Safety
                    }
                }
            }
        }
    ) { inner ->

        Box(
            Modifier.padding(inner)
        ) {

            when {

                openExercise != null -> {

                    ExerciseDetailScreen(
                        exercise = allExercises.getValue(openExercise!!),
                        store = store,
                        onBack = {
                            openExercise = null
                        }
                    )
                }

                openSession != null -> {

                    SessionScreen(
                        session = sessions.first {
                            it.id == openSession
                        },
                        store = store,
                        onBack = {
                            openSession = null
                        },
                        onExercise = {
                            openExercise = it.id
                        }
                    )
                }

                else -> {

                    AnimatedContent(
                        targetState = tab,
                        label = "root"
                    ) { selected ->

                        when (selected) {

                            RootTab.Home -> {
                                HomeScreen(
                                    store = store,
                                    start = {
                                        openSession = "A"
                                    },
                                    safety = {
                                        tab = RootTab.Safety
                                    }
                                )
                            }

                            RootTab.Program -> {
                                ProgramScreen(store) {
                                    openSession = it.id
                                }
                            }

                            RootTab.Progress -> {
                                ProgressScreen(store)
                            }

                            RootTab.Safety -> {
                                SafetyScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomItem(
    tab: RootTab,
    selected: RootTab,
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    NavigationBarItem(
        selected = tab == selected,
        onClick = onClick,
        icon = {
            Icon(
                icon,
                label
            )
        },
        label = {
            Text(
                label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Primary,
            selectedTextColor = Primary,
            indicatorColor = PrimarySoft,
            unselectedIconColor = Muted,
            unselectedTextColor = Muted
        )
    )
}

@Composable
fun HomeScreen(
    store: RebuildStore,
    start: () -> Unit,
    safety: () -> Unit
) {

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 18.dp,
            vertical = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    Modifier
                        .size(48.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "R",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 21.sp
                    )
                }

                Spacer(
                    Modifier.width(12.dp)
                )

                Column(
                    Modifier.weight(1f)
                ) {

                    Text(
                        "REBUILD",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Ink
                    )

                    Text(
                        "تمرین هوشمند · بدن سالم‌تر",
                        color = Muted,
                        fontSize = 11.sp
                    )
                }

                IconButton(
                    onClick = {}
                ) {

                    Icon(
                        Icons.Default.NotificationsNone,
                        "اعلان",
                        tint = Ink
                    )
                }
            }
        }

        item {

            Column {

                Text(
                    "سلام حامد",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Ink
                )

                Text(
                    "امروز هدف فقط یک چیز است: فرم تمیز و تمرین پیوسته.",
                    color = Muted,
                    fontSize = 13.sp
                )
            }
        }

        item {

            PremiumCard {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        Modifier.weight(1f)
                    ) {

                        Text(
                            "تمرین امروز",
                            color = Primary,
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp
                        )

                        Spacer(
                            Modifier.height(5.dp)
                        )

                        Text(
                            "Session A",
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Black,
                            color = Ink
                        )

                        Text(
                            "7 حرکت · حدود 55–65 دقیقه",
                            color = Muted,
                            fontSize = 12.sp
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = PrimarySoft,
                        modifier = Modifier.size(56.dp)
                    ) {

                        IconButton(
                            onClick = start
                        ) {

                            Icon(
                                Icons.Default.PlayArrow,
                                "شروع",
                                tint = Primary,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

                Spacer(
                    Modifier.height(14.dp)
                )

                Button(
                    onClick = start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(17.dp)
                ) {

                    Text(
                        "شروع تمرین",
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                MiniInfo(
                    "3",
                    "جلسه در هفته",
                    Icons.Default.CalendarMonth,
                    Modifier.weight(1f)
                )

                MiniInfo(
                    "RIR 2–3",
                    "شدت هدف",
                    Icons.Default.Speed,
                    Modifier.weight(1f)
                )

                MiniInfo(
                    "4 هفته",
                    "فاز اول",
                    Icons.Default.Timeline,
                    Modifier.weight(1f)
                )
            }
        }

        item {

            SectionTitle(
                "پیش از تمرین",
                "20 ثانیه"
            )

            PremiumCard(
                Modifier.clickable {
                    safety()
                }
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        color = SuccessSoft,
                        modifier = Modifier.size(48.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.HealthAndSafety,
                                null,
                                tint = Success
                            )
                        }
                    }

                    Spacer(
                        Modifier.width(12.dp)
                    )

                    Column(
                        Modifier.weight(1f)
                    ) {

                        Text(
                            "Readiness Check",
                            fontWeight = FontWeight.Black,
                            color = Ink
                        )

                        Text(
                            "گردن، زانو و علائم عصبی را قبل از شروع چک کن.",
                            color = Muted,
                            fontSize = 11.sp
                        )
                    }

                    Icon(
                        Icons.Default.ChevronLeft,
                        null,
                        tint = Muted
                    )
                }
            }
        }

        item {

            SectionTitle(
                "نقشه هفته",
                "3 جلسه"
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                sessions.forEach { session ->

                    val done =
                        store.completed(session.id)

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (done) SuccessSoft else Surface,
                        modifier = Modifier.weight(1f),
                        border = BorderStroke(
                            1.dp,
                            Border
                        )
                    ) {

                        Column(
                            Modifier.padding(vertical = 14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Surface(
                                shape = CircleShape,
                                color = if (done) Success else PrimarySoft,
                                modifier = Modifier.size(35.dp)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    if (done) {

                                        Icon(
                                            Icons.Default.Check,
                                            null,
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )

                                    } else {

                                        Text(
                                            session.id,
                                            color = Primary,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                            }

                            Spacer(
                                Modifier.height(7.dp)
                            )

                            Text(
                                session.day,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Ink
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramScreen(
    store: RebuildStore,
    open: (Session) -> Unit
) {

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {

        item {

            AppHeader(
                "برنامه تمرینی",
                "فاز 1 · عضله‌سازی کنترل‌شده"
            )
        }

        items(sessions) { session ->

            val done =
                store.completed(session.id)

            PremiumCard(
                Modifier.clickable {
                    open(session)
                }
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color =
                            if (done) SuccessSoft
                            else PrimarySoft,
                        modifier = Modifier.size(54.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            if (done) {

                                Icon(
                                    Icons.Default.CheckCircle,
                                    null,
                                    tint = Success
                                )

                            } else {

                                Text(
                                    session.id,
                                    color = Primary,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }

                    Spacer(
                        Modifier.width(13.dp)
                    )

                    Column(
                        Modifier.weight(1f)
                    ) {

                        Text(
                            "${session.day} · ${session.title}",
                            fontWeight = FontWeight.Black,
                            color = Ink,
                            fontSize = 16.sp
                        )

                        Text(
                            session.subtitle,
                            color = Muted,
                            fontSize = 11.sp
                        )

                        Spacer(
                            Modifier.height(5.dp)
                        )

                        Text(
                            "${session.exercises.size} حرکت · 55–65 دقیقه",
                            color = Primary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Icon(
                        Icons.Default.ChevronLeft,
                        null,
                        tint = Muted
                    )
                }
            }
        }

        item {

            Surface(
                shape = RoundedCornerShape(22.dp),
                color = PrimarySoft
            ) {

                Row(
                    Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.AutoAwesome,
                        null,
                        tint = Primary
                    )

                    Spacer(
                        Modifier.width(10.dp)
                    )

                    Text(
                        "ترتیب پیشرفت: فرم صحیح → بدون درد → تکرار کامل → افزایش وزنه",
                        color = Ink,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun SessionScreen(
    session: Session,
    store: RebuildStore,
    onBack: () -> Unit,
    onExercise: (Exercise) -> Unit
) {

    var completed by remember {
        mutableStateOf(
            store.completed(session.id)
        )
    }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            BackHeader(
                "${session.day} · Session ${session.id}",
                session.subtitle,
                onBack
            )
        }

        items(session.exercises) { ex ->

            PremiumCard(
                Modifier.clickable {
                    onExercise(ex)
                }
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color =
                            if (ex.animated) PrimarySoft
                            else AppBg,
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                if (ex.animated)
                                    Icons.Default.PlayCircle
                                else
                                    Icons.Default.FitnessCenter,
                                null,
                                tint =
                                    if (ex.animated)
                                        Primary
                                    else
                                        Muted
                            )
                        }
                    }

                    Spacer(
                        Modifier.width(12.dp)
                    )

                    Column(
                        Modifier.weight(1f)
                    ) {

                        Text(
                            ex.en,
                            color = Ink,
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp
                        )

                        Text(
                            ex.fa,
                            color = Muted,
                            fontSize = 11.sp
                        )

                        Spacer(
                            Modifier.height(5.dp)
                        )

                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(7.dp)
                        ) {

                            Chip(
                                "${ex.sets} ست"
                            )

                            Chip(
                                ex.reps
                            )

                            Chip(
                                "${ex.rest}s"
                            )
                        }
                    }

                    Icon(
                        Icons.Default.ChevronLeft,
                        null,
                        tint = Muted
                    )
                }
            }
        }

        item {

            Button(
                onClick = {

                    completed =
                        !completed

                    store.setCompleted(
                        session.id,
                        completed
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (completed)
                            Success
                        else
                            Primary
                )
            ) {

                Icon(
                    if (completed)
                        Icons.Default.CheckCircle
                    else
                        Icons.Default.Flag,
                    null
                )

                Spacer(
                    Modifier.width(7.dp)
                )

                Text(
                    if (completed)
                        "جلسه ثبت شد"
                    else
                        "ثبت پایان جلسه",
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun ExerciseDetailScreen(
    exercise: Exercise,
    store: RebuildStore,
    onBack: () -> Unit
) {

    var correctMode by rememberSaveable {
        mutableStateOf(true)
    }

    var isPlaying by rememberSaveable {
        mutableStateOf(true)
    }

    var frame by rememberSaveable {
        mutableIntStateOf(0)
    }

    var speed by rememberSaveable {
        mutableFloatStateOf(1f)
    }

    var seconds by rememberSaveable {
        mutableIntStateOf(exercise.rest)
    }

    var timerRunning by rememberSaveable {
        mutableStateOf(false)
    }

    var weight by rememberSaveable {
        mutableStateOf(
            store.weight(exercise.id)
        )
    }

    var reps by rememberSaveable {
        mutableStateOf(
            store
                .reps(exercise.id)
                .ifBlank {
                    exercise.reps.substringBefore('–')
                }
        )
    }

    var rir by rememberSaveable {
        mutableStateOf(
            store.rir(exercise.id)
        )
    }

    LaunchedEffect(
        isPlaying,
        speed,
        correctMode,
        exercise.animated
    ) {

        if (
            isPlaying &&
            correctMode &&
            exercise.animated
        ) {

            while (true) {

                delay(
                    if (speed == .5f)
                        1700
                    else
                        900
                )

                frame =
                    1 - frame
            }
        }
    }

    LaunchedEffect(
        timerRunning,
        seconds
    ) {

        if (
            timerRunning &&
            seconds > 0
        ) {

            delay(1000)

            seconds--

        } else if (seconds == 0) {

            timerRunning = false
        }
    }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding =
            PaddingValues(
                bottom = 28.dp
            ),
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        item {

            Column(
                Modifier.padding(
                    start = 18.dp,
                    end = 18.dp,
                    top = 14.dp
                )
            ) {

                BackHeader(
                    exercise.en,
                    exercise.fa,
                    onBack
                )
            }
        }

        item {

            if (exercise.animated) {

                Column(
                    Modifier.padding(
                        horizontal = 14.dp
                    )
                ) {

                    Surface(
                        shape =
                            RoundedCornerShape(26.dp),
                        color =
                            Color(0xFFF2F6FA),
                        border =
                            BorderStroke(
                                1.dp,
                                Border
                            )
                    ) {

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(440.dp)
                        ) {

                            if (correctMode) {

                                Crossfade(
                                    targetState = frame,
                                    animationSpec =
                                        tween(450),
                                    label =
                                        "exercise-frame"
                                ) { f ->

                                    Image(
                                        painter =
                                            painterResource(
                                                if (f == 0)
                                                    R.drawable.chest_press_start
                                                else
                                                    R.drawable.chest_press_press
                                            ),
                                        contentDescription =
                                            "Chest press motion frame",
                                        modifier =
                                            Modifier.fillMaxSize(),
                                        contentScale =
                                            ContentScale.Crop
                                    )
                                }

                            } else {

                                Image(
                                    painter =
                                        painterResource(
                                            R.drawable.mistake_head
                                        ),
                                    contentDescription =
                                        "Common mistake",
                                    modifier =
                                        Modifier.fillMaxSize(),
                                    contentScale =
                                        ContentScale.Crop
                                )
                            }

                            Surface(
                                shape =
                                    RoundedCornerShape(14.dp),
                                color =
                                    Color.White.copy(
                                        alpha = .92f
                                    ),
                                modifier =
                                    Modifier
                                        .align(
                                            Alignment.TopEnd
                                        )
                                        .padding(12.dp)
                            ) {

                                Row(
                                    Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 7.dp
                                    ),
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Icon(
                                        if (correctMode)
                                            Icons.Default.CheckCircle
                                        else
                                            Icons.Default.Cancel,
                                        null,
                                        tint =
                                            if (correctMode)
                                                Success
                                            else
                                                Danger,
                                        modifier =
                                            Modifier.size(17.dp)
                                    )

                                    Spacer(
                                        Modifier.width(5.dp)
                                    )

                                    Text(
                                        if (correctMode)
                                            "فرم صحیح"
                                        else
                                            "اشتباه رایج",
                                        fontWeight =
                                            FontWeight.Black,
                                        fontSize =
                                            11.sp,
                                        color =
                                            Ink
                                    )
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color =
                                    Color.White.copy(
                                        alpha = .94f
                                    ),
                                shadowElevation = 2.dp,
                                modifier =
                                    Modifier
                                        .align(
                                            Alignment.CenterEnd
                                        )
                                        .padding(13.dp)
                                        .size(52.dp)
                            ) {

                                IconButton(
                                    onClick = {

                                        isPlaying =
                                            !isPlaying
                                    }
                                ) {

                                    Icon(
                                        if (isPlaying)
                                            Icons.Default.Pause
                                        else
                                            Icons.Default.PlayArrow,
                                        null,
                                        tint = Ink,
                                        modifier =
                                            Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        Modifier.height(9.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        FilterChip(
                            selected =
                                correctMode,
                            onClick = {

                                correctMode = true

                                isPlaying = true
                            },
                            label = {
                                Text(
                                    "فرم صحیح"
                                )
                            },
                            leadingIcon = {

                                Icon(
                                    Icons.Default.Check,
                                    null,
                                    Modifier.size(16.dp)
                                )
                            },
                            modifier =
                                Modifier.weight(1f),
                            colors =
                                FilterChipDefaults
                                    .filterChipColors(
                                        selectedContainerColor =
                                            SuccessSoft,
                                        selectedLabelColor =
                                            Success
                                    )
                        )

                        FilterChip(
                            selected =
                                !correctMode,
                            onClick = {

                                correctMode = false

                                isPlaying = false
                            },
                            label = {
                                Text(
                                    "اشتباه خطرناک"
                                )
                            },
                            leadingIcon = {

                                Icon(
                                    Icons.Default.Close,
                                    null,
                                    Modifier.size(16.dp)
                                )
                            },
                            modifier =
                                Modifier.weight(1f),
                            colors =
                                FilterChipDefaults
                                    .filterChipColors(
                                        selectedContainerColor =
                                            DangerSoft,
                                        selectedLabelColor =
                                            Danger
                                    )
                        )

                        FilterChip(
                            selected =
                                speed == .5f,
                            onClick = {

                                speed =
                                    if (speed == .5f)
                                        1f
                                    else
                                        .5f
                            },
                            label = {

                                Text(
                                    if (speed == .5f)
                                        "0.5×"
                                    else
                                        "1×"
                                )
                            }
                        )
                    }
                }

            } else {

                Column(
                    Modifier.padding(
                        horizontal = 18.dp
                    )
                ) {

                    Surface(
                        shape =
                            RoundedCornerShape(28.dp),
                        color = PrimarySoft,
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Column(
                            Modifier.padding(26.dp),
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Icon(
                                Icons.Default.ViewInAr,
                                null,
                                tint = Primary,
                                modifier =
                                    Modifier.size(48.dp)
                            )

                            Spacer(
                                Modifier.height(10.dp)
                            )

                            Text(
                                "Motion Asset آماده اتصال",
                                fontWeight =
                                    FontWeight.Black,
                                color = Ink
                            )

                            Text(
                                "ساختار اپ برای ویدئوی سه‌بعدی این حرکت آماده است.",
                                color = Muted,
                                fontSize = 11.sp,
                                textAlign =
                                    TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        item {

            Column(
                Modifier.padding(
                    horizontal = 18.dp
                )
            ) {

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(9.dp)
                ) {

                    Metric(
                        "${exercise.sets}",
                        "ست",
                        Modifier.weight(1f)
                    )

                    Metric(
                        exercise.reps,
                        "تکرار",
                        Modifier.weight(1f)
                    )

                    Metric(
                        "${exercise.rest}s",
                        "استراحت",
                        Modifier.weight(1f)
                    )
                }
            }
        }

        if (exercise.id == "chest_press") {

            item {

                Column(
                    Modifier.padding(
                        horizontal = 18.dp
                    )
                ) {

                    PremiumCard {

                        Text(
                            "عضلات درگیر",
                            fontWeight =
                                FontWeight.Black,
                            color = Ink,
                            fontSize = 16.sp
                        )

                        Spacer(
                            Modifier.height(10.dp)
                        )

                        Image(
                            painter =
                                painterResource(
                                    R.drawable.muscles_chest
                                ),
                            contentDescription =
                                null,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(145.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            16.dp
                                        )
                                    ),
                            contentScale =
                                ContentScale.Crop
                        )

                        Spacer(
                            Modifier.height(9.dp)
                        )

                        Text(
                            "سینه بزرگ (اصلی) · جلوی شانه و پشت بازو (ثانویه)",
                            color = Muted,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        item {

            Column(
                Modifier.padding(
                    horizontal = 18.dp
                )
            ) {

                PremiumCard {

                    Text(
                        "نکته کلیدی حرکت",
                        fontWeight =
                            FontWeight.Black,
                        color = Ink,
                        fontSize = 15.sp
                    )

                    Spacer(
                        Modifier.height(7.dp)
                    )

                    Text(
                        exercise.cue,
                        color = Primary,
                        fontWeight =
                            FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        item {

            Column(
                Modifier.padding(
                    horizontal = 18.dp
                )
            ) {

                Surface(
                    shape =
                        RoundedCornerShape(22.dp),
                    color = SuccessSoft,
                    border =
                        BorderStroke(
                            1.dp,
                            Color(0xFFC8F2E2)
                        )
                ) {

                    Row(
                        Modifier.padding(15.dp),
                        verticalAlignment =
                            Alignment.Top
                    ) {

                        Icon(
                            Icons.Default.Shield,
                            null,
                            tint = Success
                        )

                        Spacer(
                            Modifier.width(9.dp)
                        )

                        Column {

                            Text(
                                "نکته ایمنی",
                                fontWeight =
                                    FontWeight.Black,
                                color = Success
                            )

                            Text(
                                exercise.safety,
                                color = Ink,
                                fontSize = 11.sp,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }
        }

        if (exercise.id == "chest_press") {

            item {

                Column(
                    Modifier.padding(
                        horizontal = 18.dp
                    )
                ) {

                    Text(
                        "اشتباهات رایج",
                        fontWeight =
                            FontWeight.Black,
                        color = Ink,
                        fontSize = 16.sp
                    )

                    Spacer(
                        Modifier.height(9.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        MistakeCard(
                            R.drawable.mistake_head,
                            "سر به جلو",
                            Modifier.weight(1f)
                        )

                        MistakeCard(
                            R.drawable.mistake_shrug,
                            "بالا آوردن شانه",
                            Modifier.weight(1f)
                        )

                        MistakeCard(
                            R.drawable.mistake_elbow,
                            "قفل آرنج",
                            Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {

            Column(
                Modifier.padding(
                    horizontal = 18.dp
                )
            ) {

                PremiumCard {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            "ثبت ست",
                            fontWeight =
                                FontWeight.Black,
                            color = Ink,
                            fontSize = 16.sp
                        )

                        Spacer(
                            Modifier.weight(1f)
                        )

                        Text(
                            "RIR 2–3",
                            color = Muted,
                            fontSize = 11.sp,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        NumericField(
                            weight,
                            {
                                weight = it
                            },
                            "وزنه kg",
                            Modifier.weight(1f)
                        )

                        NumericField(
                            reps,
                            {
                                reps = it
                            },
                            "تکرار",
                            Modifier.weight(1f)
                        )

                        NumericField(
                            rir,
                            {
                                rir = it
                            },
                            "RIR",
                            Modifier.weight(1f)
                        )
                    }

                    Spacer(
                        Modifier.height(10.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        OutlinedButton(
                            onClick = {

                                if (seconds == 0) {

                                    seconds =
                                        exercise.rest
                                }

                                timerRunning =
                                    !timerRunning
                            },
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(52.dp),
                            shape =
                                RoundedCornerShape(
                                    16.dp
                                )
                        ) {

                            Icon(
                                Icons.Default.Timer,
                                null
                            )

                            Spacer(
                                Modifier.width(6.dp)
                            )

                            Text(
                                if (timerRunning)
                                    "${seconds}s"
                                else if (seconds == 0)
                                    "دوباره"
                                else
                                    "استراحت ${seconds}s",
                                fontWeight =
                                    FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {

                                store.setLog(
                                    exercise.id,
                                    weight,
                                    reps,
                                    rir
                                )
                            },
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(52.dp),
                            shape =
                                RoundedCornerShape(
                                    16.dp
                                )
                        ) {

                            Icon(
                                Icons.Default.Check,
                                null
                            )

                            Spacer(
                                Modifier.width(6.dp)
                            )

                            Text(
                                "ثبت ست",
                                fontWeight =
                                    FontWeight.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SafetyScreen() {

    var neck by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    var knee by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    var tingling by rememberSaveable {
        mutableStateOf(false)
    }

    var weakness by rememberSaveable {
        mutableStateOf(false)
    }

    var radiating by rememberSaveable {
        mutableStateOf(false)
    }

    val red =
        tingling ||
        weakness ||
        radiating ||
        neck >= 6 ||
        knee >= 6

    val yellow =
        !red &&
        (
            neck >= 3 ||
            knee >= 3
        )

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding =
            PaddingValues(18.dp),
        verticalArrangement =
            Arrangement.spacedBy(13.dp)
    ) {

        item {

            AppHeader(
                "Readiness Check",
                "قبل از شروع تمرین · کمتر از 20 ثانیه"
            )
        }

        item {

            Surface(
                shape =
                    RoundedCornerShape(24.dp),
                color =
                    if (red)
                        DangerSoft
                    else if (yellow)
                        WarningSoft
                    else
                        SuccessSoft,
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    Modifier.padding(18.dp)
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            if (red)
                                Icons.Default.Warning
                            else
                                Icons.Default.VerifiedUser,
                            null,
                            tint =
                                if (red)
                                    Danger
                                else if (yellow)
                                    Color(0xFFB97700)
                                else
                                    Success
                        )

                        Spacer(
                            Modifier.width(8.dp)
                        )

                        Text(
                            if (red)
                                "تمرین را شروع نکن"
                            else if (yellow)
                                "محافظه‌کارانه تمرین کن"
                            else
                                "برای تمرین آماده‌ای",
                            fontWeight =
                                FontWeight.Black,
                            fontSize = 17.sp,
                            color = Ink
                        )
                    }

                    Spacer(
                        Modifier.height(6.dp)
                    )

                    Text(
                        if (red)
                            "اگر بی‌حسی، گزگز، ضعف یا درد تیرکشنده جدید داری، تمرین را متوقف کن و برای ارزیابی پزشکی اقدام کن."
                        else if (yellow)
                            "وزنه سبک‌تر و دامنه بدون درد را انتخاب کن و علائم را زیر نظر بگیر."
                        else
                            "این ابزار تشخیص پزشکی نیست؛ فقط یک گارد ایمنی قبل از تمرین است.",
                        color = Muted,
                        fontSize = 11.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        item {

            PainCard(
                "درد گردن",
                neck
            ) {
                neck = it
            }
        }

        item {

            PainCard(
                "درد زانوی چپ",
                knee
            ) {
                knee = it
            }
        }

        item {

            PremiumCard {

                SymptomRow(
                    "گزگز یا بی‌حسی جدید دست/بازو",
                    tingling
                ) {
                    tingling = it
                }

                HorizontalDivider(
                    color = Border
                )

                SymptomRow(
                    "ضعف جدید دست یا بازو",
                    weakness
                ) {
                    weakness = it
                }

                HorizontalDivider(
                    color = Border
                )

                SymptomRow(
                    "درد تیرکشنده از گردن",
                    radiating
                ) {
                    radiating = it
                }
            }
        }
    }
}

@Composable
fun ProgressScreen(
    store: RebuildStore
) {

    val count =
        sessions.count {
            store.completed(it.id)
        }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding =
            PaddingValues(18.dp),
        verticalArrangement =
            Arrangement.spacedBy(13.dp)
    ) {

        item {

            AppHeader(
                "پیشرفت من",
                "روی استمرار و کیفیت حرکت تمرکز کن"
            )
        }

        item {

            Surface(
                shape =
                    RoundedCornerShape(26.dp),
                color = Primary,
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    Modifier.padding(20.dp)
                ) {

                    Text(
                        "این هفته",
                        color =
                            Color.White.copy(
                                alpha = .8f
                            ),
                        fontSize = 12.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Row(
                        verticalAlignment =
                            Alignment.Bottom
                    ) {

                        Text(
                            "$count",
                            color = Color.White,
                            fontSize = 46.sp,
                            fontWeight =
                                FontWeight.Black
                        )

                        Text(
                            " / 3 جلسه",
                            color =
                                Color.White.copy(
                                    alpha = .75f
                                ),
                            modifier =
                                Modifier.padding(
                                    bottom = 8.dp
                                )
                        )
                    }

                    LinearProgressIndicator(
                        progress = {
                            count / 3f
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(9.dp)
                                .clip(CircleShape),
                        color =
                            Color.White,
                        trackColor =
                            Color.White.copy(
                                alpha = .22f
                            )
                    )
                }
            }
        }

        item {

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                MiniInfo(
                    "2–3",
                    "RIR هدف",
                    Icons.Default.Speed,
                    Modifier.weight(1f)
                )

                MiniInfo(
                    "3 روز",
                    "در هفته",
                    Icons.Default.CalendarMonth,
                    Modifier.weight(1f)
                )

                MiniInfo(
                    "55–65",
                    "دقیقه",
                    Icons.Default.Schedule,
                    Modifier.weight(1f)
                )
            }
        }

        item {

            PremiumCard {

                Text(
                    "قانون افزایش وزنه",
                    fontWeight =
                        FontWeight.Black,
                    color = Ink,
                    fontSize = 16.sp
                )

                Spacer(
                    Modifier.height(7.dp)
                )

                Text(
                    "وقتی همه ست‌ها را در بالاترین تکرار با فرم تمیز انجام دادی و هنوز حدود 2 تکرار ذخیره داشتی، جلسه بعد فقط کوچک‌ترین افزایش وزنه دستگاه را انتخاب کن.",
                    color = Muted,
                    fontSize = 12.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun PremiumCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Surface(
        shape =
            RoundedCornerShape(24.dp),
        color = Surface,
        border =
            BorderStroke(
                1.dp,
                Border
            ),
        shadowElevation = 1.dp,
        modifier =
            modifier.fillMaxWidth()
    ) {

        Column(
            Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
private fun MiniInfo(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier
) {

    Surface(
        shape =
            RoundedCornerShape(20.dp),
        color = Surface,
        border =
            BorderStroke(
                1.dp,
                Border
            ),
        modifier = modifier
    ) {

        Column(
            Modifier.padding(13.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                icon,
                null,
                tint = Primary,
                modifier =
                    Modifier.size(20.dp)
            )

            Spacer(
                Modifier.height(6.dp)
            )

            Text(
                value,
                color = Ink,
                fontWeight =
                    FontWeight.Black,
                fontSize = 14.sp,
                textAlign =
                    TextAlign.Center
            )

            Text(
                label,
                color = Muted,
                fontSize = 9.sp,
                textAlign =
                    TextAlign.Center
            )
        }
    }
}

@Composable
private fun Metric(
    value: String,
    label: String,
    modifier: Modifier
) {

    Surface(
        shape =
            RoundedCornerShape(18.dp),
        color = Surface,
        border =
            BorderStroke(
                1.dp,
                Border
            ),
        modifier = modifier
    ) {

        Column(
            Modifier.padding(
                vertical = 12.dp
            ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                value,
                fontWeight =
                    FontWeight.Black,
                color = Ink,
                fontSize = 17.sp
            )

            Text(
                label,
                color = Muted,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun Chip(
    text: String
) {

    Surface(
        shape =
            RoundedCornerShape(10.dp),
        color = AppBg
    ) {

        Text(
            text,
            Modifier.padding(
                horizontal = 8.dp,
                vertical = 5.dp
            ),
            color = Ink,
            fontSize = 10.sp,
            fontWeight =
                FontWeight.Bold
        )
    }
}

@Composable
private fun AppHeader(
    title: String,
    subtitle: String
) {

    Column {

        Text(
            title,
            fontSize = 27.sp,
            fontWeight =
                FontWeight.Black,
            color = Ink
        )

        Text(
            subtitle,
            color = Muted,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun BackHeader(
    title: String,
    subtitle: String,
    onBack: () -> Unit
) {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onBack
        ) {

            Icon(
                Icons.Default.ArrowForward,
                "بازگشت",
                tint = Ink
            )
        }

        Column(
            Modifier.weight(1f)
        ) {

            Text(
                title,
                fontSize = 22.sp,
                fontWeight =
                    FontWeight.Black,
                color = Ink
            )

            Text(
                subtitle,
                color = Muted,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    caption: String
) {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            title,
            fontWeight =
                FontWeight.Black,
            fontSize = 17.sp,
            color = Ink
        )

        Spacer(
            Modifier.weight(1f)
        )

        Text(
            caption,
            color = Muted,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun NumericField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier
) {

    OutlinedTextField(
        value = value,
        onValueChange = {

            onValueChange(
                it.filter { c ->
                    c.isDigit() ||
                    c == '.'
                }
            )
        },
        label = {
            Text(
                label,
                fontSize = 10.sp
            )
        },
        singleLine = true,
        keyboardOptions =
            KeyboardOptions(
                keyboardType =
                    KeyboardType.Decimal
            ),
        modifier = modifier,
        shape =
            RoundedCornerShape(14.dp)
    )
}

@Composable
private fun MistakeCard(
    image: Int,
    label: String,
    modifier: Modifier
) {

    Surface(
        shape =
            RoundedCornerShape(18.dp),
        color = DangerSoft,
        modifier = modifier
    ) {

        Column {

            Image(
                painter =
                    painterResource(image),
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(92.dp),
                contentScale =
                    ContentScale.Crop
            )

            Text(
                label,
                Modifier.padding(8.dp),
                color = Danger,
                fontSize = 10.sp,
                fontWeight =
                    FontWeight.Black,
                textAlign =
                    TextAlign.Center
            )
        }
    }
}

@Composable
private fun PainCard(
    title: String,
    value: Float,
    onChange: (Float) -> Unit
) {

    PremiumCard {

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                title,
                Modifier.weight(1f),
                fontWeight =
                    FontWeight.Black,
                color = Ink
            )

            Surface(
                shape =
                    RoundedCornerShape(10.dp),
                color =
                    if (value >= 6)
                        DangerSoft
                    else if (value >= 3)
                        WarningSoft
                    else
                        SuccessSoft
            ) {

                Text(
                    "${value.toInt()}/10",
                    Modifier.padding(
                        horizontal = 9.dp,
                        vertical = 5.dp
                    ),
                    fontWeight =
                        FontWeight.Black,
                    color = Ink
                )
            }
        }

        Slider(
            value = value,
            onValueChange = onChange,
            valueRange = 0f..10f,
            steps = 9
        )

        Row(
            Modifier.fillMaxWidth()
        ) {

            Text(
                "بدون درد",
                color = Muted,
                fontSize = 9.sp
            )

            Spacer(
                Modifier.weight(1f)
            )

            Text(
                "شدید",
                color = Muted,
                fontSize = 9.sp
            )
        }
    }
}

@Composable
private fun SymptomRow(
    title: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                vertical = 7.dp
            ),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            title,
            Modifier.weight(1f),
            color = Ink,
            fontSize = 12.sp,
            fontWeight =
                FontWeight.SemiBold
        )

        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors =
                SwitchDefaults.colors(
                    checkedTrackColor =
                        Danger
                )
        )
    }
}
