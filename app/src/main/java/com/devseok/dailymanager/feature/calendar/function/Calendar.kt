package com.devseok.dailymanager.feature.calendar.function

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.devseok.dailymanager.R
import com.devseok.dailymanager.data.CalendarDataDTO
import com.devseok.dailymanager.data.Utils
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@SuppressLint("NewApi")
@Composable
fun Calendar(
    modifier: Modifier,
    state: CalendarState,
    drawerState: DrawerState,
    userInfo: FirebaseUser,
    saveDataList : Map<LocalDate, List<CalendarDataDTO>>,
    onClick: () -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // image bitmap
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }

    LaunchedEffect(state.datePagerState.currentPage) {
        // datePager 현재 페이지가 변경되면(직접 넘겨도) 선택된 날짜를 현재 페이지 첫째 날로 변경
        state.currentPageYM.takeIf {
            it != YearMonth.from(state.selectedDate)
        }?.let {
            state.selectedDate = it.atDay(1)
        }
    }

    LaunchedEffect(state.selectedDate) {
        // 선택된 날짜가 현재 페이지에 없으면, 해당 페이지로 datePager 스크롤
        state.currentPageYM.takeIf {
            it != YearMonth.from(state.selectedDate)
        }?.let { pageMonth ->
            state.datePagerState.animateScrollToPage(state.datePagerState.currentPage  + (1.takeIf { state.selectedDate.isAfter(pageMonth.atEndOfMonth()) } ?: -1))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        scope.launch { drawerState.open() }
                    }
                ) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {

                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        block = { append(state.currentPageYM.run { takeIf { year != LocalDate.now().year }?.let { "${year}. $monthValue" } ?: "$monthValue" }) },
                                        style = SpanStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Medium,
                                            baselineShift = BaselineShift(-0.015f)
                                        )
                                    )
                                    state.currentPageYM.takeIf { it.year == LocalDate.now().year }?.let { append("월") }
                                },
                                fontSize = 20.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(
                                modifier = Modifier
                                    .width(3.dp)
                            )

                            Image(
                                painter = painterResource(R.drawable.ic_arrow_down_svg),
                                contentDescription = "날짜 선택",
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    val zzd = Utils.getUserProfile(userInfo)
                    val photoUrl: String = zzd["photoUrl"].toString()

                    Glide.with(context)
                        .asBitmap()
                        .load(photoUrl)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                bitmap.value = resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {

                            }
                        })

                    bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
                        Image(
                            bitmap = fetchedBitmap,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier

                                .size(32.dp)
                                .clip(CircleShape),
                        )
                    } ?: Image(
                        painter = painterResource(id = R.drawable.account_circle_24dp_1f1f1f),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { dayText ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = dayText,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red.takeIf { dayText == "일" } ?: Color.Black
                    )
                }
            }
        }

        HorizontalPager(
            state = state.datePagerState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) { page ->
            val pageYearMonth = remember { YearMonth.from(state.currentDate).plusMonths((page - Int.MAX_VALUE / 2).toLong()) }
            val dayOfMonth = remember { state.getDaysOfMonth(pageYearMonth) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                dayOfMonth.chunked(7).forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        week.forEach { date ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {
                                val isToday = remember(date, state.currentDate) { date == state.currentDate }
                                val isSelected = remember(date, state.selectedDate) { date == state.selectedDate }
                                val isVisibleMonth = remember { YearMonth.from(date) == pageYearMonth }

                                CalendarDay(
                                    date = date,
                                    saveDataList = saveDataList,
                                    isToday = isToday,
                                    isSelected = isSelected,
                                    isVisibleMonth = isVisibleMonth,
                                    onClick = {
                                        state.selectedDate = date
                                        onClick()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}