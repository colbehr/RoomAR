package com.colbehr.roomar

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp)
            .padding(16.dp)
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

        Column(
            modifier = Modifier
                .padding(top = 46.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_vector),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .clip(CircleShape)
                    .fillMaxWidth()
            )
            Text(
                text = "RoomAR",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                fontSize = 20.sp, // Adjust the font size as needed
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        val url = "https://github.com/colbehr"

                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(context, browserIntent, null)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }

//                IconButton(
//                    onClick = {
//                        val url = "https://www.linkedin.com/in/colbehr/"
//
//
//                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                        startActivity(context, browserIntent, null)
//                    }
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.linkedin),
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onBackground
//                    )
//                }

                IconButton(
                    onClick = {
                        val url = "https://colbehr.com"

                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(context, browserIntent, null)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.website),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Text(
                text = "Check out the project on Github and feel free to submit a pull request!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onBackground
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(
                onClick = {
                    setFirstRunFlag(context, true)
                    val toast = Toast.makeText(context, "Done", Toast.LENGTH_SHORT)
                    toast.show()
                },
                content = {
                    Text(
                        text = "Reset Welcome Dialog",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )

            TextButton(
                onClick = {
                    val alertDialogBuilder = AlertDialog.Builder(context)

                    // Set the dialog title and message
                    alertDialogBuilder.setTitle("Heroicons License")
                    alertDialogBuilder.setMessage(
                        "MIT License\n" +
                                "\n" +
                                "Copyright (c) 2020 Refactoring UI Inc.\n" +
                                "\n" +
                                "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                                "of this software and associated documentation files (the \"Software\"), to deal\n" +
                                "in the Software without restriction, including without limitation the rights\n" +
                                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                                "copies of the Software, and to permit persons to whom the Software is\n" +
                                "furnished to do so, subject to the following conditions:\n" +
                                "\n" +
                                "The above copyright notice and this permission notice shall be included in all\n" +
                                "copies or substantial portions of the Software.\n" +
                                "\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                                "SOFTWARE."
                    )

                    alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                },
                content = {
                    Text(
                        text = "App Icon License",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )

        }

    }

}

@Composable
@Preview(showBackground = true)
fun SettingsScreen() {
    SettingsScreen(navController = rememberNavController())
}