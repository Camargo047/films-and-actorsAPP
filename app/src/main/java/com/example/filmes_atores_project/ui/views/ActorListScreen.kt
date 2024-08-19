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
import com.example.filmes_atores_project.data.Actor
import com.example.filmes_atores_project.data.ActorWithMovies

@Composable
fun ActorListScreen(
    actors: ActorWithMovies,
    onActorClick: (Actor) -> Unit,
    onAddActorClick: () -> Unit,
    onDeleteActorClick: (Actor) -> Unit
) {
    Column {
        actors.forEach { actorWithMovies ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onActorClick(actorWithMovies.actor) }
                    .padding(16.dp)
            ) {
                Column {
                    Text(actorWithMovies.actor.name, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Filmes: " + actorWithMovies.movies.joinToString { it.name },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteActorClick(actorWithMovies.actor) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Actor")
                }
            }
        }

        Button(
            onClick = onAddActorClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add Actor")
        }
    }
}

