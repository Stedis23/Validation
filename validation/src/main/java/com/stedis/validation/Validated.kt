package com.stedis.validation

/**
 * Type aliases for commonly validated primitive types and [String].
 *
 * These provide convenient shorthand for [Validated] instances of standard types.
 */
typealias ValidatedString = Validated<String>
typealias ValidatedInt = Validated<Int>
typealias ValidatedDouble = Validated<Double>
typealias ValidatedBoolean = Validated<Boolean>
typealias ValidatedLong = Validated<Long>
typealias ValidatedFloat = Validated<Float>
typealias ValidatedChar = Validated<Char>
typealias ValidatedByte = Validated<Byte>
typealias ValidatedShort = Validated<Short>

/**
 * A wrapper class that holds a value of type [T] along with its validation rules,
 * automatically validating and tracking validation errors on value updates.
 *
 * This class provides:
 * - Automatic application of one or multiple validation rules.
 * - Mutable property [value] that triggers validation on every change.
 * - Access to validation errors via [errors].
 * - Convenience properties [isValid] and [isInvalid] indicating validation status.
 *
 * @param T The type of the value being validated.
 * @property initialValue The initial value to validate.
 * @property rules The list of validation rules applied to the value.
 *
 * Constructor Creates a validated value from an initial value and list of validation rules.
 *
 * Example:
 * ```kotlin
 * val validatedName = Validated("John", NotEmptyRule + MinLengthRule(5))
 *
 * println(validatedName.isValid)  // false, since "John" length < 5
 *
 * validatedName.value = "Jonathan"
 * println(validatedName.isValid)  // true
 * ```
 *
 * Constructor Creates a validated value using a [ValidationRulesComposition], which dynamically
 * provides validation rules based on the initial value.
 *
 * Example:
 * ```kotlin
 * class PasswordRules : ValidationRulesComposition<String> {
 *     override fun getComposition(value: String) =
 *         NotEmptyRule +
 *         MinLengthRule(8) +
 *         ContainsNumberRule
 * }
 *
 * val validatedPassword = Validated("pass123", PasswordRules())
 * println(validatedPassword.isValid)  // false
 * ```
 *
 * Constructor Creates a validated value with a single validation rule.
 *
 * Example:
 * ```kotlin
 * val validatedAge = Validated(25, MinValueRule(18))
 * println(validatedAge.isValid)  // true
 * ```
 */
class Validated<T>(private val initialValue: T, private val rules: List<ValidationRule<T>>) {

    /**
     * Secondary constructor that accepts a [ValidationRulesComposition] to generate rules
     * dynamically based on the initial value.
     */
    public constructor(
        initialValue: T,
        rules: ValidationRulesComposition<T>,
    ) : this(
        initialValue,
        rules.getComposition(initialValue)
    )

    /**
     * Secondary constructor taking a single [ValidationRule].
     */
    public constructor(initialValue: T, rule: ValidationRule<T>) : this(initialValue, listOf(rule))

    /**
     * List of validation error reasons accumulated after validating the current [value].
     *
     * Empty if the [value] passes all validation rules.
     */
    private var _errors: List<InvalidValueReason> = emptyList()
    public val errors: List<InvalidValueReason> get() = _errors

    private var _value: T = initialValue

    /**
     * The current value that is being validated.
     *
     * Setting this property triggers automatic re-validation against all rules.
     */
    public var value: T
        get() = _value
        set(newValue) {
            _value = newValue
            validateValue()
        }

    init {
        validateValue()
    }

    /**
     * Performs validation on the current [value] using the configured [rules].
     *
     * Updates the internal [_errors] list based on validation results.
     */
    private fun validateValue() {
        _value.validate(rules)
            .onValid { _errors = emptyList() }
            .onInvalid { _, errors -> _errors = errors }
    }

    /**
     * Boolean flag indicating whether the current [value] passes all validation rules.
     *
     * True if no validation errors exist.
     */
    public val isValid: Boolean
        get() = errors.isEmpty()

    /**
     * Boolean flag indicating whether the current [value] fails one or more validation rules.
     *
     * True if there are validation errors.
     */
    public val isInvalid: Boolean
        get() = errors.isNotEmpty()
}