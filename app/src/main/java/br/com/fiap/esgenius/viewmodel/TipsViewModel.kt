package br.com.fiap.esgenius.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.fiap.esgenius.model.InvestmentTip
import br.com.fiap.esgenius.model.TipCategory
import br.com.fiap.esgenius.repository.TipsRepository

data class TipsUiState(
    val allTips: List<InvestmentTip> = emptyList(),
    val query: String = "",
    val selectedCategories: Set<TipCategory> = emptySet()
) {
    val filteredTips: List<InvestmentTip>
        get() {
            val base = if (selectedCategories.isEmpty()) allTips
            else allTips.filter { it.category in selectedCategories }

            if (query.isBlank()) return base
            val q = query.trim().lowercase()
            return base.filter { tip ->
                tip.title.lowercase().contains(q) || tip.description.lowercase().contains(q)
            }
        }
}

class TipsViewModel : ViewModel() {
    var uiState by mutableStateOf(
        TipsUiState(allTips = TipsRepository.getTips())
    )
        private set

    fun onQueryChange(newQuery: String) {
        uiState = uiState.copy(query = newQuery)
    }

    fun toggleCategory(category: TipCategory) {
        val current = uiState.selectedCategories.toMutableSet()
        if (category in current) current.remove(category) else current.add(category)
        uiState = uiState.copy(selectedCategories = current)
    }

    fun clearFilters() {
        uiState = uiState.copy(query = "", selectedCategories = emptySet())
    }
}