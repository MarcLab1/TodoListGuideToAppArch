package com.todolistguidetoapparch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todolistguidetoapparch.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Home(vm: HomeViewModel = hiltViewModel()) {

    Column() {

        var name by remember { mutableStateOf("") }
        var completed by remember { mutableStateOf(false) }
        Row(modifier = Modifier.fillMaxWidth())
        {
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(horizontal = 10.dp)
            )
            Checkbox(checked = completed, onCheckedChange = { completed = it })
        }

        Button(onClick = { vm.insertTodo(name, completed) }) {
            Text("insert todo")
        }

        LazyColumn()
        {
            itemsIndexed(vm.todoUIs.value)
            { index, todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                )
                {

                    Text(
                        "${todo.id} ${todo.name} ${todo.completed}",
                        modifier = Modifier.clickable {
                            todo.onChangeCompleted()
                        })
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            todo.onDelete()
                        })
                }
            }
        }
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    var todoUIs: MutableState<List<TodoUI>> = mutableStateOf(emptyList())

    init {
        getTodos()
    }

    fun getTodos() {
        viewModelScope.launch {
            todoRepository.getTodos().collectLatest {
                todoUIs.value = it.map {
                    TodoUI(
                        id = it.id, name = it.name, completed = it.completed,
                        onChangeCompleted = {
                            viewModelScope.launch {
                                todoRepository.updateTodo(it.copy(completed = !it.completed))
                            }
                        },
                        onDelete = {
                            viewModelScope.launch {
                                todoRepository.deleteTodo(it)
                            }
                        }
                    )
                }
            }
        }
    }

    fun insertTodo(name: String, completed: Boolean): Unit {
        viewModelScope.launch {
            todoRepository.insertTodo(Todo(todoUIs.value.size + 1, name, completed))
        }
    }
}