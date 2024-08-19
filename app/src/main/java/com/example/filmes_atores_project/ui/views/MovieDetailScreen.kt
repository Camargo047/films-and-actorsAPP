package com.example.filmes_atores_project.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.filmes_atores_project.data.Actor
import com.example.filmes_atores_project.data.MovieWithActors

@Composable
fun MovieDetailScreen(
    movieWithActors: MovieWithActors,
    allActors: List<Actor>, // Adicione a lista de atores disponíveis
    onAddActorToMovie: (Actor) -> Unit,
    onRemoveActorFromMovie: (Actor) -> Unit
) {
    var showAddActorDialog by remember { mutableStateOf(false) }
    var selectedActor by remember { mutableStateOf<Actor?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Filme: ${movieWithActors.movie.name}")
        Text("Atores:", style = MaterialTheme.typography.bodyLarge)

        movieWithActors.actors.forEach { actor ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(actor.name, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onRemoveActorFromMovie(actor) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove Actor from Movie")
                }
            }
        }

        // Botão para adicionar novo ator ao filme
        Button(onClick = { showAddActorDialog = true }) {
            Text("Add Actor to Movie")
        }

        // Mostrar o diálogo para adicionar ator
        if (showAddActorDialog) {
            AddActorDialog(
                actors = allActors,
                onAddActor = { actor ->
                    if (actor != null) {
                        onAddActorToMovie(actor)
                    }
                    showAddActorDialog = false
                },
                onDismiss = { showAddActorDialog = false }
            )
        }
    }
}

@Composable
fun AddActorDialog(
    actors: List<Actor>,
    onAddActor: (Actor?) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedActor by remember { mutableStateOf<Actor?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Actor") },
        text = {
            Column {
                actors.forEach { actor ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedActor = actor }
                            .padding(8.dp)
                    ) {
                        Text(actor.name, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.weight(1f))
                        if (selectedActor == actor) {
                            Icon(Icons.Filled.Check, contentDescription = "Selected")
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddActor(selectedActor)
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
