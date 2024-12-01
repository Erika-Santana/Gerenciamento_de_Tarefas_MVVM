package br.edu.ifsp.dmo1.gerenciamentodetarefas.data.model

class Task(var descricaoTarefa : String, var isCompleted : Boolean) {

    /*Parte Static da classe*/
    private companion object{
        var lastId : Long = 1L
    }

    var id: Long = 0L

    init {
        id = lastId
        lastId += 1
    }

}