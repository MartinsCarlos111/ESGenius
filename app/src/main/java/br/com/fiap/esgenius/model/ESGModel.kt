package br.com.fiap.esgenius.model

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
    val logoColor: String,
    val latestESG: ESGData,
    val history: List<ESGData>
)

enum class TipCategory { GOVERNANCE, ENVIRONMENTAL, RISK, DIVERSIFICATION, EDUCATION }

data class InvestmentTip(
    val id: Int,
    val title: String,
    val description: String,
    val category: TipCategory,
    val sources: List<String> = emptyList()
)