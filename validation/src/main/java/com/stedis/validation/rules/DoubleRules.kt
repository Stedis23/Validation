package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class DoubleInvalidValueReason : InvalidValueReason {
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

public object IsPositiveDoubleRule : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value > 0) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.NOT_POSITIVE)
}

public object IsNegativeDoubleRule : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value < 0) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.NOT_NEGATIVE)
}

public object IsZeroDoubleRule : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value == 0.0) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.NOT_ZERO)
}

public class MinValueDoubleRule(private val minValue: Double) : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value >= minValue) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.MIN_VALUE)
}

public class MaxValueDoubleRule(private val maxValue: Double) : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value <= maxValue) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.MAX_VALUE)
}

public object IsEvenDoubleRule : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value % 2 == 0.0) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.NOT_EVEN)
}

public object IsOddDoubleRule : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value % 2 != 0.0) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.NOT_ODD)
}

public class IsPositiveDoubleWithPrecisionRule(private val precision: Double) :
    ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value > precision) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.NOT_POSITIVE_WITH_PRECISION)
}

public object DoubleIsIntegerRule : ValidationRule<Double> {
    override fun check(value: Double): ValidationResult =
        if (value % 1 == 0.0) ValidationResult.Valid
        else ValidationResult.Invalid(DoubleInvalidValueReason.HAS_FRACTIONAL_PART)
}