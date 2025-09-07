package br.com.fiap.esgenius.ui.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.esgenius.model.Company
import br.com.fiap.esgenius.viewmodel.CompanyUiState

@Composable
fun ListCompaniesScreen(
    uiState: CompanyUiState,
    onCompanySelected: (Company) -> Unit
) {
    when (uiState) {
        is CompanyUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CompanyUiState.Success -> {
            val rankedCompanies = uiState.companies.sortedByDescending {
                it.latestESG.governanceScore + it.latestESG.environmentalScore
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "Ranking de Empresas Sustentáveis",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                itemsIndexed(rankedCompanies) { index, company ->
                    CompanyListItem(
                        rank = index + 1,
                        company = company,
                        onClick = { onCompanySelected(company) }
                    )
                }
            }
        }
        is CompanyUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Falha ao carregar os dados. Verifique sua conexão.")
            }
        }
    }
}

@Composable
fun CompanyListItem(rank: Int, company: Company, onClick: () -> Unit) {
    val sustainabilityScore = company.latestESG.governanceScore + company.latestESG.environmentalScore
    val logoColor = Color(android.graphics.Color.parseColor(company.logoColor))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#$rank",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape).background(logoColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = company.name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(company.name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Text("${company.sector} | ${company.country}", fontSize = 14.sp, color = Color.Gray)
            }
            Text("Score: $sustainabilityScore", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}