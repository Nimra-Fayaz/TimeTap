package com.example.timecalculator.model

/**
 * Timed<T> wraps a value with a Duration, representing the time associated with that value.
 * This is a monad-like structure with map and flatMap operations.
 */
data class Timed<T>(
    val duration: Duration,
    val value: T
) {

    /**
     * Maps the value using function f, keeping the same duration.
     */
    fun <U> map(f: (T) -> U): Timed<U> =
        Timed(duration, f(value))

    /**
     * FlatMap chains Timed operations, accumulating durations.
     */
    fun <U> flatMap(f: (T) -> Timed<U>): Timed<U> {
        val next = f(value)
        return Timed(
            duration = duration + next.duration,
            value = next.value
        )
    }

    companion object {

        /**
         * Creates a Timed with zero duration.
         */
        fun <T> pure(value: T): Timed<T> =
            Timed(Duration.ZERO, value)

        /**
         * Combines a list of Timed items into a single Timed containing a list,
         * accumulating all durations.
         */
        fun <T> combine(items: List<Timed<T>>): Timed<List<T>> =
            items.fold(pure(emptyList())) { acc, timed ->
                acc.flatMap { list ->
                    timed.map { value -> list + value }
                }
            }
    }

}

