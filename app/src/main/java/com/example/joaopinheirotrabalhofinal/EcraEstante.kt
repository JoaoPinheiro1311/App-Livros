package com.example.joaopinheirotrabalhofinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(vm: LivrosViewModel) {
    val books by vm.livros.observeAsState(emptyList())
    var currentTab by remember { mutableIntStateOf(0) }
    var showSearchSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedSynopsis by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(currentTab == 0, { currentTab = 0 }, { Icon(Icons.AutoMirrored.Filled.List, null) }, label = { Text("Estante") })
                NavigationBarItem(currentTab == 1, { currentTab = 1 }, { Icon(Icons.Default.Favorite, null) }, label = { Text("Favoritos") })
                NavigationBarItem(currentTab == 2, { currentTab = 2 }, { Icon(Icons.Default.Star, null) }, label = { Text("Top") })
                NavigationBarItem(false, { showSearchSheet = true }, { Icon(Icons.Default.Add, null) }, label = { Text("Novo") })
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            if (currentTab < 2) {
                Text(if (currentTab == 0) "Estante" else "Favoritos", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    placeholder = { Text("Procurar na lista...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) }
                )

                val filtered = books.filter { (currentTab == 0 || it.favorito) && it.name.contains(searchQuery, true) }
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(filtered) { book -> BookItemCard(book, vm) }
                }
            } else {
                TopBooksScreen(vm) { selectedSynopsis = it }
            }
        }

        if (showSearchSheet) ModalBottomSheet({ showSearchSheet = false }) {
            SearchScreen(onInfo = { selectedSynopsis = it }) { book ->
                vm.guardar(LivrosEntity(book.id, book.name, book.cover, book.authors?.joinToString(",") ?: "", book.rating ?: 0.0, book.year ?: 0))
                showSearchSheet = false
            }
        }

        if (selectedSynopsis != null) {
            AlertDialog(
                onDismissRequest = { selectedSynopsis = null },
                confirmButton = { TextButton({ selectedSynopsis = null }) { Text("Fechar") } },
                title = { Text("Sinopse") },
                text = { Text(selectedSynopsis!!) }
            )
        }
    }
}
