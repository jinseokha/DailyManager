package com.devseok.dailymanager.feature.calendar

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devseok.dailymanager.R
import com.devseok.dailymanager.custom.picker.ColorEnvelopeDTO
import com.devseok.dailymanager.custom.picker.rememberColorPickerController
import com.devseok.dailymanager.data.CalendarDataDTO
import com.devseok.dailymanager.feature.calendar.add.CalendarAddDialog
import com.devseok.dailymanager.feature.calendar.add.CalendarEditDialog
import com.devseok.dailymanager.feature.calendar.function.Calendar
import com.devseok.dailymanager.feature.calendar.function.CalendarSize
import com.devseok.dailymanager.feature.calendar.function.rememberCalendarState
import com.devseok.dailymanager.feature.login.LoginPageVM
import com.devseok.dailymanager.navigation.Screen
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CalendarPage(
    navHostController: NavHostController,
    viewModel: CalendarPageVM = hiltViewModel<CalendarPageVM>(),
    loginViewModel: LoginPageVM = hiltViewModel(),
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val state = rememberCalendarState()
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerItems = listOf(Screen.Calender, Screen.Alert, Screen.Settings)

    var showAddBottomSheet by remember { mutableStateOf(false) }
    var exitDialogState by remember { mutableStateOf(false) }

    var editCalendarPageDTO by remember { mutableStateOf(CalendarDataDTO())}
    var showEditBottomSheet by remember { mutableStateOf(false) }

    val userInfo by loginViewModel.fireBaseUserInfo.collectAsState()
    val userProfile by loginViewModel.userProfile.collectAsState()

    val saveDataList by viewModel.saveDataList.collectAsState()

    val controller = rememberColorPickerController()

    BackHandler {
        exitDialogState = true
    }

    if (exitDialogState) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            modifier = Modifier
                .fillMaxWidth(0.80f),
            containerColor = colorResource(R.color.white),
            onDismissRequest = {
                exitDialogState = false
            },
            title = {
                Text(
                    text = "안내",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.text_black)
                )
            },
            text = {
                Text(
                    text = "앱 종료하시겠습니까?",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        exitDialogState = false

                        activity?.finishAffinity() // 앱 전체 종료
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                ) {
                    Text(
                        text = "확인",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.text_black)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { exitDialogState = false },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                ) {
                    Text(
                        text = "취소",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.text_black)
                    )
                }
            },
            shape = RectangleShape,
        )
    }

    // edit dialog
    if (showEditBottomSheet) {
        CalendarEditDialog(
            calendarDataDTO = editCalendarPageDTO,
            onCancelListener = {
                showEditBottomSheet = false
            },
            onConfirmListener = { calendarDTO ->
                viewModel.updateMessageToUser(calendarDTO, onResult = { it ->
                    if (it) {
                        viewModel.getAllMessage(
                            onResult = { it ->
                                showEditBottomSheet = false
                            }
                        )
                    }
                })
            }
        )
    }

    // add dialog
    if (showAddBottomSheet) {
        CalendarAddDialog(
            controller,
            onCancelListener = {
                showAddBottomSheet = false
            },
            onConfirmListener = { value, color ->

                val colorDto = ColorEnvelopeDTO(
                    colorInt = color.colorInt,
                    hexCode = color.hexCode,
                    fromUser = color.fromUser
                )

                val calendarDataDTO: CalendarDataDTO = CalendarDataDTO(
                    userId = userInfo!!.email!!,
                    date = state.selectedDate.toString(),
                    message = value,
                    color = colorDto,
                    timestamp = null,
                )

                viewModel.addMessageToUser(
                    data = calendarDataDTO,
                    onResult = { it ->
                        Log.d("test", "" + it)
                        if (it) {

                            val userid = userInfo!!.email!!
                            val selectDate = state.selectedDate.toString()

                            viewModel.getAllMessage(
                                onResult = { it ->
                                    showAddBottomSheet = false
                                }
                            )

                        } else {


                        }
                    }
                )
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllMessage(
            onResult = { it ->

            }
        )
    }

    if (userInfo != null) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        text = "Menu",
                    )

                    drawerItems.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(item.title) },
                            selected = false,
                            onClick = {
                                // 여기에 네비게이션 동작 추가
                                navHostController.navigate(item.route) {
                                    popUpTo(navHostController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }

                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.background)),
                topBar = { },
                floatingActionButton = {
                    FloatingActionButton(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        shape = CircleShape,
                        onClick = {
                            showAddBottomSheet = true
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_fab_add),
                            "add"
                        )
                    }
                },

            ) { innerPadding ->
                saveDataList.let { dataList ->
                    BoxWithConstraints(
                        modifier = Modifier
                            .padding(innerPadding)

                    ) {

                        val halfHeight = remember { maxHeight / 2 }
                        val fullHeight = remember { maxHeight }
                        var calendarHeight by remember { mutableStateOf(if (state.snapState == CalendarSize.FULL) fullHeight else halfHeight) }
                        val animatedHeight by animateDpAsState(calendarHeight)

                        Column(
                            modifier = Modifier
                                .background(colorResource(R.color.background))
                                .pointerInput(Unit) {
                                    detectVerticalDragGestures(
                                        onVerticalDrag = { change, dragAmount ->
                                            change.consume()

                                            // 상하 무한 제스처 방지
                                            calendarHeight = (calendarHeight + dragAmount.toDp()).coerceIn(halfHeight, fullHeight)
                                        },
                                        onDragEnd = {
                                            when (state.snapState) {
                                                CalendarSize.HALF -> if (calendarHeight > halfHeight) {
                                                    state.snapState = CalendarSize.FULL
                                                    calendarHeight = fullHeight
                                                }
                                                CalendarSize.FULL -> if (calendarHeight < fullHeight) {
                                                    state.snapState = CalendarSize.HALF
                                                    calendarHeight = halfHeight
                                                }
                                            }
                                        }
                                    )
                                }
                        ) {
                            Calendar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(animatedHeight),
                                state = state,
                                userInfo = userInfo!!,
                                drawerState = drawerState,
                                saveDataList = saveDataList,
                                onClick = {
                                    state.snapState = CalendarSize.HALF
                                    calendarHeight = halfHeight
                                }
                            )

                            if (userProfile != null) {
                                Log.d("test", "" + userProfile)
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 8.dp,
                                        end = 16.dp,
                                        bottom = 0.dp
                                    ),
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(configuration.screenHeightDp.dp - animatedHeight)

                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                                ) {
                                    Text(
                                        text = state.selectedDate.format(DateTimeFormatter.ofPattern("d. E")),
                                        fontSize = 17.sp,
                                        lineHeight = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(2.dp)
                                            .background(Color.Black, CircleShape)
                                    )
                                }

                                if (saveDataList.size > 0) {

                                    val calendarDatumDTOS: List<CalendarDataDTO> = saveDataList[state.selectedDate] ?: emptyList()

                                    for (data in calendarDatumDTOS) {

                                        if (data.message.isNotEmpty()) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(42.dp)
                                                        .padding(start = 17.dp, end = 17.dp, top = 10.dp, bottom = 4.dp)
                                                        .background(colorResource(R.color.white))
                                                        .clickable {
                                                            editCalendarPageDTO = data

                                                            showEditBottomSheet = true
                                                        },
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Spacer(
                                                        modifier = Modifier
                                                            .width(5.dp)
                                                            .height(42.dp)
                                                            .background(data.color.color)
                                                    )

                                                    Spacer(
                                                        modifier = Modifier
                                                            .width(10.dp)
                                                    )

                                                    Text(
                                                        text = data.message,
                                                        fontSize = 20.sp,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )

                                                    Spacer(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                    )

                                                    Image(
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clickable {

                                                                viewModel.delMessage(
                                                                    data = data,
                                                                    onResult = {
                                                                        viewModel.getAllMessage(
                                                                            onResult = { it ->

                                                                            }
                                                                        )
                                                                    }
                                                                )
                                                            },
                                                        painter = painterResource(R.drawable.baseline_delete_24),
                                                        contentDescription = "add"
                                                    )
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}