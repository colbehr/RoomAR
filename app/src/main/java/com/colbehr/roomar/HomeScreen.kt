package com.colbehr.roomar

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val openWelcomeDialog = remember { mutableStateOf(false) }
    setFirstRunFlag(context, false)
    val allFilesAndContents: Map<String, PointData> = getAllFilesContentInInternalStorage(context)

    var query by remember { mutableStateOf("") }
    var filteredFiles by remember { mutableStateOf(allFilesAndContents) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        BasicText(
//            modifier = Modifier.padding(0.dp).fillMaxWidth().zIndex(1.2f),
//            text = "RoomAR",
//            style = TextStyle(
//                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.W400,
//            )
//        )

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            query = query,
            onQueryChange = {
                query = it
                filteredFiles = filterFiles(allFilesAndContents, it)
            },
            onSearch = {},
            active = false,
            onActiveChange = {},
            tonalElevation = 16.dp,

            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(
                    onClick = { navController.navigate(Screen.SettingsScreen.route) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                }
            },

            ) {


        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(140.dp))
            for ((fileName, fileContents) in filteredFiles) {
                if (fileName.endsWith(".json")) {
                    HomeListItem(label = fileContents.name, pointData = fileContents.pointsString,
                        onItemClick = {
                            // Navigate to DataViewScreen
                            navController.navigate(
                                Screen.DataViewScreen.route + "/" + fileContents.pointsString + "/" + URLEncoder.encode(
                                    fileContents.name.trim(),
                                    "UTF-8"
                                ) + "/" + fileName
                            )
                        },
                        onExportClick = {
                            // Navigate to ExportViewScreen
                            navController.navigate(
                                Screen.ExportViewScreen.route + "/" + fileContents.pointsString + "/" + URLEncoder.encode(
                                    fileContents.name.trim(),
                                    "UTF-8"
                                ) + "/" + fileName
                            )
                        })
                }
            }
            Spacer(modifier = Modifier.height(72.dp))
        }

        FloatingActionButton(
            onClick = { navController.navigate(route = Screen.ARScreen.route) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)

        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }
    }
    when {
        openWelcomeDialog.value -> {
            WelcomeDialog(
                title = "Welcome To Room AR",
                body = "Effortlessly convert rooms to point data using AR technology. Simplify your workflow, save time, create more, faster. Please reach out if you have any feedback!\n" +
                        "\n" +
                        "Thank you!",
                onClose = { openWelcomeDialog.value = false },
            )
        }
    }
}


// Function to filter files based on the query
fun filterFiles(files: Map<String, PointData>, query: String): Map<String, PointData> {
    return files.filter { (_, pointData) ->
        pointData.name.contains(query, ignoreCase = true)
    }
}

fun isFirstRun(context: Context): Boolean {
    val prefs: SharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    return prefs.getBoolean("firstRun", true)
}

fun setFirstRunFlag(context: Context, isFirstRun: Boolean) {
    val prefs: SharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    prefs.edit {
        putBoolean("firstRun", isFirstRun)
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}

