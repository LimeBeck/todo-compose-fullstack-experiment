package dev.limebeck.todo.domain

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.coroutines.CoroutineContext

class TodoServiceImpl(override val coroutineContext: CoroutineContext) : TodoService {
    companion object {
        private val logger = LoggerFactory.getLogger(TodoServiceImpl::class.java)
    }

    private val taskList: MutableMap<TaskId, Task> = mutableMapOf()

    init {
        logger.info("<8dcfba3d> TodoServiceImpl initialized")
    }

    override suspend fun addTask(task: NewTask): Task {
        val id = TaskId(UUID.randomUUID().toString())
        val newTask = Task(id, task.description, false)
        taskList[id] = newTask
        return newTask
    }

    override suspend fun markCompleted(id: TaskId): Task {
        return taskList[id]!!.copy(completed = true).also {
            taskList[id] = it
        }
    }

    override suspend fun markNotCompleted(id: TaskId): Task {
        return taskList[id]!!.copy(completed = false).also {
            taskList[id] = it
        }
    }

    override suspend fun getTasksList(filter: TasksFilter): List<Task> = taskList.values.toList()

    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun subscribeToNewTasks(): Flow<Task> {
        return ticker(1000L).consumeAsFlow().take(10).map {
            Task(
                id = TaskId(UUID.randomUUID().toString()),
                description = "Random task",
                completed = false
            ).also {
                logger.info("<20dad9aa> $it")
                taskList[it.id] = it
            }
        }
    }
}