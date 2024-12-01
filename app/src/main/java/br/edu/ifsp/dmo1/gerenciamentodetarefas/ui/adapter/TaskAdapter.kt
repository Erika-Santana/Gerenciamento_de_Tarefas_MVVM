package br.edu.ifsp.dmo1.gerenciamentodetarefas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import br.edu.ifsp.dmo1.gerenciamentodetarefas.R
import br.edu.ifsp.dmo1.gerenciamentodetarefas.data.model.Task
import br.edu.ifsp.dmo1.gerenciamentodetarefas.databinding.TasklistItemBinding
import br.edu.ifsp.dmo1.gerenciamentodetarefas.ui.listener.TaskClickListener

class TaskAdapter(context : Context, private var my_tasks :List<Task>, private val clickListener : TaskClickListener) : ArrayAdapter<Task>(context, R.layout.tasklist_item, my_tasks)
{
    //updateList É chamado toda vez que um obejto novo é adicionado
    fun updateTasks(newTask: List<Task>){
        my_tasks = newTask
        clear()
        addAll(my_tasks)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding : TasklistItemBinding


        if (convertView == null){
            binding = TasklistItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            binding.root.tag = binding
        }else{
            binding = convertView.tag as TasklistItemBinding
        }

        val task = getItem(position)
        if (task != null) {
            binding.textTaskDescription.text = task.descricaoTarefa
            if (task.isCompleted) {
                binding.imageDone.setColorFilter(ContextCompat.getColor(context, R.color.green))
            } else {
                binding.imageDone.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }
            binding.imageDone.setOnClickListener {
                clickListener.clickDone(position)
            }
        }

        return binding.root
    }


}