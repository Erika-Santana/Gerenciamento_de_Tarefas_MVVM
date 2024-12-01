package br.edu.ifsp.dmo1.gerenciamentodetarefas.data.dao.model

import br.edu.ifsp.dmo1.gerenciamentodetarefas.data.model.Task

object TaskDao {

    private var taskList: MutableList<Task> = mutableListOf()

    fun add(task: Task) {
        taskList.add(task)
    }

    fun getAll() = taskList

    fun get(id: Long): Task? {
        return taskList.stream()
            .filter{t -> t.id == id}
            .findFirst()
            .orElse(null)
    }

}