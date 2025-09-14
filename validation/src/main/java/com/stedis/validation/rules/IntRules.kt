package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class IntInvalidValueReason : InvalidValueReason {
    NOT_POSITIVE,
    NOT_NEGATIVE,
    NOT_ZERO,
    MIN_VALUE,
    MAX_VALUE,
    NOT_EVEN,
    NOT_ODD,
}

public object IsPositiveIntRule : ValidationRule<Int> {
    override fun check(value: Int): ValidationResult =
        if (value > 0) ValidationResult.Valid
        else ValidationResult.Invalid(IntInvalidValueReason.NOT_POSITIVE)
}

public object IsNegativeIntRule : ValidationRule<Int> {
    override fun check(value: Int): ValidationResult =
        if (value < 0) ValidationResult.Valid
        else ValidationResult.Invalid(IntInvalidValueReason.NOT_NEGATIVE)
}

public object IsZeroIntRule : ValidationRule<Int> {
    override fun check(value: Int): ValidationResult =
        if (value == 0) ValidationResult.Valid
        else ValidationResult.Invalid(IntInvalidValueReason.NOT_ZERO)
}

public class MinValueIntRule(private val minValue: Int) : ValidationRule<Int> {
    override fun check(value: Int): ValidationResult =
        if (value >= minValue) ValidationResult.Valid
        else ValidationResult.Invalid(IntInvalidValueReason.MIN_VALUE)
}

public class MaxValueIntRule(private val maxValue: Int) : ValidationRule<Int> {
    override fun check(value: Int): ValidationResult =
        if (value <= maxValue) ValidationResult.Valid
        else ValidationResult.Invalid(IntInvalidValueReason.MAX_VALUE)
}

public object IsEvenIntRule : ValidationRule<Int> {
    override fun check(value: Int): ValidationResult =
        if (value % 2 == 0) ValidationResult.Valid
        else ValidationResult.Invalid(IntInvalidValueReason.NOT_EVEN)
}

public object IsOddIntRule : ValidationRule<Int> {
    override fun check(value: Int): ValidationResult =
        if (value % 2 != 0) ValidationResult.Valid
        else ValidationResult.Invalid(IntInvalidValueReason.NOT_ODD)
}