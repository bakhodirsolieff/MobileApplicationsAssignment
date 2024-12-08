package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class LoginFragment(val credentialsManager : CredentialsManager) : Fragment() {

    private var listener: EventsListener? = null

    interface EventsListener {
        fun onRegisterPressed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        require(
            context is EventsListener,
            { "Activity holding fragment must implement its EventsInterface " }
        )
        listener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_account, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnNext).setOnClickListener {
            listener?.onRegisterPressed()
        }
    }
}