package com.colbehr.roomar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun WelcomeDialog(
    title: String,
    body: String,
    onClose: () -> Unit
) {

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = body)
        },
        onDismissRequest = {
            onClose()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onClose()
                }
            ) {
                Text("Get Started")
            }
        }
    )

}

@Composable
@Preview(showBackground = true)
fun WelcomeDialogPreview() {
    WelcomeDialog("Welcome To Room AR",
        "Effortlessly convert rooms to point data using AR technology. Simplify your workflow, save time, create more, faster. Please reach out if you have any feedback!\n" +
                "\n" +
                "Thank you!",
        {})
}
