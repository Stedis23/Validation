package com.stedis.validation

/**
 * Defines a single validation rule for a value of type [T].
 *
 * Implementations should encapsulate a specific validation logic.
 *
 * @param T The type of value to validate.
 *
 * Example:
 * ```kotlin
 * class NotEmptyRule : ValidationRule<String> {
 *     override fun check(value: String): ValidationResult =
 *         if (value.isNotEmpty()) ValidationResult.Valid
 *         else ValidationResult.Invalid(StringInvalidValueReason.EMPTY)
 * }
 * ```
 */
public interface ValidationRule<T> {

    /**
     * Checks the [value] against this validation rule.
     *
     * @param value The value to validate.
     * @return [ValidationResult.Valid] if the value passes the validation,
     *         otherwise [ValidationResult.Invalid] with the failure reason.
     */
    public fun check(value: T): ValidationResult
}

/**
 * Combines two [ValidationRule] instances into a list of validation rules.
 *
 * This operator function allows using the `+` syntax to easily compose multiple validation rules
 * into a list, which can then be applied together.
 *
 * @param T The type of the value to validate.
 * @param otherValidationRule The other validation rule to combine with this one.
 *
 * @return A list containing both validation rules.
 *
 * Example:
 * ```kotlin
 * val combinedRules = NotEmptyRule() + MinLengthRule(5)
 * // combinedRules is List<ValidationRule<String>> containing both rules
 * ```
 */
public infix operator fun <T> ValidationRule<T>.plus(otherValidationRule: ValidationRule<T>): List<ValidationRule<T>> =
    listOf(this, otherValidationRule)