package com.materialkolor.builder.ui.home.page.device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.materialkolor.builder.ui.home.page.device.components.frame.AndroidPhoneFrame
import com.materialkolor.builder.ui.home.page.device.components.frame.IosPhoneFrame

@Composable
fun DeviceSection(
    modifier: Modifier = Modifier,
) {
    FlowRow(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth(),
    ) {
        AndroidPhoneFrame {
            Text("Android")
        }

        IosPhoneFrame {
            Text("iOS")
        }
    }
}