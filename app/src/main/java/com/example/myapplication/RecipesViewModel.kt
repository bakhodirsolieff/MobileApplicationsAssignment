package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.FlowPreview::class)
class RecipesViewModel : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    private val allRecipes = listOf(
        Recipe(1, "Takoyaki", "A Japanese octopus-filled ball", R.drawable.takoyaki),
        Recipe(2, "Tempura", "Deep-fried seafood or vegetables", R.drawable.tempura),
        Recipe(3, "Ramen", "A noodle soup with various toppings", R.drawable.ramen)
    )

    init {
        _recipes.value = allRecipes

        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .collect { query ->
                    filterRecipesInternal(query)
                }
        }
    }

    fun filterRecipes(query: String) {
        searchQuery.value = query
    }

    private fun filterRecipesInternal(query: String) {
        _recipes.value = if (query.length < 3) {
            allRecipes
        } else {
            allRecipes.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) ||
                        recipe.description.contains(query, ignoreCase = true)
            }
        }
    }
}