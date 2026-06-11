package com.example.smarttask.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttask.R
import com.example.smarttask.utils.Prefs

class LoginActivity : AppCompatActivity() {

    private lateinit var editUsuario: EditText
    private lateinit var editSenha: EditText
    private lateinit var btnLogin: Button
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = Prefs(this)
        
        if (prefs.isLogged()) {
            irParaMain()
            return
        }

        setContentView(R.layout.activity_login)

        editUsuario = findViewById(R.id.editUsuario)
        editSenha = findViewById(R.id.editSenha)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val usuario = editUsuario.text.toString().trim()
            val senha = editSenha.text.toString().trim()

            if (usuario.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                if (usuario == "admin" && senha == "admin") {
                    prefs.setLogged(true)
                    irParaMain()
                } else {
                    Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun irParaMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
