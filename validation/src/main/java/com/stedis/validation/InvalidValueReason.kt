package com.stedis.validation

/**
 * Marker interface representing the reason why a value failed validation.
 *
 * Implement this interface to provide domain-specific failure reasons.
 *
 * Example:
 * ```kotlin
 * public object EmptyStringReason : InvalidValueReason
 *
 * public enum class BooleanInvalidValueReason : InvalidValueReason {
 *     NOT_TRUE,
 *     NOT_FALSE,
 * }
 * ```
 */
public interface InvalidValueReason