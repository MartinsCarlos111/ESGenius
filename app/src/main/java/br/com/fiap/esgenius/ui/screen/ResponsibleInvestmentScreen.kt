package br.com.fiap.esgenius.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResponsibleInvestmentScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                "Dicas de Investimento Responsável",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            TipCard(
                title = "1. Entenda o que é ESG",
                content = "ESG significa Environmental (Ambiental), Social e Governance (Governança). São três pilares usados para medir a sustentabilidade e o impacto ético de um investimento em uma empresa."
            )
        }
        item {
            TipCard(
                title = "2. Pesquise os Ratings ESG",
                content = "Use ferramentas como a nossa para verificar a pontuação ESG das empresas. Ratings altos geralmente indicam melhores práticas de sustentabilidade e menor risco a longo prazo."
            )
        }
        item {
            TipCard(
                title = "3. Diversifique seus Investimentos ESG",
                content = "Não coloque todo o seu capital em uma única empresa ou setor. Diversificar ajuda a mitigar riscos e permite que você apoie a sustentabilidade em várias frentes da economia."
            )
        }
        item {
            TipCard(
                title = "4. Pense a Longo Prazo",
                content = "Investimento responsável é uma maratona, não uma corrida. Empresas com fortes práticas ESG tendem a ser mais resilientes e a performar melhor ao longo do tempo."
            )
        }
    }
}

@Composable
fun TipCard(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(content, fontSize = 16.sp, lineHeight = 24.sp)
        }
    }
}