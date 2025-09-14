package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class FloatInvalidValueReason : InvalidValueReason {
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

public object IsPositiveFloatRule : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value > 0.0f) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.NOT_POSITIVE)
}

public object IsNegativeFloatRule : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value < 0.0f) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.NOT_NEGATIVE)
}

public object IsZeroFloatRule : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value == 0.0f) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.NOT_ZERO)
}

public class MinValueFloatRule(private val minValue: Float) : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value >= minValue) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.MIN_VALUE)
}

public class MaxValueFloatRule(private val maxValue: Float) : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value <= maxValue) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.MAX_VALUE)
}

public object IsEvenFloatRule : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value % 2 == 0.0f) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.NOT_EVEN)
}

public object IsOddFloatRule : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value % 2 != 0.0f) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.NOT_ODD)
}

public class IsPositiveFloatWithPrecisionRule(private val precision: Float) :
    ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value > precision) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.NOT_POSITIVE_WITH_PRECISION)
}

public object FloatIsIntegerRule : ValidationRule<Float> {
    override fun check(value: Float): ValidationResult =
        if (value % 1 == 0.0f) ValidationResult.Valid
        else ValidationResult.Invalid(FloatInvalidValueReason.HAS_FRACTIONAL_PART)
}