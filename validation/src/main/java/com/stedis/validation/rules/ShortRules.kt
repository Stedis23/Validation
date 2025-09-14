package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

enum class ShortInvalidValueReason : InvalidValueReason {
    NOT_POSITIVE,
    NOT_NEGATIVE,
    NOT_ZERO,
    MIN_VALUE,
    MAX_VALUE,
    NOT_EVEN,
    NOT_ODD,
    NOT_POSITIVE_WITH_PRECISION,
    HAS_FRACTIONAL_PART,
}

public object ShortIsPositiveRule : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value > 0) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.NOT_POSITIVE)
}

public object ShortIsNegativeRule : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value < 0) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.NOT_NEGATIVE)
}

public object ShortIsZeroRule : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value == 0.toShort()) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.NOT_ZERO)
}

public class ShortMinValueRule(private val minValue: Short) : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value >= minValue) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.MIN_VALUE)
}

public class ShortMaxValueRule(private val maxValue: Short) : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value <= maxValue) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.MAX_VALUE)
}

public object ShortIsEvenRule : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value % 2 == 0) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.NOT_EVEN)
}

public object ShortIsOddRule : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value % 2 != 0) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.NOT_ODD)
}

public class ShortIsPositiveWithPrecisionRule(private val precision: Short) :
    ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value > precision) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.NOT_POSITIVE_WITH_PRECISION)
}

public object ShortHasNoFractionalPartRule : ValidationRule<Short> {
    override fun check(value: Short): ValidationResult =
        if (value.toDouble() % 1 == 0.0) ValidationResult.Valid
        else ValidationResult.Invalid(ShortInvalidValueReason.HAS_FRACTIONAL_PART)
}