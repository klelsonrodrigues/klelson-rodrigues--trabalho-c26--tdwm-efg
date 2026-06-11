package com.example.smarttask.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.smarttask.model.Tarefa
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(context: Context) {
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "smarttask_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun setLogged(isLogged: Boolean) {
        prefs.edit().putBoolean("isLogged", isLogged).apply()
    }

    fun isLogged(): Boolean {
        return prefs.getBoolean("isLogged", false)
    }

    fun salvar(lista: MutableList<Tarefa>) {
        val json = Gson().toJson(lista)
        prefs.edit().putString("lista_tarefas", json).apply()
    }

    fun carregar(): MutableList<Tarefa> {
        val json = prefs.getString("lista_tarefas", null)
        return if (json != null) {
            val tipo = object : TypeToken<MutableList<Tarefa>>() {}.type
            Gson().fromJson(json, tipo)
        } else {
            mutableListOf()
        }
    }

    fun limparDados() {
        prefs.edit().clear().apply()
    }
}
