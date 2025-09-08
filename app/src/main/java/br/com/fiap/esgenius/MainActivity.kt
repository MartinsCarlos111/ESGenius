package br.com.fiap.esgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.esgenius.model.Company
import br.com.fiap.esgenius.ui.component.AppBottomNavigationBar
import br.com.fiap.esgenius.ui.screen.*
import br.com.fiap.esgenius.ui.theme.ESGeniusTheme
import br.com.fiap.esgenius.viewmodel.CompanyViewModel
import br.com.fiap.esgenius.viewmodel.TipsViewModel

enum class Screen {
    Home, CompanyList, Details, Historical, InvestmentTips
}

data class BottomNavItem(
    val label: String, val icon: ImageVector, val screen: Screen
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ESGeniusTheme {
                val companyViewModel: CompanyViewModel = viewModel()
                var currentScreen by remember { mutableStateOf(Screen.Home) }
                var selectedCompany by remember { mutableStateOf<Company?>(null) }
                val tipsViewModel: TipsViewModel = viewModel()

                Scaffold(
                    bottomBar = {
                        AppBottomNavigationBar(
                            currentScreen = currentScreen,
                            onNavigate = { screen -> currentScreen = screen }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            Screen.Home -> HomeScreen(
                                onSearch = { currentScreen = Screen.CompanyList }
                            )
                            Screen.CompanyList -> {
                                val uiState = companyViewModel.companyUiState
                                ListCompaniesScreen(
                                    uiState = uiState,
                                    onCompanySelected = { company ->
                                        selectedCompany = company
                                        currentScreen = Screen.Details
                                    }
                                )
                            }
                            Screen.Details -> {
                                selectedCompany?.let { company ->
                                    DetailsScreen(
                                        company = company,
                                        onShowHistory = { currentScreen = Screen.Historical },
                                        onBack = { currentScreen = Screen.CompanyList }
                                    )
                                }
                            }
                            Screen.Historical -> {
                                selectedCompany?.let { company ->
                                    HistoricalScreen(
                                        company = company,
                                        onBack = { currentScreen = Screen.Details }
                                    )
                                }
                            }
                            Screen.InvestmentTips -> {
                                ResponsibleInvestmentScreen(tipsViewModel = tipsViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}