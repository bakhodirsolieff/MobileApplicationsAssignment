package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            loadMainContent()
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, CreateAccountFragment())
                    .commit()
            }
        }
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            navigateToCreateAccountFragment()

            findViewById<RecyclerView>(R.id.recyclerView).visibility = View.GONE
            findViewById<Button>(R.id.logoutButton).visibility = View.GONE
        }
    }

    fun loadMainContent() {
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

    private fun navigateToCreateAccountFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, CreateAccountFragment())
            .commit()
    }
}

data class Recipe(
    val id: Int,
    val title: String,
    val imageResId: Int
)

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val itemClickListener: (Recipe) -> Unit,
    private val likeClickListener: (Recipe) -> Unit,
    private val shareClickListener: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val btnLike: ImageButton = itemView.findViewById(R.id.btn_like)
        private val btnShare: ImageButton = itemView.findViewById(R.id.btn_share)

        fun bind(recipe: Recipe) {
            image.setImageResource(recipe.imageResId)
            title.text = recipe.title

            itemView.setOnClickListener { itemClickListener(recipe) }
            btnLike.setOnClickListener { likeClickListener(recipe) }
            btnShare.setOnClickListener { shareClickListener(recipe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size
}