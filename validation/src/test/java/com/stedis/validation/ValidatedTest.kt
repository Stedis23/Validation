package com.stedis.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidatedTest {

    private object EmptyReason : InvalidValueReason
    private object TooShortReason : InvalidValueReason

    private class AlwaysValidRule<T> : ValidationRule<T> {
        override fun check(value: T): ValidationResult = ValidationResult.Valid
    }

    private class AlwaysInvalidRule<T>(private val reason: InvalidValueReason) : ValidationRule<T> {
        override fun check(value: T): ValidationResult = ValidationResult.Invalid(reason)
    }

    private class MinLengthRule(private val minLength: Int) : ValidationRule<String> {
        override fun check(value: String): ValidationResult =
            if (value.length >= minLength) ValidationResult.Valid else ValidationResult.Invalid(
                TooShortReason
            )
    }

    private class SimpleRulesComposition(private val minLength: Int) :
        ValidationRulesComposition<String> {
        override fun getComposition(value: String): List<ValidationRule<String>> =
            listOf(MinLengthRule(minLength))
    }

    @Test
    fun `init with valid value and single rule should have no errors`() {
        val rule = MinLengthRule(3)

        val validated = Validated("abcd", rule)

        assertTrue(validated.errors.isEmpty())
    }

    @Test
    fun `init with invalid value and multiple rules collects errors`() {
        val rules = listOf(
            AlwaysInvalidRule(EmptyReason),
            MinLengthRule(5)
        )

        val validated = Validated("abc", rules)

        assertEquals(listOf(EmptyReason, TooShortReason), validated.errors)
    }

    @Test
    fun `constructor with ValidationRulesComposition applies composed rules`() {
        val composition = SimpleRulesComposition(4)

        val validated = Validated("abc", composition)

        assertTrue(validated.isInvalid)
        assertTrue(validated.errors.contains(TooShortReason))
    }

    @Test
    fun `setting value triggers re-validation and updates errors`() {
        val rule = MinLengthRule(5)
        val validated = Validated("123456", rule)
        assertTrue(validated.isValid)

        validated.value = "123"

        assertTrue(validated.isInvalid)
        assertTrue(validated.errors.contains(TooShortReason))
    }

    @Test
    fun `setting value to valid clears previous errors`() {
        val rule = MinLengthRule(5)
        val validated = Validated("123", rule)
        assertTrue(validated.isInvalid)

        validated.value = "12345"

        assertTrue(validated.isValid)
        assertTrue(validated.errors.isEmpty())
    }

    @Test
    fun `isValid is true when no errors`() {
        val rule = AlwaysValidRule<String>()

        val validated = Validated("ok", rule)

        assertTrue(validated.isValid)
    }

    @Test
    fun `isInvalid is true when there are errors`() {
        val rule = AlwaysInvalidRule<String>(EmptyReason)

        val validated = Validated("fail", rule)

        assertTrue(validated.isInvalid)
    }
}