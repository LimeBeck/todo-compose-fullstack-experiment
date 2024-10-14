package pages

import androidx.compose.runtime.*
import app.softwork.routingcompose.NavLink
import dev.limebeck.todo.domain.Task
import dev.limebeck.todo.domain.TodoService
import domain.initRpcClient
import kotlinx.coroutines.launch
import kotlinx.rpc.krpc.streamScoped
import kotlinx.rpc.withService
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

sealed interface ServiceState<T> {
    class Initializing<T> : ServiceState<T>
    value class Success<T>(val value: T) : ServiceState<T>
    class Error<T>(error: Throwable? = null) : ServiceState<T>
}

@Composable
fun HomePage() {
    val coroutineScope = rememberCoroutineScope()

    var todos by remember { mutableStateOf(emptyList<Task>()) }

    val (todoService, setTodoService) = remember { mutableStateOf<ServiceState<TodoService>>(ServiceState.Initializing()) }

    LaunchedEffect(coroutineScope) {
        val client = initRpcClient()
        setTodoService(ServiceState.Success(client.withService()))
    }

    remember(todoService) {
        when (val service = todoService) {
            is ServiceState.Initializing -> {}
            is ServiceState.Success -> {
                coroutineScope.launch {
                    todos = service.value.getTasksList()
                }

                coroutineScope.launch {
                    streamScoped {
                        service.value.subscribeToNewTasks().collect {
                            console.log(it)
                            todos = todos + it
                        }
                    }
                }
            }

            is ServiceState.Error<TodoService> -> {}
        }
    }

    Div {
        Text("Hello World")
        todos.forEach {
            Div {
                NavLink(it.id.toString()) {
                    Text(it.description)
                }
            }
        }
    }
}
