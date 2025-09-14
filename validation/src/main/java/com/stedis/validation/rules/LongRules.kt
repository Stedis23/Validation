package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class LongInvalidValueReason : InvalidValueReason {
    NOT_POSITIVE,
    NOT_NEGATIVE,
    NOT_ZERO,
    MIN_VALUE,
    MAX_VALUE,
    NOT_EVEN,
    NOT_ODD,
}

public object IsPositiveLongRule : ValidationRule<Long> {
    override fun check(value: Long): ValidationResult =
        if (value > 0) ValidationResult.Valid
        else ValidationResult.Invalid(LongInvalidValueReason.NOT_POSITIVE)
}

public object IsNegativeLongRule : ValidationRule<Long> {
    override fun check(value: Long): ValidationResult =
        if (value < 0) ValidationResult.Valid
        else ValidationResult.Invalid(LongInvalidValueReason.NOT_NEGATIVE)
}

public object IsZeroLongRule : ValidationRule<Long> {
    override fun check(value: Long): ValidationResult =
        if (value == 0L) ValidationResult.Valid
        else ValidationResult.Invalid(LongInvalidValueReason.NOT_ZERO)
}

public class MinValueLongRule(private val minValue: Long) : ValidationRule<Long> {
    override fun check(value: Long): ValidationResult =
        if (value >= minValue) ValidationResult.Valid
        else ValidationResult.Invalid(LongInvalidValueReason.MIN_VALUE)
}

public class MaxValueLongRule(private val maxValue: Long) : ValidationRule<Long> {
    override fun check(value: Long): ValidationResult =
        if (value <= maxValue) ValidationResult.Valid
        else ValidationResult.Invalid(LongInvalidValueReason.MAX_VALUE)
}

public object IsEvenLongRule : ValidationRule<Long> {
    override fun check(value: Long): ValidationResult =
        if (value % 2 == 0L) ValidationResult.Valid
        else ValidationResult.Invalid(LongInvalidValueReason.NOT_EVEN)
}

public object IsOddLongRule : ValidationRule<Long> {
    override fun check(value: Long): ValidationResult =
        if (value % 2 != 0L) ValidationResult.Valid
        else ValidationResult.Invalid(LongInvalidValueReason.NOT_ODD)
}