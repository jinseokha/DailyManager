package com.devseok.dailymanager.feature.calendar.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devseok.dailymanager.R
import com.devseok.dailymanager.custom.picker.ColorEnvelopeDTO
import com.devseok.dailymanager.custom.picker.HsvColorPicker
import com.devseok.dailymanager.custom.picker.rememberColorPickerController
import com.devseok.dailymanager.data.CalendarDataDTO

/**
 * @author Ha Jin Seok
 * @created 2025-09-05
 * @desc
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarEditDialog(
    calendarDataDTO: CalendarDataDTO,
    onCancelListener: () -> Unit,
    onConfirmListener: (CalendarDataDTO) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var strText by remember { mutableStateOf(calendarDataDTO.message) }

    var currentColorEnvelopeDTO by remember {
        mutableStateOf(
            calendarDataDTO.color
        )
    }

    val controller = rememberColorPickerController()

    var showColorPicker by remember { mutableStateOf(false) }

    if (showColorPicker) {
        var saveColorEnvelopeDTO: ColorEnvelopeDTO = currentColorEnvelopeDTO

        AlertDialog(
            onDismissRequest = {
                showColorPicker = false
            },
            title = { Text(text = "알림") },
            text = {
                Column(

                ) {
                    HsvColorPicker(
                        initialColor = currentColorEnvelopeDTO.color,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(10.dp),
                        controller = controller,
                        onColorChanged = { colorEnvelopeDTO: ColorEnvelopeDTO ->
                            // do something
                            saveColorEnvelopeDTO = colorEnvelopeDTO

                            val color: Color = colorEnvelopeDTO.color // ARGB color value.
                            // Color hex code, which represents color value.
                            val hexCode = colorEnvelopeDTO.hexCode
                            val fromUser: Boolean = colorEnvelopeDTO.fromUser

                            /*strColor = hexCode*/
                        }
                    )

                    Row {

                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showColorPicker = false
                    currentColorEnvelopeDTO = saveColorEnvelopeDTO
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showColorPicker = false }) {
                    Text("취소")
                }
            }
        )
    }

    ModalBottomSheet(
        onDismissRequest = {
            onCancelListener()
        },
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = strText,
                onValueChange = {
                    strText = it
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(R.drawable.ic_pencil),
                        contentDescription = "pencil"
                    )
                },
                placeholder = {
                    Text(
                        text = "새 일정 입력",
                        fontSize = 13.sp,
                    )
                }
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(currentColorEnvelopeDTO.color)
                        .clickable {
                            showColorPicker = true
                        }
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            val saveCalendarDTO = CalendarDataDTO(
                                id = calendarDataDTO.id,
                                userId = calendarDataDTO.userId,
                                date = calendarDataDTO.date,
                                message = strText,
                                color = currentColorEnvelopeDTO
                            )

                            onConfirmListener(saveCalendarDTO)
                        },
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = "add"
                )

            }

        }


    }


}

























