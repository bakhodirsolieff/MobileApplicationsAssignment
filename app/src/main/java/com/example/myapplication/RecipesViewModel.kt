package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.allRecipes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.FlowPreview::class)
class RecipesViewModel : ViewModel() {

    private val _recipes = MutableStateFlow(allRecipes)
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .map { query ->
                    if (query.length < 3) {
                        allRecipes
                    } else {
                        allRecipes.filter { recipe ->
                            recipe.title.contains(query, ignoreCase = true) ||
                                    recipe.description.contains(query, ignoreCase = true)
                        }
                    }
                }
                .collect { filteredRecipes ->
                    _recipes.value = filteredRecipes
                }
        }
    }

    fun filterRecipes(query: String) {
        searchQuery.value = query
    }
}