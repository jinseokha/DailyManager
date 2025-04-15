package com.devseok.dailymanager.feature.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devseok.dailymanager.R

@Composable
fun HomePage(
    navHostController: NavHostController,
    viewModel: HomePageBaseVM = hiltViewModel<HomePageVM>()
) {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .height(25.dp)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.view_gray),
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(all = 12.dp)
                .clickable {
                    //navHostController.navigate(Route.SearchPage.routeName)
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "To DO",
                fontSize = 16.sp,
                color = colorResource(R.color.text_black)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Text(
                text = "검색",
                fontSize = 16.sp,
                color = colorResource(R.color.text_black)
            )
        }

    }

}