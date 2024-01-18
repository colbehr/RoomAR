package com.colbehr.roomar

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ExportViewScreen(
    navController: NavController,
    points: String,
    name: String,
    fileName: String
) {
    val context = LocalContext.current
    var selectedOptions by remember { mutableStateOf<Set<Option>>(emptySet()) }
    val jsonObjectString = createJsonObject(points, name)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 46.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // Export Options Title at the top
            Text(
                text = "Export Options",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(5)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.export_graphic),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        // Choose export type options using checkboxes
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OptionCheckbox("2D Point Data", Option.TwoD in selectedOptions, true) {
                selectedOptions = if (Option.TwoD in selectedOptions) {
                    selectedOptions - Option.TwoD
                } else {
                    selectedOptions + Option.TwoD
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OptionCheckbox("3D Model", Option.ThreeD in selectedOptions, true) {
                selectedOptions = if (Option.ThreeD in selectedOptions) {
                    selectedOptions - Option.ThreeD
                } else {
                    selectedOptions + Option.ThreeD
                }
            }
        }

        // Cancel/Go Back and Export buttons at the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cancel/Go Back", color = MaterialTheme.colorScheme.onBackground)
            }

            TextButton(
                onClick = {
                    //delete old file
                    deleteFileByFilename(context, fileName)
                    //save json to file
                    val newFileName = saveStringToFile(context, jsonObjectString)
                    when {
                        selectedOptions.contains(Option.TwoD) && selectedOptions.contains(Option.ThreeD) -> {
                            // Share both

                            val jsonFileUri = readFileUri(context, newFileName)
                            val modelFileUri = createOBJFile(context, jsonObjectString)

                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND_MULTIPLE
                                type = "*/*" // Set a generic MIME type
                                putParcelableArrayListExtra(
                                    Intent.EXTRA_STREAM,
                                    arrayListOf(jsonFileUri, modelFileUri)
                                )
                                putExtra(Intent.EXTRA_TEXT, jsonObjectString)

                            }
                            val chooser = Intent.createChooser(shareIntent, "Share Content")
                            startActivity(context, chooser, null)
                        }

                        selectedOptions.contains(Option.TwoD) -> {
                            // Share the text
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, jsonObjectString)
                            }
                            val chooser = Intent.createChooser(shareIntent, "Share JSON Object")
                            startActivity(context, chooser, null)
                        }

                        selectedOptions.contains(Option.ThreeD) -> {
                            // Share the 3D model
                            val fileUri = createOBJFile(context, jsonObjectString)
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_STREAM, fileUri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                type = "model/obj"
                            }
                            val chooser =
                                Intent.createChooser(shareIntent, "Share 3D Model as .obj")
                            startActivity(context, chooser, null)
                        }
                    }

                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export", color = MaterialTheme.colorScheme.onPrimary)
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun ExportViewScreenPreview() {
    ExportViewScreen(
        navController = rememberNavController(),
        "0.27217773:-1.1501698,-0.07644056:-1.15017,0.22540171:-1.1501701",
        name = "squire",
        fileName = "123123.json"
    )
}