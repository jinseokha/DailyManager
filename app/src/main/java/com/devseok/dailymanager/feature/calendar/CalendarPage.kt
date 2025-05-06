package com.devseok.dailymanager.feature.calendar

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devseok.dailymanager.R
import com.devseok.dailymanager.custom.FabItem
import com.devseok.dailymanager.custom.MultiFloatingActionButton
import com.devseok.dailymanager.feature.calendar.add.CalendarAddDialog
import com.devseok.dailymanager.feature.calendar.function.Calendar
import com.devseok.dailymanager.feature.calendar.function.CalendarSize
import com.devseok.dailymanager.feature.calendar.function.rememberCalendarState
import com.devseok.dailymanager.feature.login.LoginPageVM
import com.devseok.dailymanager.navigation.NavGraph
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

    val state = rememberCalendarState()
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerItems = listOf(Screen.Calender, Screen.Alert, Screen.Settings)

    var showAddBottomSheet by remember { mutableStateOf(false) }

    val userInfo by loginViewModel.fireBaseUserInfo.collectAsState()
    val userProfile by loginViewModel.userProfile.collectAsState()

    if (showAddBottomSheet) {
        CalendarAddDialog(
            onCancelListener = {
                showAddBottomSheet = false
            },
            onConfirmListener = { value ->
                viewModel.addMessageToUser(value)
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
                    /*MultiFloatingActionButton(
                        fabIcon = painterResource(id = R.drawable.ic_fab_add),
                        items = arrayListOf(
                            FabItem(icon = painterResource(R.drawable.ic_fab_add), label = "Button 1") {
                                Toast.makeText(context,"Floating Button clicked",Toast.LENGTH_LONG).show()
                            }
                        )
                    )*/
                    FloatingActionButton(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        shape = CircleShape,
                        onClick = {
                            showAddBottomSheet = true
                          //  Toast.makeText(context,"Floating Button clicked",Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_fab_add),
                            "add"
                        )
                    }
                },

            ) { innerPadding ->
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(innerPadding)
                        .statusBarsPadding()
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
                                    .padding(horizontal = 20.dp)
                            ) {
                                Text(
                                    text = state.selectedDate.format(DateTimeFormatter.ofPattern("d. E")),
                                    fontSize = 17.sp,
                                    lineHeight = 17.sp,
                                    fontWeight = FontWeight.SemiBold
                                )


                                /*Text(
                                    text = state.calculateDaysDifference(),
                                    fontSize = 17.sp,
                                    lineHeight = 17.sp,
                                    fontWeight = FontWeight.SemiBold
                                )*/

                                Box(
                                    modifier = Modifier
                                        .size(2.dp)
                                        .background(Color.Black, CircleShape)
                                )

                                /*Text(
                                    text = state.selectedDate.format(DateTimeFormatter.ofPattern("M. d. (E)")),
                                    fontSize = 15.sp,
                                    lineHeight = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF999999)
                                )*/
                            }
                        }
                    }
                }

            }
        }
    }

}