package com.stedis.validation.rules

import com.stedis.validation.InvalidValueReason
import com.stedis.validation.ValidationResult
import com.stedis.validation.ValidationRule

public enum class StringInvalidValueReason : InvalidValueReason {
    EMPTY,
    MIN_LENGTH,
    MAX_LENGTH,
    LENGTH_NOT_EQUAL,
    INVALID_FORMAT,
    MISSING_LETTERS,
    ONLY_LETTERS,
    MISSING_DIGIT,
    ONLY_DIGIT,
    SPACES_NOT_ALLOWED,
    SPECIAL_CHARACTERS_NOT_ALLOWED,
    MISSING_SPECIAL_CHARACTERS,
    UPPERCASE_NOT_ALLOWED,
    LOWERCASE_NOT_ALLOWED,
    MISSING_UPPERCASE,
    MISSING_LOWERCASE,
    NOT_STARTS_WITH,
    NOT_ENDS_WITH,
    NOT_CONTAIN_SUBSTRING,
    SUBSTRING_LENGTH_TOO_SHORT,
    SUBSTRING_LENGTH_TOO_LONG
}

public object IsNotEmptyRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.isNotEmpty()) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.EMPTY)
}

public class MinLengthRule(private val minLength: Int) : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.length >= minLength) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.MIN_LENGTH)
}

public class MaxLengthRule(private val maxLength: Int) : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.length <= maxLength) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.MAX_LENGTH)
}

public class LengthEqualRule(private val length: Int) : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.length == length) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.LENGTH_NOT_EQUAL)
}

public class RegexMatchRule(private val regex: Regex) : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (regex.matches(value)) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.INVALID_FORMAT)
}

public object ContainsLettersRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.any { it.isLetter() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.MISSING_LETTERS)
}

public object LettersOnlyRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.all { it.isLetter() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.ONLY_LETTERS)
}

public object ContainsDigitRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.any { it.isDigit() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.MISSING_DIGIT)
}

public object DigitOnlyRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.all { it.isDigit() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.ONLY_DIGIT)
}

public object NoSpacesRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (!value.contains(" ")) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.SPACES_NOT_ALLOWED)
}

public object NoSpecialCharactersRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.all { it.isLetterOrDigit() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.SPECIAL_CHARACTERS_NOT_ALLOWED)
}

public object ContainsSpecialCharactersRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.any { !it.isLetterOrDigit() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.MISSING_SPECIAL_CHARACTERS)
}

public object NoUppercaseRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.all { it.isLowerCase() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.UPPERCASE_NOT_ALLOWED)
}

public object NoLowercaseRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.all { it.isUpperCase() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.LOWERCASE_NOT_ALLOWED)
}

public object ContainsUppercaseRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.any { it.isUpperCase() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.MISSING_UPPERCASE)
}

public object ContainsLowercaseRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.any { it.isLowerCase() }) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.MISSING_LOWERCASE)
}

public class StartsWithRule(private val prefix: String) : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.startsWith(prefix)) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.NOT_STARTS_WITH)
}

public class EndsWithRule(private val suffix: String) : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.endsWith(suffix)) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.NOT_ENDS_WITH)
}

public class ContainsSubstringRule(
    private val substring: String,
    private val ignoreCase: Boolean = false,
) : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.contains(substring, ignoreCase)) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.NOT_CONTAIN_SUBSTRING)
}

public class SubstringMinLengthRule(
    private val startIndex: Int,
    private val endIndex: Int,
    private val minLength: Int,
) : ValidationRule<String> {
    override fun check(value: String): ValidationResult {
        val substring = value.substring(startIndex, endIndex)
        return if (substring.length >= minLength) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.SUBSTRING_LENGTH_TOO_SHORT)
    }
}

public class SubstringMaxLengthRule(
    private val startIndex: Int,
    private val endIndex: Int,
    private val maxLength: Int,
) : ValidationRule<String> {
    override fun check(value: String): ValidationResult {
        val substring = value.substring(startIndex, endIndex)
        return if (substring.length <= maxLength) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.SUBSTRING_LENGTH_TOO_LONG)
    }
}