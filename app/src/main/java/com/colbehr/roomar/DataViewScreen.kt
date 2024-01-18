package com.colbehr.roomar

import android.content.Context
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.io.File
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun DataViewScreen(
    navController: NavController,
    points: String,
    fileName: String,
    name: String
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val nameLabel = remember { mutableStateOf(name) }
    val pointsList = parseStringToPointArrays(points)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 46.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
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
                text = URLDecoder.decode(nameLabel.value, "UTF-8"),
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

        // 2 Buttons (Export/delete) at the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    deleteFileByFilename(context, fileName)
                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete", color = MaterialTheme.colorScheme.onErrorContainer)
            }
            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                onClick = {
                    navController.navigate(
                        route = Screen.ExportViewScreen.route + "/" + points + "/" + URLEncoder.encode(
                            nameLabel.value,
                            "UTF-8"
                        ) + "/" + fileName
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
                name = URLDecoder.decode(nameLabel.value, "UTF-8"),
                onDismiss = { openAlertDialog.value = false },
                onConfirm = { newName ->
                    run {
                        nameLabel.value = newName; openAlertDialog.value = false
                    }
                }
            )
        }
    }
}

fun deleteFileByFilename(context: Context, filename: String): Boolean {
    try {
        val file = File(context.filesDir, filename)
        if (file.exists() && file.isFile) {
            return file.delete()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}


@Composable
@Preview(showBackground = true)
fun DataViewScreenPreview() {
    DataViewScreen(
        navController = rememberNavController(),
        "0.27217773:-0.11698,-0.07644056:-1.18017,0.22540171:-1.1501701",
        name = "squire",
        fileName = "123123.json"
    )
}