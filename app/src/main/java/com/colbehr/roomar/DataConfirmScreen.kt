package com.colbehr.roomar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DataConfirmScreen(
    navController: NavController,
    points: String
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = Date()
    val formattedDate = dateFormatter.format(currentDate)

    val nameLabel = remember { mutableStateOf(formattedDate) }
    val pointsList = parseStringToPointArrays(points)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 46.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openAlertDialog.value = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = nameLabel.value,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(5))
        ) {
            Column {
                Points2DGraphic(pointsList, false)
            }
        }

        Spacer(modifier = Modifier)

        // Two Buttons (Restart and Export) at the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Restart", color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                onClick = {
                    navController.navigate(
                        route = Screen.ExportScreen.route + "/" + points + "/" + URLEncoder.encode(
                            nameLabel.value,
                            "UTF-8"
                        )
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(50))
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Next", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
    when {
        openAlertDialog.value -> {
            RenameDialog(
                name = nameLabel.value,
                onDismiss = { openAlertDialog.value = false },
                onConfirm = { newName ->
                    run {
                        nameLabel.value = newName; openAlertDialog.value = false; Log.d(
                        "",
                        "DataConfirmScreen: $nameLabel"
                    )
                    }
                }
            )
        }
    }
}

fun parseStringToPointArrays(input: String): List<Offset> {
    return input.split(",") // Split by comma to get individual point strings (x:y)
        .map { pointStr ->
            val pointSplit =
                pointStr.split(":") // Split each point string by colon to get x and y values
            Offset(pointSplit[0].toFloat(), pointSplit[1].toFloat()) // Convert to Offset
        }
}

@Composable
@Preview(showBackground = true)
fun DataConfirmScreenPreview() {
    DataConfirmScreen(
        navController = rememberNavController(),
        "0.27217773:-0.11698,-0.07644056:-1.18017,0.22540171:-1.1501701"
    )
}