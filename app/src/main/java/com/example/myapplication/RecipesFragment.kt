package com.example.myapplication

import RecipeAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesFragment : Fragment(R.layout.fragment_recipes) {

    private lateinit var recipeAdapter: RecipeAdapter
    private val recipesViewModel: RecipesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("RecipesFragment", "Fragment view created")

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipesViewModel.uiState.collect { uiState ->
                    Log.d("RecipesFragment", "UIState collected: $uiState")
                    withContext(Dispatchers.Main) {
                        updateUIState(uiState)
                    }
                }
            }
        }


        setupLogoutButton(view)

        loadMainContent(view)

    }

    private fun setupLogoutButton(view: View) {
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            CredentialsManager.setLoggedIn(requireContext(), false)
            navigateToCreateAccountFragment()
            view.findViewById<RecyclerView>(R.id.recyclerView).visibility = View.GONE
            logoutButton.visibility = View.GONE
        }
    }

    private fun navigateToCreateAccountFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainerView, CreateAccountFragment())
            ?.commitAllowingStateLoss()
    }

    private fun loadMainContent(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recipeAdapter = RecipeAdapter(
            recipes = emptyList(),
            itemClickListener = { recipe -> openRecipeDetail(recipe) },
            likeClickListener = { recipe ->
                Toast.makeText(requireContext(), "Liked: ${recipe.title}", Toast.LENGTH_SHORT)
                    .show()
            },
            shareClickListener = { recipe ->
                Toast.makeText(requireContext(), "Shared: ${recipe.title}", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        recyclerView.adapter = recipeAdapter

        val searchView: SearchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    recipesViewModel.updateSearchQuery(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    recipesViewModel.updateSearchQuery(it)
                }
                return true
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipesViewModel.recipes.collect { updatedRecipes ->
                    recipeAdapter.updateRecipes(updatedRecipes)
                }
            }
        }
    }

    private fun openRecipeDetail(recipe: Recipe) {
        val intent = Intent(requireContext(), RecipeDetailActivity::class.java).apply {
            putExtra("RECIPE_ID", recipe.id)
            putExtra("RECIPE_TITLE", recipe.title)
            putExtra("RECIPE_IMAGE", recipe.imageResId)
        }
        startActivity(intent)
    }

    private fun updateUIState(state: RecipesUiState) {
        view?.let { view ->
            val progressIndicator: CircularProgressIndicator =
                view.findViewById(R.id.circularProgressIndicator)
            /*val progressIndicator: CircularProgressIndicator =
                view.findViewById(R.id.circularProgressIndicator)
            progressIndicator.visibility = View.VISIBLE*/
            when (state) {
                is RecipesUiState.Loading -> {
                    Log.d("RecipesFragment", "Setting progress indicator to visible")
                    progressIndicator.visibility = View.VISIBLE
                }

                is RecipesUiState.Success -> {
                    Log.d("RecipesFragment", "Setting progress indicator to gone")
                    progressIndicator.visibility = View.GONE
                    recipeAdapter.updateRecipes(state.recipes)
                }

                is RecipesUiState.Error -> {
                    Log.d("RecipesFragment", "Setting progress indicator to gone")
                    progressIndicator.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}