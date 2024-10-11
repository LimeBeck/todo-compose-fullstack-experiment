package dev.limebeck.todo

import kotlin.jvm.JvmInline
import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.RPC
import kotlinx.serialization.Serializable


interface TodoService : RPC {
    suspend fun addTask(task: NewTask): Task
    suspend fun markCompleted(id: TaskId): Task
    suspend fun markNotCompleted(id: TaskId): Task
    suspend fun getTasksList(filter: TasksFilter = TasksFilter()): List<Task>

    suspend fun subscribeToNewTasks(): Flow<Task>
}

@Serializable 
@JvmInline 
value class TaskId(val value: String)

@Serializable
data class Task(
    val id: TaskId,
    val description: String,
    val completed: Boolean,
)

@Serializable 
data class NewTask(val description: String)

@Serializable 
data class TasksFilter(val completed: Boolean? = null)