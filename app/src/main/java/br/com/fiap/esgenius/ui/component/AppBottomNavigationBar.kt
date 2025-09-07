package br.com.fiap.esgenius.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import br.com.fiap.esgenius.BottomNavItem
import br.com.fiap.esgenius.Screen

@Composable
fun AppBottomNavigationBar(currentScreen: Screen, onNavigate: (Screen) -> Unit) {
    val navItems = listOf(
        BottomNavItem("Início", Icons.Default.Home, Screen.Home),
        BottomNavItem("Ranking", Icons.Default.Menu, Screen.CompanyList),
        BottomNavItem("Dicas", Icons.Default.Warning, Screen.InvestmentTips)
    )

    NavigationBar {
        navItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentScreen == item.screen ||
                        (item.screen == Screen.CompanyList && (currentScreen == Screen.Details || currentScreen == Screen.Historical)),
                onClick = { onNavigate(item.screen) }
            )
        }
    }
}