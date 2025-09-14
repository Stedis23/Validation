package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class ByteInvalidValueReason : InvalidValueReason {
    NOT_POSITIVE,
    NOT_NEGATIVE,
    MIN_VALUE,
    MAX_VALUE,
}

public object ByteIsPositiveRule : ValidationRule<Byte> {
    override fun check(value: Byte): ValidationResult =
        if (value > 0) ValidationResult.Valid
        else ValidationResult.Invalid(ByteInvalidValueReason.NOT_POSITIVE)
}

public object ByteIsNegativeRule : ValidationRule<Byte> {
    override fun check(value: Byte): ValidationResult =
        if (value < 0) ValidationResult.Valid
        else ValidationResult.Invalid(ByteInvalidValueReason.NOT_NEGATIVE)
}

public class ByteMaxValueRule(private val maxValue: Byte) : ValidationRule<Byte> {
    override fun check(value: Byte): ValidationResult =
        if (value <= maxValue) ValidationResult.Valid
        else ValidationResult.Invalid(ByteInvalidValueReason.MAX_VALUE)
}

public class ByteMinValueRule(private val minValue: Byte) : ValidationRule<Byte> {
    override fun check(value: Byte): ValidationResult =
        if (value >= minValue) ValidationResult.Valid
        else ValidationResult.Invalid(ByteInvalidValueReason.MIN_VALUE)
}