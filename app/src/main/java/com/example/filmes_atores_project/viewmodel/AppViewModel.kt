import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.filmes_atores_project.MovieApplication
import com.example.filmes_atores_project.data.Actor
import com.example.filmes_atores_project.data.ActorWithMovies
import com.example.filmes_atores_project.data.AppDao
import com.example.filmes_atores_project.data.Movie
import com.example.filmes_atores_project.data.MovieActor
import com.example.filmes_atores_project.data.MovieWithActors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val appDao: AppDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var movieId = MutableStateFlow(0)
    private var actorId = MutableStateFlow(0)

    val movies: StateFlow<List<Movie>> =
        appDao.getAllMovies()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf(),
            )

    val actors: StateFlow<List<Actor>> =
        appDao.getAllActors()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    val movieWithActors: StateFlow<MovieWithActors> =
        movieId.flatMapLatest { id ->
            appDao.getMovieWithActors(id)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = MovieWithActors(Movie(0, ""), listOf())
            )

    val actorWithMovies: StateFlow<ActorWithMovies> =
        actorId.flatMapLatest { id ->
            appDao.getActorWithMovies(id)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ActorWithMovies(Actor(0, ""), listOf())
            )

    val uiState: MutableState<ScreenState> = mutableStateOf(ScreenState.MovieList)

    fun insertActor(actor: Actor) {
        viewModelScope.launch {
            appDao.insertActor(actor)
        }
    }

    fun updateActor(actor: Actor) {
        viewModelScope.launch {
            appDao.updateActor(actor)
        }
    }

    fun deleteActor(actor: Actor) {
        viewModelScope.launch {
            appDao.deleteActor(actor)
        }
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            appDao.insertMovie(movie)
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            appDao.updateMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            appDao.deleteMovie(movie)
        }
    }

    fun insertMovieActor(movieActor: MovieActor) {
        viewModelScope.launch {
            appDao.insertMovieActor(movieActor)
        }
    }

    fun deleteMovieActor(movieActor: MovieActor) {
        viewModelScope.launch {
            appDao.deleteMovieActor(movieActor)
        }
    }

    fun selectMovie(movie: Movie) {
        movieId.value = movie.id
        uiState.value = ScreenState.MovieDetail(movie.id)
    }

    fun selectActor(actor: Actor) {
        actorId.value = actor.id
        uiState.value = ScreenState.ActorDetail(actor.id)
    }

    fun navigateToMovieList() {
        uiState.value = ScreenState.MovieList
    }

    fun navigateToActorList() {
        uiState.value = ScreenState.ActorList
    }

    fun goBack() {
        uiState.value = when (uiState.value) {
            is ScreenState.MovieDetail -> ScreenState.MovieList
            is ScreenState.ActorDetail -> ScreenState.ActorList
            else -> ScreenState.MovieList
        }
    }

    sealed class ScreenState {
        object MovieList : ScreenState()
        object ActorList : ScreenState()
        data class MovieDetail(val movieId: Int) : ScreenState()
        data class ActorDetail(val actorId: Int) : ScreenState()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                return AppViewModel(
                    (application as MovieApplication).database.appDao(),
                    savedStateHandle,
                ) as T
            }
        }
    }
}
