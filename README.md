# Validation Library for Kotlin

Welcome to the comprehensive guide on using our Kotlin validation library!  
This library allows you to create, combine, and apply validation rules to any data easily and
cleanly.

---

## Table of Contents

- [Quick start](#quick-start)
- [What is it and why use it?](#what-is-it-and-why-use-it)
- [How to install](#how-to-install)
- [Validation basics](#validation-basics)
- [Creating custom validation rules](#creating-custom-validation-rules)
- [Composing multiple rules](#composing-multiple-rules)
- [Creating custom `InvalidValueReason`](#creating-custom-invalidvaluereason)
- [Validating values with lists or rule compositions](#validating-values-with-lists-or-rule-compositions)
- [The
  `Validated` class: handy validated value holder](#the-validated-class-handy-validated-value-holder)
- [Handling validation results](#handling-validation-results)
- [Built-in rules for strings and basic types](#built-in-rules-for-strings-and-basic-types)

---

## Quick start

```kotlin
val password = "YourPassword123!"

// Validate your data by specifying a set of rules
val result = password.validate(
    MinLengthRule(12) +
            ContainsUppercaseRule +
            ContainsLowercaseRule +
            ContainsDigitRule +
            ContainsSpecialCharactersRule
)

// Use the validation result for further actions
result
    .onValid { println("success value $it") }
    .onInvalid { _, errors -> println("has errors: $errors") }
```

---

## What is it and why use it?

Validation is a critical part of many applications where user input or data must satisfy certain
constraints (e.g., non-empty name, valid email format, number within range).

**This library provides:**

- Abstractions for validated values and their error reasons
- Tools for creating custom validation rules
- Ability to group rules into composite sets
- Clean and concise functions for validating data and handling errors
- The `Validated` class representing a value along with its validation state and errors

---

## How to install

Add this to your Gradle dependencies (example):

```kotlin
dependencies {
    implementation("io.github.stedis23:validation:1.0.0")
}
```

---

## Validation basics

### What is a validation rule?

A validation rule checks a value of type T and returns either a success or a list of failure
reasons.

Typical interface:

```kotlin
interface ValidationRule<T> {
    fun validate(value: T): Validation<T>
}
```

Where Validation<T> has implementations:

- `Valid<T>(value: T)` — validation passed
- `Invalid<T>(value: T, errors: List<InvalidValueReason>)` — validation failed with reasons

---

## Creating custom validation rules

Make your own rule by implementing `ValidationRule<T>`, e.g.:

```kotlin
public object IsNotEmptyRule : ValidationRule<String> {
    override fun check(value: String): ValidationResult =
        if (value.isNotEmpty()) ValidationResult.Valid
        else ValidationResult.Invalid(StringInvalidValueReason.EMPTY)
}
```

We check that the string is not empty; if it is, we return an error.

---

## Composing multiple rules

```kotlin
public interface ValidationRulesComposition<T> {
    fun getComposition(value: T): List<ValidationRule<T>>
}
```

Example composite rules for password validation:

```kotlin
public object PasswordValidationRules : ValidationRulesComposition<String> {
    override fun getComposition(value: String) =
        MinLengthRule(12) +
                ContainsUppercaseRule +
                ContainsLowercaseRule +
                ContainsDigitRule +
                ContainsSpecialCharactersRule
}
```

---

## Creating custom `InvalidValueReason`

You can define your own reason types for better error description:

```kotlin
public object EmptyStringReason : InvalidValueReason

public enum class BooleanInvalidValueReason : InvalidValueReason {
    NOT_TRUE,
    NOT_FALSE,
}

public data class SampleReason(message: String) : InvalidValueReason

```

---

## Validating values with lists or rule compositions

Using composition
Example:

```kotlin
val password = "pass123"
val result = password.validate(
    composition = PasswordRules(),
    onValid = { "Password invalid" },
    onInvalid = { value, errors -> "Error: $errors" }
)
println(result)
```

Using list of rules directly
Example:

```kotlin
val rules = listOf(NotEmptyRule(), MinLengthRule(5))
val username = "John"
val validation = username.validate(rules)

validation.fold(
    onValid = { println("Username invalid: $it") },
    onInvalid = { _, errors -> println("Errors: $errors") }
)

```

Using single rule
Example:

```kotlin
val ageRule = MinValueRule(18)
val ageValidation = 16.validate(ageRule)

ageValidation.onInvalid { value, errors ->
    println("Age $value is not correct: $errors")
}

```

---

## The `Validated` class: handy validated value holder

`Validated<T>` holds a value with its validation rules and automatically tracks errors on value
changes.

Example:

```kotlin
val validatedName = Validated<String>(
    initialValue = "Anya",
    rules = NotEmptyRule + MinLengthRule(3)
)

println(validatedName.isValid) // true

validatedName.value = "Al"

if (validatedName.isInvalid) {
    println("Errors: ${validatedName.errors}")
}
```

This is ideal for UI inputs and forms where live validation and error feedback is needed.

---

## Handling validation results

`Validation<T>` can be:

- `Valid<T>` — success
- `Invalid<T>` — failure + error reasons

Useful helper methods:

- `fold(onValid, onInvalid)` — handle both cases
- `onValid { ... }` — execute on success
- `onInvalid { ... }` — execute on failure

Example:

```kotlin
val validation = email.validate(emailRules)

validation.fold(
    onValid = { value -> println("Email valid: $value") },
    onInvalid = { _, errors -> println("Email errors: $errors") }
)

//or

validation
    .onValid { value -> println("Email valid: $value") }
    .onInvalid { _, errors -> println("Email errors: $errors") }
```

---

## Built-in rules for strings and basic types

The library includes a set of ready-to-use validation rules for strings and basic types in the
package:

```kotlin
package com.stedis.validation.rules
```

---

## Summary

- Create custom rules by implementing ValidationRule<T>
- Group rules with composition interfaces
- Validate data with direct lists or compositions
- Use Validated to hold a value with live validation
- Handle results cleanly with .fold(), .onValid(), .onInvalid()
- Extend error reasons with your own InvalidValueReason classes
- Utilize built-in rules from com.stedis.validation.rules package for common validations
- This library is easy to expand and flexible. Create your own validations, structure them well, and
  keep your data integrity solid!