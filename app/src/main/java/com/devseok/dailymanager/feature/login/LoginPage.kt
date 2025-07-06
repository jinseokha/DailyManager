package com.devseok.dailymanager.feature.login

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devseok.dailymanager.R
import com.devseok.dailymanager.commons.LockScreenOrientation
import com.devseok.dailymanager.feature.calendar.CalendarPageVM
import com.devseok.dailymanager.navigation.Route
import com.google.android.gms.auth.api.signin.GoogleSignIn


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginPage(
    navHostController: NavHostController,
    viewModel: LoginPageVM = hiltViewModel(),
) {
    // 세로 영역 고정
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val context = LocalContext.current

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val userInfo by viewModel.userProfile.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        viewModel.handleGoogleSignInResult(task)
    }

    LaunchedEffect(key1 = userInfo) {
        if (userInfo != null) {
            navHostController.navigate(Route.CalendarPage.routeName) {
                popUpTo(Route.CalendarPage.routeName) { inclusive = true }
            }

        }
    }

    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "다음 계정으로 로그인",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center
            )
            Divider(modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // 구글 로그인 버튼 등록
            Image(
                painter = painterResource(
                    id = R.drawable.android_light_rd_na
                ),
                contentDescription = "Google Login Button",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        //onGoogleLoginClick()
                        launcher.launch(viewModel.getSignInIntent())
                    }
            )
        }
    }
}