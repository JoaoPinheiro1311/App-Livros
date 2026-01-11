package com.example.joaopinheirotrabalhofinal

import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.joaopinheirotrabalhofinal.ui.theme.JoaopinheirotrabalhofinalTheme
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.*

data class Book(
    @SerializedName("book_id") val id: String,
    val name: String,
    val cover: String,
    val authors: List<String>? = null,
    val rating: Double? = 0.0,
    val year: Int? = 0,
    val synopsis: String? = null
)

class MainActivity : ComponentActivity() {
    private val viewModel: LivrosViewModel by viewModels { 
        LivrosViewModelFactory(application) 
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JoaopinheirotrabalhofinalTheme {
                MainScreen(viewModel)
            }
        }
    }
}

fun String.stripHtml() = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString().trim()

private val httpClient = OkHttpClient()

suspend fun callApi(path: String): List<Book> = withContext(Dispatchers.IO) {
    val req = Request.Builder()
        .url("https://hapi-books.p.rapidapi.com/$path")
        .header("x-rapidapi-key", "aa0666c0a3msha5910c2a679c0c6p12c296jsn06f5324e2331")
        .header("x-rapidapi-host", "hapi-books.p.rapidapi.com")
        .build()
    val res = httpClient.newCall(req).execute().body?.string() ?: ""
    Gson().fromJson(res, object : TypeToken<List<Book>>() {}.type) ?: emptyList()
}

suspend fun callApiSingle(id: String): Book? = withContext(Dispatchers.IO) {
    val req = Request.Builder()
        .url("https://hapi-books.p.rapidapi.com/book/$id")
        .header("x-rapidapi-key", "aa0666c0a3msha5910c2a679c0c6p12c296jsn06f5324e2331")
        .header("x-rapidapi-host", "hapi-books.p.rapidapi.com")
        .build()
    val res = httpClient.newCall(req).execute().body?.string() ?: ""
    Gson().fromJson(res, Book::class.java)
}
