package fr.iim.authapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

private const val ARG_EMAIL = ""

class LoggedFragment : Fragment() {
    private var email: String? = null
    private lateinit var listener: Listener

    interface Listener {
        fun onSearch(city: String);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException("$context needs to be implement the LoginListener interface.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(ARG_EMAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logged, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Display hello message
        val helloText: TextView = view.findViewById<TextView>(R.id.helloText)
        helloText.text = getString(R.string.hello_email_template, email)

        // Lister when search button is clicked
        view.findViewById<Button>(R.id.buttonSearch).setOnClickListener {
            var error = false

            // City input validation
            val cityInput = view.findViewById<EditText>(R.id.inputCity)
            cityInput.setError(null)
            if (cityInput.text.toString().isEmpty()) {
                cityInput.setError(getString(R.string.input_error_empty_field))
                error = true
            }

            if(!error){
                listener.onSearch(cityInput.text.toString())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(email: String) =
            LoggedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EMAIL, email)
                }
            }
    }
}