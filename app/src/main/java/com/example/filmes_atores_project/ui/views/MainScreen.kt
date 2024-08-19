package com.example.filmes_atores_project.ui.views

import AppViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.filmes_atores_project.data.Actor
import com.example.filmes_atores_project.data.Movie
import com.example.filmes_atores_project.data.MovieActor

@Composable
fun MainScreen(viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is AppViewModel.ScreenState.MovieList -> {
            MovieListScreen(
                movies = viewModel.movieWithActors.collectAsState().value,
                onMovieClick = { movie -> viewModel.selectMovie(movie) },
                onAddMovieClick = { viewModel.insertMovie(Movie(name = "Novo Filme")) }
            ) { movie -> viewModel.deleteMovie(movie) }
        }
        is AppViewModel.ScreenState.ActorList -> {
            ActorListScreen(
                actors = viewModel.actorWithMovies.collectAsState().value,
                onActorClick = { actor -> viewModel.selectActor(actor) },
                onAddActorClick = { viewModel.insertActor(Actor(name = "Novo Ator")) }
            ) { actor -> viewModel.deleteActor(actor) }
        }
        is AppViewModel.ScreenState.MovieDetail -> {
            val movieWithActors = viewModel.movieWithActors.collectAsState().value
            MovieDetailScreen(
                movieWithActors = movieWithActors,
                onAddActorToMovie = { actor -> viewModel.insertMovieActor(MovieActor(movieWithActors.movie.id, actor.id)) },
                onRemoveActorFromMovie = { actor -> viewModel.deleteMovieActor(MovieActor(movieWithActors.movie.id, actor.id)) }
            )
        }
        is AppViewModel.ScreenState.ActorDetail -> {
            val actorWithMovies = viewModel.actorWithMovies.collectAsState().value
            ActorDetailScreen(
                actorWithMovies = actorWithMovies,
                onAddMovieToActor = { movie -> viewModel.insertMovieActor(MovieActor(movie.id, actorWithMovies.actor.id)) },
                onRemoveMovieFromActor = { movie -> viewModel.deleteMovieActor(MovieActor(movie.id, actorWithMovies.actor.id)) }
            )
        }
    }
}
