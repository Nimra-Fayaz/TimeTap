package com.example.timecalculator.model

/**
 * Immutable Duration type representing days, hours, and minutes.
 * All values are normalized (e.g., 25 hours becomes 1 day + 1 hour).
 */
data class Duration(
    val days: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0
) {
    init {
        require(days >= 0) { "Days must be non-negative" }
        require(hours >= 0) { "Hours must be non-negative" }
        require(minutes >= 0) { "Minutes must be non-negative" }
    }

    /**
     * Converts this Duration to total minutes for easier arithmetic.
     */
    fun toMinutes(): Int {
        return days * 24 * 60 + hours * 60 + minutes
    }

    /**
     * Operator overloading for + to add two Durations together.
     * Result is normalized.
     */
    operator fun plus(other: Duration): Duration {
        return fromMinutes(this.toMinutes() + other.toMinutes())
    }

    /**
     * Returns a normalized (simplified) version of this Duration.
     */
    fun normalize(): Duration {
        return fromMinutes(toMinutes())
    }

    /**
     * Returns a human-readable string representation.
     */
    override fun toString(): String {
        val parts = mutableListOf<String>()
        if (days > 0) parts.add("${days}d")
        if (hours > 0) parts.add("${hours}h")
        if (minutes > 0) parts.add("${minutes}m")
        return if (parts.isEmpty()) "0m" else parts.joinToString(" ")
    }

    companion object {
        val ZERO = Duration(0, 0, 0)

        /**
         * Creates a Duration from total minutes, properly normalized.
         */
        fun fromMinutes(totalMinutes: Int): Duration {
            require(totalMinutes >= 0) { "Total minutes must be non-negative" }
            val days = totalMinutes / (24 * 60)
            val hours = (totalMinutes % (24 * 60)) / 60
            val minutes = totalMinutes % 60
            return Duration(days, hours, minutes)
        }

        /**
         * Creates a Duration from a string like "2d 3h 45m" or "3h 30m"
         */
        fun fromString(str: String): Duration {
            var totalMinutes = 0
            val regex = """(\d+)([dhm])""".toRegex()
            regex.findAll(str).forEach { match ->
                val value = match.groupValues[1].toInt()
                when (match.groupValues[2]) {
                    "d" -> totalMinutes += value * 24 * 60
                    "h" -> totalMinutes += value * 60
                    "m" -> totalMinutes += value
                }
            }
            return fromMinutes(totalMinutes)
        }
    }
}

