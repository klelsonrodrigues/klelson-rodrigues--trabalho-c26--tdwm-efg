package com.example.smarttask.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttask.R
import com.example.smarttask.model.Tarefa
class TarefaAdapter(
    private val lista: MutableList<Tarefa>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Int) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {
    class TarefaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo = view.findViewById<TextView>(R.id.textTitulo)
        val check = view.findViewById<CheckBox>(R.id.checkConcluida)
        val excluir = view.findViewById<Button>(R.id.btnExcluir)
        val editar = view.findViewById<Button>(R.id.btnEditar)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TarefaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }
    override fun getItemCount() = lista.size
    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = lista[position]
        holder.titulo.text = tarefa.titulo
        holder.check.isChecked = tarefa.concluida
        holder.check.setOnCheckedChangeListener { _, isChecked ->
            tarefa.concluida = isChecked
        }
        holder.excluir.setOnClickListener {
            onDelete(position)
        }
        holder.editar.setOnClickListener {
            onEdit(position)
        }
    }
}