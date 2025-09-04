package br.com.fiap.esgenius.data

import androidx.compose.ui.graphics.Color

data class ESGData(
    val date: String,
    val governanceScore: Int,
    val environmentalScore: Int,
    val emissions: String = "N/A",
    val renewableEnergy: String = "N/A",
    val wasteManagement: String = "N/A",
    val sustainabilityProjects: String = "N/A",
    val diversityPolicies: String = "N/A",
    val workerSafety: String = "N/A",
    val communityRelations: String = "N/A",
    val boardStructure: String = "N/A",
    val transparency: String = "N/A",
    val antiCorruption: String = "N/A"
)

data class Company(
    val id: Int,
    val name: String,
    val sector: String,
    val country: String,
    val ticker: String,
    val logoColor: Color,
    val latestESG: ESGData,
    val history: List<ESGData>
)

val mockCompanyList = listOf(
    Company(
        id = 1,
        name = "EcoTech Solutions",
        sector = "Tecnologia",
        country = "Brasil",
        ticker = "ECOT3",
        logoColor = Color(0xFF2E7D32), // Verde
        latestESG = ESGData("09/2025", governanceScore = 28, environmentalScore = 35),
        history = listOf(
            ESGData("01/2024", 25, 30),
            ESGData("04/2024", 26, 32),
            ESGData("07/2024", 28, 35),
        )
    ),
    Company(
        id = 2,
        name = "Global Steel Corp",
        sector = "Indústria",
        country = "Brasil",
        ticker = "GLST4",
        logoColor = Color(0xFF616161), // Cinza
        latestESG = ESGData("09/2025", governanceScore = 18, environmentalScore = 22),
        history = listOf(
            ESGData("01/2024", 15, 20),
            ESGData("04/2024", 17, 21),
            ESGData("07/2024", 18, 22),
        )
    ),
    Company(
        id = 3,
        name = "Varejo Rápido S.A.",
        sector = "Varejo",
        country = "Brasil",
        ticker = "VRAP3",
        logoColor = Color(0xFFD32F2F), // Vermelho
        latestESG = ESGData("09/2025", governanceScore = 12, environmentalScore = 15),
        history = listOf(
            ESGData("01/2024", 10, 18),
            ESGData("04/2024", 11, 16),
            ESGData("07/2024", 12, 15),
        )
    ),
    Company(
        id = 4,
        name = "Agro Forte Brasil",
        sector = "Agronegócio",
        country = "Brasil",
        ticker = "AGRO3",
        logoColor = Color(0xFF7CB342), // Verde claro
        latestESG = ESGData("09/2025", governanceScore = 25, environmentalScore = 29),
        history = listOf(
            ESGData("01/2024", 22, 25),
            ESGData("04/2024", 24, 28),
            ESGData("07/2024", 25, 29),
        )
    )
)

// Média do setor para usar nos gráficos
val mockSectorAverage = listOf(
    ESGData("01/2024", 19, 22),
    ESGData("04/2024", 20, 23),
    ESGData("07/2024", 21, 24),
)