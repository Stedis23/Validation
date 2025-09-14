package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class CharInvalidValueReason : InvalidValueReason {
    NOT_A_LETTER,
    NOT_A_DIGIT,
    NOT_WHITESPACE,
    NOT_SPECIAL_CHARACTER,
    NOT_IN_RANGE,
}

public object CharIsLetterRule : ValidationRule<Char> {
    override fun check(value: Char): ValidationResult =
        if (value.isLetter()) ValidationResult.Valid
        else ValidationResult.Invalid(CharInvalidValueReason.NOT_A_LETTER)
}

public object CharIsDigitRule : ValidationRule<Char> {
    override fun check(value: Char): ValidationResult =
        if (value.isDigit()) ValidationResult.Valid
        else ValidationResult.Invalid(CharInvalidValueReason.NOT_A_DIGIT)
}

public object CharIsWhitespaceRule : ValidationRule<Char> {
    override fun check(value: Char): ValidationResult =
        if (value.isWhitespace()) ValidationResult.Valid
        else ValidationResult.Invalid(CharInvalidValueReason.NOT_WHITESPACE)
}

public object IsSpecialCharacterRule : ValidationRule<Char> {
    override fun check(value: Char): ValidationResult =
        if (value.isLetterOrDigit()) ValidationResult.Valid
        else ValidationResult.Invalid(CharInvalidValueReason.NOT_SPECIAL_CHARACTER)
}

public class CharInRangeRule(private val min: Char, private val max: Char) : ValidationRule<Char> {
    override fun check(value: Char): ValidationResult =
        if (value in min..max) ValidationResult.Valid
        else ValidationResult.Invalid(CharInvalidValueReason.NOT_IN_RANGE)
}