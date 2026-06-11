package com.example.smarttask.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttask.R
import com.example.smarttask.adapter.TarefaAdapter
import com.example.smarttask.model.Tarefa
import com.example.smarttask.utils.Prefs

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var editTarefa: EditText
    private lateinit var btnAdicionar: Button
    private lateinit var btnLogout: Button
    private lateinit var adapter: TarefaAdapter
    private lateinit var prefs: Prefs
    private var lista = mutableListOf<Tarefa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        prefs = Prefs(this)

        if (!prefs.isLogged()) {
            redirecionarParaLogin()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inicializarInterface()
        configurarListaEDados()
        configurarCliques()
        verificarPermissaoNotificacao()
    }

    private fun verificarPermissaoNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    AlertDialog.Builder(this)
                        .setTitle("Permissão de Notificação")
                        .setMessage("As notificações são usadas para lembrar você de suas tarefas importantes. Deseja permitir?")
                        .setPositiveButton("Sim") { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        .setNegativeButton("Não", null)
                        .show()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notificações habilitadas", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "As notificações estão desativadas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inicializarInterface() {
        recycler = findViewById(R.id.recyclerTarefas)
        editTarefa = findViewById(R.id.editTarefa)
        btnAdicionar = findViewById(R.id.btnAdicionar)
        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun configurarListaEDados() {
        lista = prefs.carregar()
        
        adapter = TarefaAdapter(
            lista,
            onDelete = { position ->
                lista.removeAt(position)
                prefs.salvar(lista)
                adapter.notifyDataSetChanged()
            },
            onEdit = { position ->
                abrirDialogEdicao(position)
            }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    private fun configurarCliques() {
        btnAdicionar.setOnClickListener {
            val texto = editTarefa.text.toString().trim()
            if (texto.isNotEmpty()) {
                lista.add(Tarefa(texto))
                prefs.salvar(lista)
                adapter.notifyItemInserted(lista.size - 1)
                editTarefa.text.clear()
            }
        }

        btnLogout.setOnClickListener {
            prefs.setLogged(false)
            redirecionarParaLogin()
        }
    }

    private fun abrirDialogEdicao(position: Int) {
        val editText = EditText(this)
        editText.setText(lista[position].titulo)
        AlertDialog.Builder(this)
            .setTitle("Editar Tarefa")
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val novoTexto = editText.text.toString().trim()
                if (novoTexto.isNotEmpty()) {
                    lista[position].titulo = novoTexto
                    prefs.salvar(lista)
                    adapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun redirecionarParaLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
