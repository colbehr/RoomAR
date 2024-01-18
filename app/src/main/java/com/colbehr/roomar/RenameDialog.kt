package com.colbehr.roomar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RenameDialog(
    name: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var newName by remember { mutableStateOf(name) }

    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = {
            Text(text = "Rename")
        },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    capitalization = KeyboardCapitalization.Words
                )
            )

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(newName.trim())
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }

    )
}


@Composable
@Preview(showBackground = true)
fun RenameDialogPreview() {
    RenameDialog(
        name = "OldName",
        onDismiss = {
        },
        onConfirm = { newName ->
        }
    )
}