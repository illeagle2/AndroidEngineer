package com.example.androidengineer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import java.util.regex.Pattern

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var passwordFirst: EditText
    private lateinit var passwordSecond: EditText
    private lateinit var emailError: LinearLayout
    private lateinit var passwordError: LinearLayout
    private lateinit var passwordNotMatch: LinearLayout
    private lateinit var checkmarkEmail: ImageView
    private lateinit var checkmarkPassword: ImageView
    private lateinit var checkmarkPassword2: ImageView
    private lateinit var nextButton: RelativeLayout

    // sample list of emails
    private val registeredEmails = arrayOf("bob@gmail.com", "sam@gmail.com", "chris@gmail.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val backButton: RelativeLayout = findViewById(R.id.back_bar)
        email = findViewById(R.id.editTextEmailAddress)
        passwordFirst = findViewById(R.id.editTextPassword)
        passwordSecond = findViewById(R.id.editTextRepeatPassword)
        emailError = findViewById(R.id.llEmailError)
        passwordError = findViewById(R.id.llPasswordError)
        passwordNotMatch = findViewById(R.id.llPasswordNotMatch)
        checkmarkEmail = findViewById(R.id.checkmark_email)
        checkmarkPassword = findViewById(R.id.checkmark_password)
        checkmarkPassword2 = findViewById(R.id.checkmark_password2)
        nextButton = findViewById(R.id.rlButton)


        // Check for each field to be updated and updated the NEXT button if the
        // the entered information is valid
        email.setOnFocusChangeListener { view, b -> updateCheck(b) }
        passwordFirst.setOnFocusChangeListener { view, b -> updateCheck(b) }
        passwordSecond.setOnFocusChangeListener { view, b -> updateCheck(b) }


        //Navigate back
        backButton.setOnClickListener {
            finish()
        }



        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val emailText = "" + p0
                isValidEmail(emailText)

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        // add text changed listener for password fields to check if password is valid
        //based on constraints listed
        passwordFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int, before: Int, count: Int
            ) {
                s?.apply {
                    // check user input a valid formatted password
                    if (isValidPassword() && toString().length >= 8) {
                        passwordFirst.setBackgroundResource(R.drawable.edit_text_bg_green)
                        passwordError.visibility = View.GONE
                        checkmarkPassword.visibility = View.VISIBLE
                    } else {
                        // show error on input invalid password
                        passwordFirst.setBackgroundResource(R.drawable.edit_text_bg_red)
                        passwordError.visibility = View.VISIBLE
                        checkmarkPassword.visibility = View.INVISIBLE

                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        passwordSecond.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0.apply {
                    if (toString() == passwordFirst.text.toString()) {

                        passwordSecond.setBackgroundResource(R.drawable.edit_text_bg_green)
                        passwordNotMatch.visibility = View.GONE
                        checkmarkPassword2.visibility = View.VISIBLE

                    } else {
                        passwordSecond.setBackgroundResource(R.drawable.edit_text_bg_red)
                        passwordNotMatch.visibility = View.VISIBLE
                        checkmarkPassword2.visibility = View.INVISIBLE
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    private fun updateCheck(b: Boolean) {
        nextButton = findViewById(R.id.rlButton)
        if (checkmarkEmail.isVisible && checkmarkPassword.isVisible && checkmarkPassword2.isVisible) {
            nextButton.alpha = 255F
            nextButton.isEnabled = true
            nextButton.setOnClickListener {
                if (nextButton.isEnabled) {
                    Toast.makeText(this, "Email Saved", Toast.LENGTH_SHORT).show()
                } else {
                    nextButton.isEnabled = false
                }
            }
        }
    }


    //Validate email to database and for valid email naming conventions
    private fun isValidEmail(input: String) {

        if (registeredEmails.contains(input)) {
            //email is already registered
            email.setBackgroundResource(R.drawable.edit_text_bg_red)
            emailError.visibility = View.VISIBLE
            checkmarkEmail.visibility = View.INVISIBLE
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            //email entered dose not match emailtype
            email.setBackgroundResource(R.drawable.edit_text_bg_red)
            emailError.visibility = View.INVISIBLE
            checkmarkEmail.visibility = View.INVISIBLE
        } else {
            //good email
            email.setBackgroundResource(R.drawable.edit_text_bg_green)
            emailError.visibility = View.GONE
            checkmarkEmail.visibility = View.VISIBLE
        }

        }


    // extension function to validate password rules/patterns
    fun CharSequence.isValidPassword(): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }
}

