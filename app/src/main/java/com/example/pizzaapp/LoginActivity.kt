package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance text
        val txtUsername:EditText = findViewById(R.id.editTextEmail)
        val txtPassword:EditText = findViewById(R.id.editTextPassword)
        //instance button login
        val btnLogin:Button = findViewById(R.id.buttonLogin)

        //event button login
        btnLogin.setOnClickListener {
            //object class DatabaseHelper
            val databaseHelper = DatabaseHelper(this)

            val data:String = databaseHelper.checkData("stevi.ema@amikom.ac.id")
            Toast.makeText(this@LoginActivity, "Result + " + data,
                Toast.LENGTH_SHORT).show()
            if (data == null){
                databaseHelper.addAccount("stevi.ema@amikom.ac.id",
                "Stevi Ema W", "Cashier", "12345")
            }

            val email = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString().trim()

                //check login
            val result:Boolean = databaseHelper.checkLogin(email, password)
            if (result){
                Toast.makeText(this@LoginActivity, "Login Sukses",
                Toast.LENGTH_SHORT).show()
                val IntentLogin = Intent(this@LoginActivity,
                MainActivity::class.java)
                startActivity(IntentLogin)
            }else{
                Toast.makeText(this@LoginActivity,"Login Failed, Try Again !!!1",
                Toast.LENGTH_SHORT).show()
            }
            val intentLogin = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intentLogin)
        }

    }
}
