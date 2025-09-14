package com.stedis.validation

/**
 * Represents a composition of multiple validation rules for a given type [T].
 *
 * This interface allows you to define a set of validation rules that can be
 * applied as a group to a value.
 *
 * @param T The type of value to validate.
 *
 * Example:
 * ```kotlin
 * class UsernameValidationRules : ValidationRulesComposition<String> {
 *     override fun getComposition(value: String) =
 *         NotEmptyRule +
 *         MinLengthRule(3) +
 *         AlphanumericRule
 *     )
 * }
 * ```
 */
public interface ValidationRulesComposition<T> {

    /**
     * Returns a list of validation rules to be applied to the given [value].
     *
     * @param value The value to derive validation rules for.
     * @return A list of [ValidationRule] instances applicable to [value].
     */
    public fun getComposition(value: T): List<ValidationRule<T>>
}