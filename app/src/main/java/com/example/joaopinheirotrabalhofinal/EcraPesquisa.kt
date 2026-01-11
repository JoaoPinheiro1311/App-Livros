package com.example.joaopinheirotrabalhofinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.net.URLEncoder

@Composable
fun SearchScreen(onInfo: (String) -> Unit, onAdd: (Book) -> Unit) {
    var searchTerm by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(emptyList<Book>()) }
    val scope = rememberCoroutineScope()

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(searchTerm, { searchTerm = it }, Modifier.fillMaxWidth(), placeholder = { Text("Pesquisar novo livro...") })
        Button({ scope.launch { searchResults = callApi("search/${URLEncoder.encode(searchTerm.trim(), "UTF-8")}") } }, Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Procurar")
        }
        LazyColumn(Modifier.padding(top = 16.dp)) {
            items(searchResults) { b ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(b.cover, null, Modifier.size(40.dp, 60.dp), contentScale = ContentScale.Crop)
                        Text(b.name, Modifier.padding(start = 12.dp).weight(1f))
                        IconButton({ scope.launch { onInfo(callApiSingle(b.id)?.synopsis?.stripHtml() ?: "Sem sinopse.") } }) { Icon(Icons.Default.Info, null) }
                        IconButton({ onAdd(b) }) { Icon(Icons.Default.Add, null) }
                    }
                }
            }
        }
    }
}
