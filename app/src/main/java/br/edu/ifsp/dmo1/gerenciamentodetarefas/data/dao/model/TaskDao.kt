package br.edu.ifsp.dmo1.gerenciamentodetarefas.data.dao.model

import br.edu.ifsp.dmo1.gerenciamentodetarefas.data.model.Task

object TaskDao {

    private var taskList: MutableList<Task> = mutableListOf()

    fun add(task: Task) {
        taskList.add(task)
    }

    fun getAll(): MutableList<Task> {
        //O sortBy retorna um Unit e não uma lista nova modificada, já que ele ordena in place
        taskList.sortBy { it.isCompleted }
        return taskList
    }


    fun get(id: Long): Task? {
        return taskList.stream()
            .filter{t -> t.id == id}
            .findFirst()
            .orElse(null)
    }

    fun getTarefasConcluidas() :MutableList<Task>{
        val concluidas : MutableList<Task>

        concluidas = taskList.filter { it.isCompleted }.toMutableList()

        return concluidas
    }

    fun getTarefasNaoConcluidas() :MutableList<Task>{
        val naoConcluidas : MutableList<Task>

        naoConcluidas = taskList.filter { !it.isCompleted }.toMutableList()

        return naoConcluidas
    }

}