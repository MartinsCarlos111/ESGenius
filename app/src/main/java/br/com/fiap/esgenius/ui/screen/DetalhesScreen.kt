package br.com.fiap.esgenius.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.esgenius.data.Company

// Função renomeada para DetailsScreen para seguir o padrão
@Composable
fun DetailsScreen(company: Company, onShowHistory: () -> Unit, onBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier.size(32.dp).clip(CircleShape).background(company.logoColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = company.name.first().toString(), color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Detalhes da Empresa", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(company.logoColor))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = company.name, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Setor: ${company.sector} | País: ${company.country}", fontSize = 14.sp, color = Color.Gray)
                        Text(text = "Código na bolsa: ${company.ticker}", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Ranking de Empresas Sustentáveis", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    // CORRIGIDO: de currentESG para latestESG
                    val sustainabilityScore = company.latestESG.governanceScore + company.latestESG.environmentalScore
                    Text(text = "Pontuação de Sustentabilidade: $sustainabilityScore", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Ambiental (E - Environmental)", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    // CORRIGIDO: de currentESG para latestESG
                    IndicatorDetail(label = "Emissões de CO₂", value = company.latestESG.emissions)
                    IndicatorDetail(label = "Uso de energia renovável", value = company.latestESG.renewableEnergy)
                    IndicatorDetail(label = "Gestão de resíduos", value = company.latestESG.wasteManagement)
                    IndicatorDetail(label = "Projetos de sustentabilidade", value = company.latestESG.sustainabilityProjects)
                }
            }
        }
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Social (S - Social)", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    // CORRIGIDO: de currentESG para latestESG
                    IndicatorDetail(label = "Políticas de diversidade", value = company.latestESG.diversityPolicies)
                    IndicatorDetail(label = "Saúde e segurança", value = company.latestESG.workerSafety)
                    IndicatorDetail(label = "Relacionamento com a comunidade", value = company.latestESG.communityRelations)
                }
            }
        }
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Governança (G - Governance)", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    // CORRIGIDO: de currentESG para latestESG
                    IndicatorDetail(label = "Estrutura do conselho", value = company.latestESG.boardStructure)
                    IndicatorDetail(label = "Transparência em relatórios", value = company.latestESG.transparency)
                    IndicatorDetail(label = "Políticas anticorrupção", value = company.latestESG.antiCorruption)
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Button(
                onClick = onShowHistory,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Ver Histórico ESG", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun IndicatorDetail(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.End)
    }
}