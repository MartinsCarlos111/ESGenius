package br.com.fiap.esgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.fiap.esgenius.data.*
import br.com.fiap.esgenius.ui.screen.*
import br.com.fiap.esgenius.ui.screen.DetailsScreen
import br.com.fiap.esgenius.ui.screen.ESGHistoryScreen
import br.com.fiap.esgenius.ui.screen.HomeScreen
import br.com.fiap.esgenius.ui.screen.ListCompaniesScreen
import br.com.fiap.esgenius.ui.screen.ResponsibleInvestmentScreen
import com.google.android.gms.games.leaderboard.Leaderboard

enum class Screen {
    Home,
    CompanyList,
    Details,
    Historical,
    InvestmentTips
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                var currentScreen by remember { mutableStateOf(Screen.Home) }
                var selectedCompany by remember { mutableStateOf<Company?>(null) }

                Scaffold(
                    bottomBar = {
                        AppBottomNavigationBar(
                            currentScreen = currentScreen,
                            onNavigate = { screen ->
                                // Navega para a tela, mas não limpa a empresa selecionada
                                // para poder voltar para Detalhes/Histórico se necessário.
                                currentScreen = screen
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            Screen.Home -> HomeScreen(
                                onSearch = { currentScreen = Screen.CompanyList }
                            )
                            Screen.CompanyList -> ListCompaniesScreen(
                                companies = mockCompanyList,
                                onCompanySelected = { company ->
                                    selectedCompany = company
                                    currentScreen = Screen.Details
                                }
                            )
                            Screen.Details -> {
                                // A exclamação dupla (!!) é segura aqui, pois só chegamos
                                // nesta tela após selecionar uma empresa.
                                DetailsScreen(
                                    company = selectedCompany!!,
                                    onShowHistory = { currentScreen = Screen.Historical },
                                    onBack = { currentScreen = Screen.CompanyList }
                                )
                            }
                            Screen.Historical -> {
                                ESGHistoryScreen(
                                    company = selectedCompany!!,
                                    sectorHistory = mockSectorAverage,
                                    onBack = { currentScreen = Screen.Details }
                                )
                            }
                            Screen.InvestmentTips -> {
                                ResponsibleInvestmentScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

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