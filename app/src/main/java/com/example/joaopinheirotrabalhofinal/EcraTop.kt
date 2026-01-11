package com.example.joaopinheirotrabalhofinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun TopBooksScreen(vm: LivrosViewModel, onInfo: (String) -> Unit) {
    var searchYear by remember { mutableStateOf("2024") }
    var topBooks by remember { mutableStateOf(emptyList<Book>()) }
    val scope = rememberCoroutineScope()

    Column {
        OutlinedTextField(
            value = searchYear,
            onValueChange = { searchYear = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Melhores do Ano") },
            trailingIcon = {
                IconButton({ scope.launch { topBooks = callApi("top/$searchYear") } }) { Icon(Icons.Default.Search, null) }
            }
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
            items(topBooks) { b ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(b.cover, null, Modifier.size(50.dp, 75.dp), contentScale = ContentScale.Crop)
                        Text(b.name, Modifier.padding(start = 12.dp).weight(1f), maxLines = 1)
                        IconButton({ scope.launch { onInfo(callApiSingle(b.id)?.synopsis?.stripHtml() ?: "Sem sinopse.") } }) { Icon(Icons.Default.Info, null) }
                        IconButton({ vm.guardar(LivrosEntity(b.id, b.name, b.cover, "Vários", 4.5, searchYear.toIntOrNull() ?: 0)) }) { Icon(Icons.Default.Add, null) }
                    }
                }
            }
        }
    }
}
