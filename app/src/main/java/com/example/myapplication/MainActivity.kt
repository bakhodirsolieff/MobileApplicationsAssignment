package com.example.myapplication

import RecipeAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recipeAdapter: RecipeAdapter
    private val recipesViewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (CredentialsManager.isLoggedIn(this)) {
            loadMainContent()
            setupSearchView()
        } else {
            if (savedInstanceState == null) {
                navigateToCreateAccountFragment()
            }
        }

        setupLogoutButton()
    }

    private fun loadMainContent() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recipeAdapter = RecipeAdapter(
            recipes = emptyList(),
            itemClickListener = { recipe ->
                openRecipeDetail(recipe)
            },
            likeClickListener = { recipe ->
                Toast.makeText(this, "Liked: ${recipe.title}", Toast.LENGTH_SHORT).show()
            },
            shareClickListener = { recipe ->
                Toast.makeText(this, "Shared: ${recipe.title}", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = recipeAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipesViewModel.recipes.collect { updatedRecipes ->
                    recipeAdapter.updateRecipes(updatedRecipes)
                }
            }
        }

        recipesViewModel.filterRecipes("")
    }


    private fun openRecipeDetail(recipe: Recipe) {
        val intent = Intent(this, RecipeDetailActivity::class.java).apply {
            putExtra("RECIPE_ID", recipe.id)
            putExtra("RECIPE_TITLE", recipe.title)
            putExtra("RECIPE_IMAGE", recipe.imageResId)
        }
        startActivity(intent)
    }

    private fun setupLogoutButton() {
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            CredentialsManager.setLoggedIn(this, false)
            navigateToCreateAccountFragment()

            findViewById<RecyclerView>(R.id.recyclerView).visibility = View.GONE
            logoutButton.visibility = View.GONE
        }
    }

    private fun navigateToCreateAccountFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, CreateAccountFragment())
            .commit()
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    recipesViewModel.filterRecipes(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    recipesViewModel.filterRecipes(it)
                }
                return true
            }
        })
    }
}