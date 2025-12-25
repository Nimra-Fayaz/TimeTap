package com.example.timecalculator.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TimeCalculatorScreen(
    viewModel: TimeCalculatorViewModel = viewModel(),
    onNavigateToTodos: () -> Unit
) {
    val display by viewModel.display.collectAsState()
    val resultDisplay by viewModel.resultDisplay.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Time Calculator",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                actions = {
                    IconButton(onClick = onNavigateToTodos) {
                        Icon(
                            Icons.Default.List,
                            contentDescription = "View Todos",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                )
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Display Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (resultDisplay.isNotEmpty()) {
                        Text(
                            text = resultDisplay,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            textAlign = TextAlign.End
                        )
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.surface,
                                            MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = display,
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(24.dp),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Button Grid
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Row 1: 7, 8, 9, d
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CalculatorButton("7", Modifier.weight(1f)) { viewModel.onDigitClick("7") }
                        CalculatorButton("8", Modifier.weight(1f)) { viewModel.onDigitClick("8") }
                        CalculatorButton("9", Modifier.weight(1f)) { viewModel.onDigitClick("9") }
                        CalculatorButton("d", Modifier.weight(1f), isUnit = true) { viewModel.onUnitClick("d") }
                    }

                    // Row 2: 4, 5, 6, h
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CalculatorButton("4", Modifier.weight(1f)) { viewModel.onDigitClick("4") }
                        CalculatorButton("5", Modifier.weight(1f)) { viewModel.onDigitClick("5") }
                        CalculatorButton("6", Modifier.weight(1f)) { viewModel.onDigitClick("6") }
                        CalculatorButton("h", Modifier.weight(1f), isUnit = true) { viewModel.onUnitClick("h") }
                    }

                    // Row 3: 1, 2, 3, m
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CalculatorButton("1", Modifier.weight(1f)) { viewModel.onDigitClick("1") }
                        CalculatorButton("2", Modifier.weight(1f)) { viewModel.onDigitClick("2") }
                        CalculatorButton("3", Modifier.weight(1f)) { viewModel.onDigitClick("3") }
                        CalculatorButton("m", Modifier.weight(1f), isUnit = true) { viewModel.onUnitClick("m") }
                    }

                    // Row 4: C, 0, +, =
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CalculatorButton("C", Modifier.weight(1f), isOperation = true) { viewModel.onClearClick() }
                        CalculatorButton("0", Modifier.weight(1f)) { viewModel.onDigitClick("0") }
                        CalculatorButton("+", Modifier.weight(1f), isOperation = true) { viewModel.onPlusClick() }
                        CalculatorButton("=", Modifier.weight(1f), isOperation = true) { viewModel.onEqualsClick() }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    isOperation: Boolean = false,
    isUnit: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isOperation -> Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary
            )
        )
        isUnit -> Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.tertiary,
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
            )
        )
        else -> Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }

    val contentColor = when {
        isOperation -> MaterialTheme.colorScheme.onPrimary
        isUnit -> MaterialTheme.colorScheme.onTertiary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .height(75.dp)
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}
