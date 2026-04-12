package edu.learn.weatherapprbk.core.architecture

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Intent, State, Effect>(
    initialState: State
) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    protected val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    abstract fun onIntent(intent: Intent)

    protected fun setState(reducer: State.() -> State) {
        _state.value = _state.value.reducer()
    }

    protected suspend fun sendEffect(builder: () -> Effect) {
        _effect.send(builder())
    }
}