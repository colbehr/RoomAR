package com.colbehr.roomar

sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object ARScreen : Screen(route = "ar_screen")
    object DataConfirmScreen : Screen(route = "data_confirm_screen")
    object DataViewScreen : Screen(route = "data_view_screen")
    object ExportScreen : Screen(route = "export_screen")
    object ExportViewScreen : Screen(route = "export_view_screen")
    object SettingsScreen : Screen(route = "settings_screen")
}
