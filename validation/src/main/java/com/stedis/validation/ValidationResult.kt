package com.stedis.validation

/**
 * Represents the result of a validation check.
 *
 * Can be either [Valid] or [Invalid].
 */
public sealed interface ValidationResult {

    /**
     * Indicates the value passed validation successfully.
     */
    public data object Valid : ValidationResult

    /**
     * Indicates the value failed validation.
     *
     * @property reason The specific reason why the value was invalid.
     */
    public data class Invalid(val reason: InvalidValueReason) : ValidationResult
}