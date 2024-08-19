package com.example.filmes_atores_project.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.filmes_atores_project.data.Movie
import com.example.filmes_atores_project.data.MovieWithActors

@Composable
fun MovieListScreen(
    movies: MovieWithActors,
    onMovieClick: (Movie) -> Unit,
    onAddMovieClick: () -> Unit,
    onDeleteMovieClick: (Movie) -> Unit
) {
    Column {
        movies.forEach { movieWithActors ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMovieClick(movieWithActors.movie) }
                    .padding(16.dp)
            ) {
                Column {
                    Text(movieWithActors.movie.name, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Atores: " + movieWithActors.actors.joinToString { it.name },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteMovieClick(movieWithActors.movie) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Movie")
                }
            }
        }

        Button(
            onClick = onAddMovieClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add Movie")
        }
    }
}

