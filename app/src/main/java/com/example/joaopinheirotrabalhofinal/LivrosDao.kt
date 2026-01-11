package com.example.joaopinheirotrabalhofinal

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LivrosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserir(livro: LivrosEntity)

    @Update
    fun atualizar(livro: LivrosEntity)

    @Query("DELETE FROM livros WHERE id = :id")
    fun apagar(id: String)

    @Query("SELECT * FROM livros")
    fun listarTodos(): LiveData<List<LivrosEntity>>

    @Query("SELECT * FROM livros WHERE id = :id")
    fun procurarPorId(id: String): LivrosEntity?
}
