package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class FragmentSampleActivity : AppCompatActivity(R.layout.activity_fragment_sample),
    LoginFragment.EventsListener {
    val credentialsManager = CredentialsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragment_sample)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.commit {
            val fragment = LoginFragment(credentialsManager)
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }

        findViewById<Button>(R.id.change_button).setOnClickListener {
            supportFragmentManager.commit {
                replace<FragmentB>(R.id.fragment_container_view)
            }

        }
    }

    override fun onRegisterPressed() {
        supportFragmentManager.commit {
            replace<LoginFragment>(R.id.fragment_container_view)
        }
    }
}
