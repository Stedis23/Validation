package com.stedis.validation

/**
 * Represents a validated value of type [T], which may be valid or invalid.
 *
 * Supports chaining of validations via [bind], aggregating all validation errors.
 *
 * @param T The type of the validated value.
 *
 * Example:
 * ```kotlin
 * val validation = Validation.unit("hello")
 *     .bind(NotEmptyRule())
 *     .bind(MinLengthRule(3))
 *
 * if (validation is Invalid) {
 *     println("Errors: ${validation.errors}")
 * }
 * ```
 */
public sealed class Validation<T>(private val value: T) {

    public companion object {
        /**
         * Creates a valid [Validation] instance for the given [value].
         *
         * @param value The initial value considered valid.
         * @return A [Valid] instance holding [value].
         */
        public fun <T> unit(value: T): Validation<T> = Valid(value)
    }

    /**
     * Applies the given [validationRule] to the current value and aggregates errors.
     *
     * If the current validation state is invalid, retains existing errors and adds
     * new ones if the rule fails.
     *
     * @param validationRule The validation rule to apply.
     * @return A new [Validation] instance containing all accumulated validation errors.
     */
    public fun bind(validationRule: ValidationRule<T>): Validation<T> {
        val errors = when (this) {
            is Invalid -> this.errors
            else -> emptyList()
        }

        val newErrors = when (val result = validationRule.check(value)) {
            is ValidationResult.Invalid -> listOf(result.reason)
            ValidationResult.Valid -> emptyList()
        }

        val allErrors = errors + newErrors

        return if (allErrors.isEmpty()) Valid(value) else Invalid(value, allErrors)
    }
}

/**
 * Represents a valid [Validation] holding a successfully validated [value].
 *
 * @param T The type of the validated value.
 *
 * @property value The successfully validated value.
 */
public class Valid<T>(val value: T) : Validation<T>(value)

/**
 * Represents an invalid [Validation] holding the original [value] and a list of errors.
 *
 * @param T The type of the validated value.
 *
 * @property value The original value that failed validation.
 * @property errors A list of reasons why the validation failed.
 */
public class Invalid<T>(val value: T, val errors: List<InvalidValueReason>) : Validation<T>(value)

/**
 * Checks whether this [Validation] instance represents a valid value.
 *
 * Returns `true` if the validation result is [Valid], otherwise `false`.
 *
 * @receiver The [Validation] instance to check.
 * @return `true` if valid, `false` otherwise.
 */
public val <T> Validation<T>.isValid: Boolean get() = this is Valid

/**
 * Checks whether this [Validation] instance represents an invalid value.
 *
 * Returns `true` if the validation result is [Invalid], otherwise `false`.
 *
 * @receiver The [Validation] instance to check.
 * @return `true` if invalid, `false` otherwise.
 */
public val <T> Validation<T>.isInvalid: Boolean get() = this is Invalid