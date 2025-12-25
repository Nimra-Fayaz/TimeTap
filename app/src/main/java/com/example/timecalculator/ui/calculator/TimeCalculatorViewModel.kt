package com.example.timecalculator.ui.calculator

import androidx.lifecycle.ViewModel
import com.example.timecalculator.model.Duration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for the Time Calculator screen.
 * Manages the calculator state and operations.
 */
class TimeCalculatorViewModel : ViewModel() {

    private val _display = MutableStateFlow("0")
    val display: StateFlow<String> = _display.asStateFlow()

    private val _resultDisplay = MutableStateFlow("")
    val resultDisplay: StateFlow<String> = _resultDisplay.asStateFlow()

    private var currentInput = ""
    private var currentDuration: Duration? = null
    private var pendingOperation: (() -> Unit)? = null

    fun onDigitClick(digit: String) {
        currentInput += digit
        _display.value = currentInput.ifEmpty { "0" }
    }

    fun onUnitClick(unit: String) {
        if (currentInput.isEmpty()) return

        currentInput += unit
        _display.value = currentInput
    }

    fun onPlusClick() {
        if (currentInput.isEmpty()) return

        // Parse current input as a duration
        val inputDuration = try {
            parseDuration(currentInput)
        } catch (e: Exception) {
            _display.value = "Error"
            currentInput = ""
            return
        }

        currentDuration = if (currentDuration == null) {
            inputDuration
        } else {
            currentDuration!! + inputDuration
        }

        _resultDisplay.value = currentDuration.toString()
        currentInput = ""
        _display.value = "0"
    }

    fun onEqualsClick() {
        if (currentInput.isEmpty() && currentDuration != null) {
            _display.value = currentDuration.toString()
            _resultDisplay.value = ""
            return
        }

        if (currentInput.isEmpty()) return

        val inputDuration = try {
            parseDuration(currentInput)
        } catch (e: Exception) {
            _display.value = "Error"
            currentInput = ""
            return
        }

        val result = if (currentDuration == null) {
            inputDuration
        } else {
            currentDuration!! + inputDuration
        }

        _display.value = result.toString()
        _resultDisplay.value = ""
        currentDuration = null
        currentInput = ""
    }

    fun onClearClick() {
        currentInput = ""
        currentDuration = null
        _display.value = "0"
        _resultDisplay.value = ""
    }

    /**
     * Parses a string like "2d3h45m" or "3h30m" into a Duration.
     */
    private fun parseDuration(input: String): Duration {
        var totalMinutes = 0
        val regex = """(\d+)([dhm])""".toRegex()
        
        val matches = regex.findAll(input).toList()
        if (matches.isEmpty()) {
            throw IllegalArgumentException("Invalid duration format")
        }

        matches.forEach { match ->
            val value = match.groupValues[1].toInt()
            when (match.groupValues[2]) {
                "d" -> totalMinutes += value * 24 * 60
                "h" -> totalMinutes += value * 60
                "m" -> totalMinutes += value
            }
        }
        return Duration.fromMinutes(totalMinutes)
    }
}

