package com.stedis.validation

/**
 * Validates the receiver value [T] against a set of validation rules defined by a [ValidationRulesComposition].
 *
 * Applies all rules obtained from the composition for the current value, then invokes either
 * [onValid] callback if validation passes, or [onInvalid] callback with validation errors if it fails.
 *
 * @param R The return type of the result callbacks.
 * @param composition The [ValidationRulesComposition] providing the validation rules.
 * @param onValid Lambda invoked with the value if all rules pass validation.
 * @param onInvalid Lambda invoked with the value and list of error reasons if validation fails.
 * @return The result of invoking either [onValid] or [onInvalid].
 *
 * Example:
 * ```kotlin
 * val result = "abc".validate(
 *     composition = MyStringRules(),
 *     onValid = { "Valid: $it" },
 *     onInvalid = { value, errors -> "Invalid: $value; reasons: $errors" }
 * )
 * ```
 */
public inline fun <R, T> T.validate(
    composition: ValidationRulesComposition<T>,
    onValid: (value: T) -> R,
    onInvalid: (value: T, errors: List<InvalidValueReason>) -> R,
): R =
    this
        .validate(composition.getComposition(this))
        .fold(onValid, onInvalid)

/**
 * Validates the receiver value [T] against a set of validation rules provided by a [ValidationRulesComposition].
 *
 * All rules returned from the composition are applied cumulatively. If no rules are defined, validation
 * automatically succeeds.
 *
 * @param composition The [ValidationRulesComposition] providing the validation rules.
 * @return A [Validation] object representing the validation outcome.
 */
public fun <T> T.validate(composition: ValidationRulesComposition<T>): Validation<T> {
    val rules = composition.getComposition(this)

    if (rules.isEmpty()) return Valid(this)

    return rules.fold(Validation.unit(this)) { currentResult, rule ->
        currentResult.bind(rule)
    }
}

/**
 * Validates the receiver value [T] against a list of [ValidationRule]s.
 *
 * Applies each rule in order and aggregates all failures. On success, calls [onValid],
 * otherwise calls [onInvalid] with the list of failure reasons.
 *
 * @param R The return type of the callbacks.
 * @param rules List of [ValidationRule] instances to apply.
 * @param onValid Lambda invoked if validation passes.
 * @param onInvalid Lambda invoked if validation fails, providing errors.
 * @return The result of [onValid] or [onInvalid].
 */
public inline fun <R, T> T.validate(
    rules: List<ValidationRule<T>>,
    onValid: (value: T) -> R,
    onInvalid: (value: T, errors: List<InvalidValueReason>) -> R,
): R =
    this
        .validate(rules)
        .fold(onValid, onInvalid)

/**
 * Validates the receiver value [T] against a list of [ValidationRule]s.
 *
 * If the rule list is empty, validation is considered successful.
 *
 * @param rules List of validation rules to apply.
 * @return A [Validation] instance reflecting the validation results.
 */
public fun <T> T.validate(rules: List<ValidationRule<T>>): Validation<T> {
    if (rules.isEmpty()) return Valid(this)

    return rules.fold(Validation.unit(this)) { currentResult, rule ->
        currentResult.bind(rule)
    }
}

/**
 * Validates the receiver value [T] against a single [ValidationRule].
 *
 * Invokes [onValid] if the rule passes, or [onInvalid] if it fails.
 *
 * @param R The result type of the callbacks.
 * @param rule The validation rule to apply.
 * @param onValid Lambda called if validation passes.
 * @param onInvalid Lambda called if validation fails, receives errors.
 * @return The result of [onValid] or [onInvalid].
 */
public inline fun <R, T> T.validate(
    rule: ValidationRule<T>,
    onValid: (value: T) -> R,
    onInvalid: (value: T, errors: List<InvalidValueReason>) -> R,
): R =
    this
        .validate(rule)
        .fold(onValid, onInvalid)

/**
 * Validates the receiver value [T] against a single [ValidationRule].
 *
 * @param rule The validation rule to apply.
 * @return A [Validation] instance indicating success or failure.
 */
public fun <T> T.validate(rule: ValidationRule<T>): Validation<T> =
    Validation.unit(this).bind(rule)

/**
 * Processes this [Validation] instance by executing the appropriate callback depending
 * on whether the validation succeeded or failed.
 *
 * @param R The result type of the callbacks.
 * @param onValid Lambda executed if validation is [Valid] with the validated value.
 * @param onInvalid Lambda executed if validation is [Invalid], with the value and error reasons.
 * @return The result of either [onValid] or [onInvalid].
 *
 * Example:
 * ```kotlin
 * validation.fold(
 *     onValid = { value -> println("Valid: $value") },
 *     onInvalid = { value, errors -> println("Invalid $value: $errors") }
 * )
 * ```
 */
public inline fun <R, T> Validation<T>.fold(
    onValid: (value: T) -> R,
    onInvalid: (value: T, errors: List<InvalidValueReason>) -> R,
): R =
    when (this) {
        is Valid -> onValid(value)
        is Invalid -> onInvalid(value, errors)
    }

/**
 * Performs the given [body] lambda if this [Validation] instance is [Valid].
 *
 * Returns this [Validation] instance to allow chaining.
 *
 * @param T The type of the validated value.
 * @param body Lambda to invoke with the valid value.
 * @return This [Validation] instance unchanged.
 */
public inline fun <T> Validation<T>.onValid(body: (value: T) -> Unit): Validation<T> =
    apply {
        when (this) {
            is Valid -> body(value)
            is Invalid -> Unit
        }
    }

/**
 * Performs the given [body] lambda if this [Validation] instance is [Invalid].
 *
 * Returns this [Validation] instance to allow chaining.
 *
 * @param T The type of the validated value.
 * @param body Lambda to invoke with the invalid value and list of error reasons.
 * @return This [Validation] instance unchanged.
 */
public inline fun <T> Validation<T>.onInvalid(body: (value: T, errors: List<InvalidValueReason>) -> Unit): Validation<T> =
    apply {
        when (this) {
            is Valid -> Unit
            is Invalid -> body(value, errors)
        }
    }