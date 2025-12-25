package com.example.timecalculator.ui.todos

import androidx.lifecycle.ViewModel
import com.example.timecalculator.model.Duration
import com.example.timecalculator.model.Timed
import com.example.timecalculator.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * ViewModel for managing todos with durations (in-memory storage only).
 */
class TodosViewModel : ViewModel() {

    private val _todos = MutableStateFlow<List<TodoItem>>(
        listOf(
            TodoItem(
                id = UUID.randomUUID(),
                timedDescription = Timed(Duration(hours = 5, minutes = 30), "Study for exam"),
                isCompleted = false
            ),
            TodoItem(
                id = UUID.randomUUID(),
                timedDescription = Timed(Duration(hours = 1, minutes = 15), "Grocery shopping"),
                isCompleted = false
            ),
            TodoItem(
                id = UUID.randomUUID(),
                timedDescription = Timed(Duration(days = 3, hours = 2), "Complete Android Lab 3"),
                isCompleted = false
            )
        )
    )
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    private val _selectedTodoIds = MutableStateFlow<Set<UUID>>(emptySet())
    val selectedTodoIds: StateFlow<Set<UUID>> = _selectedTodoIds.asStateFlow()

    private val _completionMessage = MutableStateFlow<String?>(null)
    val completionMessage: StateFlow<String?> = _completionMessage.asStateFlow()

    fun createTodo(description: String, duration: Duration) {
        val newTodo = TodoItem(
            id = UUID.randomUUID(),
            timedDescription = Timed(duration, description),
            isCompleted = false
        )
        _todos.value = _todos.value + newTodo
    }

    fun updateTodo(id: UUID, description: String, duration: Duration) {
        _todos.value = _todos.value.map { todo ->
            if (todo.id == id) {
                todo.copy(timedDescription = Timed(duration, description))
            } else {
                todo
            }
        }
    }

    fun deleteTodo(id: UUID) {
        _todos.value = _todos.value.filter { it.id != id }
    }

    fun toggleTodoSelection(id: UUID) {
        _selectedTodoIds.value = if (_selectedTodoIds.value.contains(id)) {
            _selectedTodoIds.value - id
        } else {
            _selectedTodoIds.value + id
        }
    }

    fun finishSelectedTodos() {
        val selectedTodos = _todos.value.filter { _selectedTodoIds.value.contains(it.id) }
        if (selectedTodos.isEmpty()) return

        // Use Timed.combine to accumulate durations and descriptions
        val timedDescriptions = selectedTodos.map { it.timedDescription }
        val combined = Timed.combine(timedDescriptions)

        val message = buildString {
            append("Completed tasks (Total: ${combined.duration}):\n\n")
            combined.value.forEachIndexed { index, desc ->
                append("${index + 1}. $desc\n")
            }
        }

        _completionMessage.value = message

        // Remove completed todos
        _todos.value = _todos.value.filter { !_selectedTodoIds.value.contains(it.id) }
        _selectedTodoIds.value = emptySet()
    }

    fun clearCompletionMessage() {
        _completionMessage.value = null
    }

    fun getTodoById(id: UUID): TodoItem? {
        return _todos.value.find { it.id == id }
    }
}
