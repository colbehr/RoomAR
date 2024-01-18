package com.colbehr.roomar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

//https://www.youtube.com/watch?v=glyqjzkc4fk
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.ARScreen.route
        ) {
            ARScreen(navController = navController)
        }
        composable(
            route = Screen.DataConfirmScreen.route + "/{points}",
            arguments = listOf(navArgument("points") {
                type = NavType.StringType
            }
            )) {
            val points = it.arguments?.getString("points")!!
            DataConfirmScreen(navController = navController, points = points)
        }
        composable(
            route = Screen.DataViewScreen.route + "/{points}/{name}/{fileName}",
            arguments = listOf(
                navArgument("points") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("fileName") {
                    type = NavType.StringType
                }
            )) {
            val points = it.arguments?.getString("points")!!
            val fileName = it.arguments?.getString("fileName")!!
            val name = it.arguments?.getString("name")!!
            DataViewScreen(
                navController = navController,
                points = points,
                name = name,
                fileName = fileName
            )
        }
        composable(
            route = Screen.ExportScreen.route + "/{points}/{name}",
            arguments = listOf(
                navArgument("points") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                }
            )) {
            val points = it.arguments?.getString("points")!!
            val name = it.arguments?.getString("name")!!
            ExportScreen(navController = navController, points = points, name = name)
        }
        composable(
            route = Screen.ExportViewScreen.route + "/{points}/{name}/{fileName}",
            arguments = listOf(
                navArgument("points") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("fileName") {
                    type = NavType.StringType
                }
            )) {
            val points = it.arguments?.getString("points")!!
            val name = it.arguments?.getString("name")!!
            val fileName = it.arguments?.getString("fileName")!!
            ExportViewScreen(
                navController = navController,
                points = points,
                name = name,
                fileName = fileName
            )
        }
        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(navController = navController)
        }
    }
}