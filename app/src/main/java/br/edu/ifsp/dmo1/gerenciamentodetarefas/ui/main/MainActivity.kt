package br.edu.ifsp.dmo1.gerenciamentodetarefas.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo1.gerenciamentodetarefas.R
import br.edu.ifsp.dmo1.gerenciamentodetarefas.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.gerenciamentodetarefas.databinding.DialogNewTaskBinding
import br.edu.ifsp.dmo1.gerenciamentodetarefas.databinding.TasklistItemBinding
import br.edu.ifsp.dmo1.gerenciamentodetarefas.ui.adapter.TaskAdapter
import br.edu.ifsp.dmo1.gerenciamentodetarefas.ui.listener.TaskClickListener

class MainActivity : AppCompatActivity(), TaskClickListener, OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TaskAdapter
    private val valores  = arrayOf("Tarefas concluídas", "Tarefas a concluir", "Mostrar todas as tarefas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapterSpinner : ArrayAdapter<String> = ArrayAdapter(
            this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, valores
        )

        binding.spinnerId.adapter = adapterSpinner

        binding.spinnerId.onItemSelectedListener = this

        configListview()
        configOnClickListener()
        configObservers()

    }


   override fun clickDone(position: Int) {
        viewModel.updateTask(position)
    }


    private fun configListview() {
        adapter = TaskAdapter(this, mutableListOf(), this)
        binding.listItem.adapter = adapter
    }

    private fun configObservers() {
        viewModel.tasks.observe(this, Observer {
            adapter.updateTasks(it)
        })

        viewModel.insertTask.observe(this, Observer {
            val str: String = if (it) {
                getString(R.string.task_inserted_sucess)
            } else {
                getString(R.string.task_inserted_error)
            }
            Toast.makeText(this, str, Toast.LENGTH_LONG).show()
        })

        viewModel.updateTask.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    getString(R.string.task_update_sucess),
                    Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun configOnClickListener() {
        binding.add.setOnClickListener {
            openDialogNewTask()
        }
    }

    private fun openDialogNewTask() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_new_task, null)
        val bindingDialog = DialogNewTaskBinding.bind(dialogView)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle(getString(R.string.add_new_task))
            .setPositiveButton(
                getString(R.string.save),
                DialogInterface.OnClickListener { dialog, which ->
                    val description = bindingDialog.editDescription.text.toString()
                    viewModel.insertTask(description)
                    dialog.dismiss()
                })
            .setNegativeButton(
                getString(R.string.cancel),
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> viewModel.tarefasConcluidas()
            1 -> viewModel.tarefasNaoConcluidas()
            2 -> viewModel.pegaTodasTarefas()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}