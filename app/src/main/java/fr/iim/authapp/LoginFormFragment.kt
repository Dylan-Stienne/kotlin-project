package fr.iim.authapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class LoginFormFragment : Fragment() {
    private lateinit var listener: Listener

    interface Listener {
        fun onLogin(email: String);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException("$context needs to be implement the LoginListener interface.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            var error = false

            // Email input validation
            val emailInput = view.findViewById<EditText>(R.id.inputEmail)
            emailInput.setError(null)
            if (!emailInput.text.toString().matches(Regex("^[^\\@]+\\@[^\\@]+\\.[^\\@]+$"))) {
                emailInput.setError(getString(R.string.input_error_regex_email))
                error = true
            }
            if (emailInput.text.toString().isEmpty()) {
                emailInput.setError(getString(R.string.input_error_empty_field))
                error = true
            }

            // Password input validation
            val passwordInput = view.findViewById<EditText>(R.id.inputPassword)
            passwordInput.setError(null)
            if (!passwordInput.text.toString().matches(Regex("^(?=.*?[#?!@\$%^&*-]).+\$"))) {
                passwordInput.setError(getString(R.string.input_error_regex_password_no_special_char))
                error = true
            }
            if (!passwordInput.text.toString().matches(Regex("^(?=.*?[0-9]).+\$"))) {
                passwordInput.setError(getString(R.string.input_error_regex_password_no_number))
                error = true
            }
            if (!passwordInput.text.toString().matches(Regex("^(?=.*?[A-Z]).+\$"))) {
                passwordInput.setError(getString(R.string.input_error_regex_password_no_upper_letter))
                error = true
            }
            if (!passwordInput.text.toString().matches(Regex("^(?=.*?[a-z]).+\$"))) {
                passwordInput.setError(getString(R.string.input_error_regex_password_no_lower_letter))
                error = true
            }
            if (passwordInput.text.toString().length < 8) {
                passwordInput.setError(getString(R.string.input_error_regex_password_to_short))
                error = true
            }
            if (passwordInput.text.toString().isEmpty()) {
                passwordInput.setError(getString(R.string.input_error_empty_field))
                error = true
            }

            // No bot input validation
            val noBotInput = view.findViewById<CheckBox>(R.id.inputNoBot)
            noBotInput.setError(null)
            if (!noBotInput.isChecked()) {
                noBotInput.setError(getString(R.string.input_error_empty_field))
                error = true
            }

            // Check if form failed
            if(!error){
                listener.onLogin(emailInput.text.toString())
            }
        }
    }
}