package br.edu.ifsp.dmo1.gerenciamentodetarefas.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.gerenciamentodetarefas.data.dao.model.TaskDao
import br.edu.ifsp.dmo1.gerenciamentodetarefas.data.model.Task

class MainViewModel : ViewModel() {
    private val dao = TaskDao

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() {
            return _tasks
        }

    private val _insertTask = MutableLiveData<Boolean>()
    val insertTask: LiveData<Boolean> = _insertTask

    private val _updateTask = MutableLiveData<Boolean>()
    val updateTask: LiveData<Boolean>
        get() = _updateTask

    init {
        mock()
        load(0)
    }

    fun pegaTodasTarefas(){
        load(0)
    }

    fun tarefasConcluidas(){
      load(1)
    }

    fun tarefasNaoConcluidas(){
        load(2)
    }

    fun insertTask(description: String) {
        val task = Task(description, false)
        dao.add(task)
        _insertTask.value = true
        load(0)
    }

    fun updateTask(position: Int) {
        val task = dao.getAll()[position]
        task.isCompleted = !task.isCompleted
        _updateTask.value = true
        load(0)
    }

    private fun mock() {
        dao.add(Task("Arrumar a Cama", false))
        dao.add(Task("Retirar o lixo", false))
        dao.add(Task("Fazer trabalho de DMO1", true))
    }

    private fun load(valor: Int) {
        when (valor) {
            0 -> _tasks.value = dao.getAll()
            1 -> _tasks.value = dao.getTarefasConcluidas()
            2 -> _tasks.value = dao.getTarefasNaoConcluidas()
        }
    }

}