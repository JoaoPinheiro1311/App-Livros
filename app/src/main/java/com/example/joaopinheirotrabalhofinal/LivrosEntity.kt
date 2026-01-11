package com.example.joaopinheirotrabalhofinal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "livros")
data class LivrosEntity(
    @PrimaryKey val id: String,
    val name: String,
    val cover: String,
    val authors: String,
    val rating: Double,
    val year: Int,
    val percentage: Int = 0,
    val description: String = "",
    val favorito: Boolean = false
)
