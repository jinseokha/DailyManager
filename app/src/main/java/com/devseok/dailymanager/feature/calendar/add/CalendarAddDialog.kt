package com.devseok.dailymanager.feature.calendar.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarAddDialog(
    onCancelListener: () -> Unit,
    onConfirmListener: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var strText by remember { mutableStateOf("") }

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

                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {

                        },
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "add"
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onConfirmListener(strText)
                        },
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "add"
                )
            }


            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )
        }
    }
}