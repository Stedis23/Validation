package com.stedis.validation

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationTest {

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
            if (value.length >= minLength) ValidationResult.Valid
            else ValidationResult.Invalid(TooShortReason)
    }

    @Test
    fun `unit() should create valid Validation with given value`() {
        val value = "test"

        val validation = Validation.unit(value)

        assertEquals(value, (validation as Valid).value)
    }

    @Test
    fun `bind() on valid Validation with passing ValidationRule returns Valid`() {
        val validation = Validation.unit("hello")
        val rule = AlwaysValidRule<String>()

        val result = validation.bind(rule)

        assertEquals("hello", (result as Valid).value)
    }

    @Test
    fun `bind() on valid Validation with failing ValidationRule returns Invalid with reason`() {
        val validation = Validation.unit("hello")
        val rule = AlwaysInvalidRule<String>(EmptyReason)

        val result = validation.bind(rule)

        val invalid = result as Invalid
        assertEquals(listOf(EmptyReason), invalid.errors)
    }

    @Test
    fun `bind() on invalid Validation accumulates new errors`() {
        val initialValidation = Validation.unit("abc").bind(AlwaysInvalidRule(EmptyReason))

        val result = initialValidation.bind(AlwaysInvalidRule(TooShortReason))

        val invalid = result as Invalid
        assertEquals(listOf(EmptyReason, TooShortReason), invalid.errors)
    }

    @Test
    fun `bind() with multiple passing ValidationRules returns Valid`() {
        val validation = Validation.unit("abcd")
        val rule1 = AlwaysValidRule<String>()
        val rule2 = MinLengthRule(3)

        val result = validation.bind(rule1).bind(rule2)

        assertEquals("abcd", (result as Valid).value)
    }


    @Test
    fun `bind() with multiple rules returns Invalid containing all failure reasons`() {
        val validation = Validation.unit("ab")
        val failingRule = AlwaysInvalidRule<String>(EmptyReason)
        val lengthRule = MinLengthRule(3)

        val result = validation.bind(failingRule).bind(lengthRule)

        val invalid = result as Invalid
        assertEquals(listOf(EmptyReason, TooShortReason), invalid.errors)
    }

    @Test
    fun `bind() retains existing errors when already invalid`() {
        val validation = Validation.unit("x")
            .bind(AlwaysInvalidRule(EmptyReason))

        val result = validation.bind(AlwaysInvalidRule(TooShortReason))

        val invalid = result as Invalid
        assertEquals(listOf(EmptyReason, TooShortReason), invalid.errors)
    }
}
