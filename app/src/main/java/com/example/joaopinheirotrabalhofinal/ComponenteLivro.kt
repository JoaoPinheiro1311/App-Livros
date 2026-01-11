package com.example.joaopinheirotrabalhofinal

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun BookItemCard(book: LivrosEntity, vm: LivrosViewModel) {
    var notes by remember { mutableStateOf(book.description) }
    var showDeleteAlert by remember { mutableStateOf(false) }
    val bgColor = if (book.percentage == 100) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant

    Card(elevation = CardDefaults.cardElevation(4.dp), colors = CardDefaults.cardColors(bgColor)) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(book.cover, null, Modifier.size(70.dp, 100.dp), contentScale = ContentScale.Crop)
                Column(Modifier.padding(start = 12.dp).weight(1f)) {
                    Text(book.name, fontWeight = FontWeight.Bold, fontSize = 17.sp, maxLines = 2)
                    Text(book.authors, fontSize = 13.sp, color = Color.DarkGray)
                    Text("${book.rating} ⭐ | ${book.year}", fontSize = 12.sp)
                }
                Column {
                    IconButton({ vm.alternarFavorito(book.id) }) {
                        Icon(if (book.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder, null, tint = Color.Red)
                    }
                    IconButton({ showDeleteAlert = true }) { Icon(Icons.Default.Delete, null, tint = Color.Gray) }
                }
            }
            Row(Modifier.fillMaxWidth().padding(top = 8.dp), Arrangement.SpaceBetween) {
                listOf(0, 25, 50, 75, 100).forEach { p ->
                    TextButton({ vm.atualizarProgresso(book.id, p) }) {
                        Text("$p%", fontSize = 10.sp, color = if (book.percentage == p) Color.Black else Color.Gray)
                    }
                }
            }
            LinearProgressIndicator(book.percentage / 100f, Modifier.fillMaxWidth().padding(vertical = 4.dp))
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(notes, { notes = it; vm.atualizarNota(book.id, it) }, Modifier.fillMaxWidth(), placeholder = { Text("Notas...") })
        }
    }
    if (showDeleteAlert) AlertDialog(
        onDismissRequest = { showDeleteAlert = false },
        confirmButton = { TextButton({ vm.apagar(book.id); showDeleteAlert = false }) { Text("Apagar", color = Color.Red) } },
        dismissButton = { TextButton({ showDeleteAlert = false }) { Text("Cancelar") } },
        title = { Text("Apagar?") },
        text = { Text("Tens a certeza?") }
    )
}
