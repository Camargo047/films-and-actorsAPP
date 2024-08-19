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
import com.example.filmes_atores_project.data.ActorWithMovies
import com.example.filmes_atores_project.data.Movie

@Composable
fun ActorDetailScreen(
    actorWithMovies: ActorWithMovies,
    allMovies: List<Movie>, // Adicione a lista de filmes disponíveis
    onAddMovieToActor: (Movie) -> Unit,
    onRemoveMovieFromActor: (Movie) -> Unit
) {
    var showAddMovieDialog by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ator: ${actorWithMovies.actor.name}")
        Text("Filmes:", style = MaterialTheme.typography.bodyLarge)

        actorWithMovies.movies.forEach { movie ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(movie.name, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onRemoveMovieFromActor(movie) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove Movie from Actor")
                }
            }
        }

        // Botão para adicionar novo filme ao ator
        Button(onClick = { showAddMovieDialog = true }) {
            Text("Add Movie to Actor")
        }

        // Mostrar o diálogo para adicionar filme
        if (showAddMovieDialog) {
            AddMovieDialog(
                movies = allMovies,
                onAddMovie = { movie ->
                    if (movie != null) {
                        onAddMovieToActor(movie)
                    }
                    showAddMovieDialog = false
                },
                onDismiss = { showAddMovieDialog = false }
            )
        }
    }
}

@Composable
fun AddMovieDialog(
    movies: List<Movie>,
    onAddMovie: (Movie?) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Movie") },
        text = {
            Column {
                movies.forEach { movie ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedMovie = movie }
                            .padding(8.dp)
                    ) {
                        Text(movie.name, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.weight(1f))
                        if (selectedMovie == movie) {
                            Icon(Icons.Filled.Check, contentDescription = "Selected")
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddMovie(selectedMovie)
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
