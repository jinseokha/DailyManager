package com.devseok.dailymanager.feature.alert

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devseok.dailymanager.custom.picker.ColorEnvelopeDTO
import com.devseok.dailymanager.custom.picker.HsvColorPicker
import com.devseok.dailymanager.custom.picker.rememberColorPickerController

@Composable
fun AlertPage(
    navHostController: NavHostController,
    viewModel: AlertPageBaseVM = hiltViewModel<AlertPageVM>()
) {

    val scope = rememberCoroutineScope()

    val controller = rememberColorPickerController()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelopeDTO: ColorEnvelopeDTO ->
                // do something

                val color: Color = colorEnvelopeDTO.color // ARGB color value.
                val hexCode: String = colorEnvelopeDTO.hexCode // Color hex code, which represents color value.
                val fromUser: Boolean = colorEnvelopeDTO.fromUser

                Log.d("testtest", "color = " + color)
                Log.d("testtest", "hexCode = " + hexCode)
                Log.d("testtest", "fromUser = " + fromUser)
            }
        )

    }


}