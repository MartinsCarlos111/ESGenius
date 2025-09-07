package br.com.fiap.esgenius.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.esgenius.model.Company
import br.com.fiap.esgenius.model.ESGData

@Composable
fun HistoricalScreen(company: Company, onBack: () -> Unit) {
    val logoColor = Color(android.graphics.Color.parseColor(company.logoColor))

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(logoColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = company.name.first().toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Histórico ESG - ${company.name}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            HighlightsCard(history = company.history)
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Text(
                text = "Gráfico de Pontuações ESG (Linha)",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ESGScoresLineChart(companyHistory = company.history)
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Text(
                text = "Comparação Trimestral (Barras)",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ESGBarChart(history = company.history)
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        // CORREÇÃO: Usando argumento nomeado para evitar ambiguidade
        items(items = company.history) { data ->
            HistoryItem(data)
        }
    }
}

// ... O restante do arquivo (HighlightsCard, ESGScoresLineChart, etc.) continua o mesmo
// (Código dos outros composables omitido para brevidade)

@Composable
fun HighlightsCard(history: List<ESGData>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Destaques", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            var maxIncrease = 0
            var maxDecrease = 0
            var increasePeriod = ""
            var decreasePeriod = ""

            for (i in 1 until history.size) {
                val change = history[i].governanceScore - history[i - 1].governanceScore
                if (change > maxIncrease) {
                    maxIncrease = change
                    increasePeriod = "${history[i - 1].date} -> ${history[i].date}"
                }
                if (change < maxDecrease) {
                    maxDecrease = change
                    decreasePeriod = "${history[i - 1].date} -> ${history[i].date}"
                }
            }

            if (maxIncrease > 0) {
                Text(
                    "Maior crescimento em Governança: +$maxIncrease pontos ($increasePeriod)",
                    color = Color(0xFF008000),
                    fontSize = 14.sp
                )
            }
            if (maxDecrease < 0) {
                Text(
                    "Maior queda em Governança: $maxDecrease pontos ($decreasePeriod)",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ESGScoresLineChart(companyHistory: List<ESGData>) {
    val textMeasurer = rememberTextMeasurer()
    val maxScore = 40f
    val paddingHorizontal = 32.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                val totalWidth = size.width - paddingHorizontal.toPx() * 2
                val totalHeight = size.height - 40.dp.toPx()
                val pointCount = companyHistory.size
                val xStep = if (pointCount > 1) totalWidth / (pointCount - 1) else 0f

                val companyPathGov = Path()
                val companyPathEnv = Path()

                companyHistory.forEachIndexed { index, data ->
                    val x = paddingHorizontal.toPx() + (index * xStep)
                    val governanceY =
                        totalHeight * (1 - (data.governanceScore / maxScore)) + 20.dp.toPx()
                    val environmentalY =
                        totalHeight * (1 - (data.environmentalScore / maxScore)) + 20.dp.toPx()

                    if (index == 0) {
                        companyPathGov.moveTo(x, governanceY)
                        companyPathEnv.moveTo(x, environmentalY)
                    } else {
                        companyPathGov.lineTo(x, governanceY)
                        companyPathEnv.lineTo(x, environmentalY)
                    }
                    drawCircle(
                        color = Color.Red,
                        radius = 4.dp.toPx(),
                        center = Offset(x, governanceY)
                    )
                    drawCircle(
                        color = Color.Green,
                        radius = 4.dp.toPx(),
                        center = Offset(x, environmentalY)
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = data.date,
                        topLeft = Offset(x - 20.dp.toPx(), size.height - 20.dp.toPx()),
                        style = TextStyle(fontSize = 10.sp)
                    )
                }

                drawPath(path = companyPathGov, color = Color.Red, style = Stroke(width = 2.dp.toPx()))
                drawPath(
                    path = companyPathEnv,
                    color = Color.Green,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(color = Color.Red, label = "Governança")
                Spacer(modifier = Modifier.width(8.dp))
                LegendItem(color = Color.Green, label = "Ambiental")
            }
        }
    }
}

@Composable
fun ESGBarChart(history: List<ESGData>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(color = Color.Red, label = "Governança")
                Spacer(modifier = Modifier.width(8.dp))
                LegendItem(color = Color.Green, label = "Ambiental")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                history.forEach { data ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Box(
                                modifier = Modifier
                                    .height(data.governanceScore.dp * 3f)
                                    .width(20.dp)
                                    .background(Color.Red)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Box(
                                modifier = Modifier
                                    .height(data.environmentalScore.dp * 3f)
                                    .width(20.dp)
                                    .background(Color.Green)
                            )
                        }
                        Text(
                            text = data.date,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun HistoryItem(data: ESGData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Data: ${data.date}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Governança: ${data.governanceScore}", fontSize = 14.sp)
            Text(text = "Meio Ambiente: ${data.environmentalScore}", fontSize = 14.sp)
        }
    }
}