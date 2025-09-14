package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class BooleanInvalidValueReason : InvalidValueReason {
    NOT_TRUE,
    NOT_FALSE,
}

public object IsTrueRule : ValidationRule<Boolean> {
    override fun check(value: Boolean): ValidationResult =
        if (value) ValidationResult.Valid
        else ValidationResult.Invalid(BooleanInvalidValueReason.NOT_TRUE)
}

public object IsFalseRule : ValidationRule<Boolean> {
    override fun check(value: Boolean): ValidationResult =
        if (!value) ValidationResult.Valid
        else ValidationResult.Invalid(BooleanInvalidValueReason.NOT_FALSE)
}