package com.example.myapplication

import com.example.myapplication.RecipeAdapter
import com.example.myapplication.Recipe
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isLoggedIn = CredentialsManager.isLoggedIn(this)

        if (isLoggedIn) {
            loadMainContent()
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

        val recipes = listOf(
            Recipe(1, "Takoyaki", R.drawable.takoyaki),
            Recipe(2, "Tempura", R.drawable.tempura),
            Recipe(3, "Ramen", R.drawable.ramen)
        )

        val adapter = RecipeAdapter(
            recipes,
            itemClickListener = { recipe ->
                val intent = Intent(this, RecipeDetailActivity::class.java).apply {
                    putExtra("RECIPE_ID", recipe.id)
                    putExtra("RECIPE_TITLE", recipe.title)
                    putExtra("RECIPE_IMAGE", recipe.imageResId)
                }
                startActivity(intent)
            },
            likeClickListener = { recipe ->
                Toast.makeText(this, "Liked: ${recipe.title}", Toast.LENGTH_SHORT).show()
            },
            shareClickListener = { recipe ->
                Toast.makeText(this, "Shared: ${recipe.title}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter
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
}