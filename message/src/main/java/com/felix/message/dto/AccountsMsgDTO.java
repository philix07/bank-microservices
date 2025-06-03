package com.felix.message.dto;

public record AccountsMsgDTO(Long accountNumber, String name, String email, String mobileNumber) {
}

// A record in Java is a special type of class introduced in Java 16.
// It is used to model simple, immutable data structures with minimal boilerplate code.

// When you define a record, Java automatically generates:
// - A constructor
// - Getter methods (called component accessors)
// - equals(), hashCode(), and toString() methods

// Records are implicitly final and cannot extend other classes,
// but they can implement interfaces.

// Fields in a record are final and cannot be modified after the object is created.
// This makes records ideal for data transfer objects (DTOs) or value objects.

// Syntax example:
// public record Person(String name, int age) { }

// The above is equivalent to:
// public final class Person {
//     private final String name;
//     private final int age;
//     // constructor, getters, equals(), hashCode(), toString() are auto-generated
// }

// Notes:
// - Records cannot define mutable fields
// - You cannot add setters
// - All fields must be declared in the record header
