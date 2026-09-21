package com.premkumar.to_do_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.premkumar.to_do_list.ui.theme.To_Do_listTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            To_Do_listTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 1. Call TodoApp instead of Greeting
                    // 2. Pass the innerPadding so the UI doesn't overlap the status bar
                    TodoApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Todo(
    val id: Int,
    val title: String,
    var isChecked: Boolean = false
)

@Composable
// Accept the modifier passed from the Scaffold
fun TodoApp(modifier: Modifier = Modifier) {
    var todoText by remember { mutableStateOf("") }

    // Add <Todo> so it knows what type of data is in the list
    val todoList = remember { mutableStateListOf<Todo>() }

    // Apply the modifier to the outermost Column
    Column(modifier = modifier.padding(16.dp).fillMaxSize()) {

        // Input Area
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = todoText,
                onValueChange = { todoText = it },
                label = { Text("Enter a new task") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (todoText.isNotBlank()) {
                    todoList.add(Todo(id = todoList.size, title = todoText))
                    todoText = "" // Clear the field after adding
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        LazyColumn {
            items(todoList) { todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = todo.isChecked,
                        onCheckedChange = { isChecked ->
                            // Update the checked state
                            val index = todoList.indexOf(todo)
                            if (index != -1) {
                                todoList[index] = todo.copy(isChecked = isChecked)
                            }
                        }
                    )
                    Text(
                        text = todo.title,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { todoList.remove(todo) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Task"
                        )
                    }
                }
            }
        }
    }
}