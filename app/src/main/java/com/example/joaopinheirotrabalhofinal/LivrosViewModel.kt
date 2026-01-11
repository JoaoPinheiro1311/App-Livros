package com.example.joaopinheirotrabalhofinal

import android.app.Application
import androidx.lifecycle.*

class LivrosViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: LivrosRepository
    val livros: LiveData<List<LivrosEntity>>

    init {
        val dao = LivrosDatabase.getInstance(app).livrosDao()
        repository = LivrosRepository(dao)
        livros = repository.todosLivros
    }

    fun guardar(livro: LivrosEntity) {
        repository.inserir(livro)
    }

    fun atualizarProgresso(id: String, valor: Int) {
        repository.atualizarProgresso(id, valor)
    }

    fun atualizarNota(id: String, texto: String) {
        repository.atualizarNota(id, texto)
    }

    fun alternarFavorito(id: String) {
        repository.alternarFavorito(id)
    }

    fun apagar(id: String) {
        repository.apagar(id)
    }
}

class LivrosViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LivrosViewModel(application) as T
    }
}
