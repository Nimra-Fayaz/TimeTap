package com.example.timecalculator.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.timecalculator.ui.calculator.TimeCalculatorScreen
import com.example.timecalculator.ui.todos.TodoDetailScreen
import com.example.timecalculator.ui.todos.TodosListScreen
import com.example.timecalculator.ui.todos.TodosViewModel

/**
 * Navigation routes for the app.
 */
sealed class Screen(val route: String) {
    object Calculator : Screen("calculator")
    object TodosList : Screen("todos_list")
    object TodoDetail : Screen("todo_detail/{todoId}") {
        fun createRoute(todoId: String) = "todo_detail/$todoId"
    }
}

/**
 * Main navigation graph for the app.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Calculator.route
) {
    // Shared ViewModel for todos across screens
    val todosViewModel: TodosViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Calculator.route) {
            TimeCalculatorScreen(
                onNavigateToTodos = {
                    navController.navigate(Screen.TodosList.route)
                }
            )
        }

        composable(Screen.TodosList.route) {
            TodosListScreen(
                viewModel = todosViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { todoId ->
                    navController.navigate(Screen.TodoDetail.createRoute(todoId))
                }
            )
        }

        composable(
            route = Screen.TodoDetail.route,
            arguments = listOf(
                navArgument("todoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId") ?: "new"
            TodoDetailScreen(
                todoIdArg = todoId,
                viewModel = todosViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

