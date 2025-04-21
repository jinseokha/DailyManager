package com.devseok.dailymanager.feature.calendar.function

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.YearMonth

@SuppressLint("NewApi")
@Composable
fun Calendar(
    modifier: Modifier,
    state: CalendarState,
    onClick: () -> Unit
) {

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



    }










}















