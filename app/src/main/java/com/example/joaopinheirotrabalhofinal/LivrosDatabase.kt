package com.example.joaopinheirotrabalhofinal

import android.content.Context
import androidx.room.*

@Database(entities = [LivrosEntity::class], version = 7, exportSchema = false)
abstract class LivrosDatabase : RoomDatabase() {
    abstract fun livrosDao(): LivrosDao
    
    companion object {
        @Volatile private var INSTANCE: LivrosDatabase? = null
        
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                LivrosDatabase::class.java,
                "livros_db"
            ).fallbackToDestructiveMigration()
             .build()
             .also { INSTANCE = it }
        }
    }
}
