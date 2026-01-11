package com.example.joaopinheirotrabalhofinal

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class LivrosRepository(private val dao: LivrosDao) {
    val todosLivros: LiveData<List<LivrosEntity>> = dao.listarTodos()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun inserir(livro: LivrosEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.inserir(livro)
        }
    }
    
    fun apagar(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.apagar(id)
        }
    }

    private fun atualizar(id: String, transform: (LivrosEntity) -> LivrosEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.procurarPorId(id)?.let { dao.atualizar(transform(it)) }
        }
    }

    fun atualizarProgresso(id: String, valor: Int) = 
        atualizar(id) { it.copy(percentage = valor) }

    fun atualizarNota(id: String, texto: String) = 
        atualizar(id) { it.copy(description = texto) }

    fun alternarFavorito(id: String) = 
        atualizar(id) { it.copy(favorito = !it.favorito) }
}
