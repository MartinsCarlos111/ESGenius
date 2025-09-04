package br.com.fiap.esgenius.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onSearch: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Encontre Empresas Sustentáveis",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(48.dp))

        // Simulação de filtros
        FilterSection(title = "Setor", options = listOf("Tecnologia", "Indústria", "Todos"))
        Spacer(modifier = Modifier.height(24.dp))
        FilterSection(title = "País", options = listOf("Brasil", "EUA", "Global"))
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onSearch,
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Buscar Empresas", fontSize = 16.sp)
        }
    }
}

@Composable
fun FilterSection(title: String, options: List<String>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { option ->
                Button(onClick = { /* Lógica de filtro aqui */ }, elevation = ButtonDefaults.buttonElevation(2.dp)) {
                    Text(option)
                }
            }
        }
    }
}