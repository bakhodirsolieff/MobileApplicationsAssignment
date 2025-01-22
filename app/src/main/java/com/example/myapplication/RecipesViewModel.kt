@file:OptIn(FlowPreview::class)

package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.allRecipes
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(allRecipes)
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _uiState = MutableStateFlow<RecipesUiState>(RecipesUiState.Loading)
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .collect { query ->
                    _uiState.value = RecipesUiState.Loading
                    delay(1000)
                    val filteredRecipes = if (query.length < 3) {
                        allRecipes
                    } else {
                        allRecipes.filter {
                            it.title.contains(query, ignoreCase = true) ||
                                    it.description.contains(query, ignoreCase = true)
                        }
                    }

                    _recipes.value = filteredRecipes
                    if (filteredRecipes.isEmpty()) {
                        _uiState.value = RecipesUiState.Error("No recipes found.")
                    } else {
                        _uiState.value = RecipesUiState.Success(filteredRecipes)
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}

sealed class RecipesUiState {
    object Loading : RecipesUiState()
    data class Success(val recipes: List<Recipe>) : RecipesUiState()
    data class Error(val message: String) : RecipesUiState()
}