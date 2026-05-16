package OpenAI_Interviews;

// Problem Statement: Generic Type Resolver
//Overview
//
//You are given a small type system with primitives, generic type variables, nested tuple types, and function signatures.
//Task
//
//Implement data structures to represent these types, and implement a function that determines the concrete return type of a generic function when concrete argument types are supplied.
//
//Supported type forms:
//
//    primitive types such as int, float, str, bool
//    generic variables such as T, T1, U2
//    tuple types written as bracketed lists, such as [int, str] or [T1, [bool, str]]
//    function signatures consisting of a list of parameter types and one output type
//
//Your task has two parts:
//
//    implement string rendering for the type structures
//    implement return-type resolution for a function signature with generics
//
//When resolving a function call:
//
//    compare the declared parameter types with the concrete argument types
//    build a mapping from generic variables to concrete types
//    apply that mapping recursively to the declared return type
//
//Reject invalid calls such as:
//
//    wrong number of arguments
//    primitive/type-shape mismatch
//    conflicting assignments for the same generic
//
//Tuple matching must be recursive and structure-sensitive. For example, two tuple types only match if they have compatible element counts and compatible element types at each position.
//Example
//
//Function:
//  [[K, int], V, K] -> [V, K]
//
//Arguments:
//  [[str, int], bool, str]
//
//Result:
//  [bool, str]
//
//Here, K resolves to str and V resolves to bool.
//
//Second example:
//
//Function:
//  [X, [X, bool]] -> X
//
//Arguments:
//  [int, [str, bool]]
//
//This should fail because X would need to be both int and str.
//Constraints
//
//    Function signatures may have multiple parameters.
//    Tuple types may be nested.
//    Generic variables may appear more than once in a signature.
//    A generic variable must resolve consistently everywhere it appears in the same call.
//    Type resolution must distinguish between primitive types and tuple structure.

import java.util.List;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.assertTrue;
import static OpenAI_Interviews.TestHelpers.runTest;

public class GenericTypeResolver {
    // Helper methods, not the point of the interview - skip these.
    Type primitive(String name) {
        switch (name) {
            case "int":
                // fall through
            case "float":
                // fall through
            case "str":
                // fall through
            case "bool":
                return new PrimitiveType(name);
            default:
                throw new IllegalArgumentException("Not a primitive");
        }
    }

    // Helper methods, not the point of the interview - skip these.
    Type generic(String name) {
        try {
            primitive(name);
            return null;
        } catch (IllegalArgumentException iae) {
            return new GenericType(name);
        }
    }

    // Helper methods, not the point of the interview - skip these.
    Type tuple(List<Type> elements) {
        return new TupleType(elements);
    }

    FunctionSignature function(List<Type> parameterTypes, Type returnType) {
        return null;
    }

    Type resolveReturnType(FunctionSignature signature, List<Type> argumentTypes) {
        return null;
    }

    public static void main(String[] args) {
        runTest("primitive type renders as its name", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();

            assertEquals("int", resolver.primitive("int").toString());
            assertEquals("str", resolver.primitive("str").toString());
        });

        runTest("generic type renders as its variable name", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();

            assertEquals("T", resolver.generic("T").toString());
            assertEquals("U2", resolver.generic("U2").toString());
        });

        runTest("tuple type renders nested elements", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();

            assertEquals(
                    "[int, [bool, str]]",
                    resolver.tuple(
                            resolver.primitive("int"),
                            resolver.tuple(resolver.primitive("bool"), resolver.primitive("str"))
                    ).toString()
            );
        });

        runTest("resolves simple generic return type", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();
            Type t = resolver.generic("T");
            FunctionSignature identity = resolver.function(List.of(t), t);

            assertEquals(
                    resolver.primitive("int"),
                    resolver.resolveReturnType(identity, List.of(resolver.primitive("int"))),
                    "T should resolve to int"
            );
        });

        runTest("resolves nested tuple generics in return type", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();
            Type k = resolver.generic("K");
            Type v = resolver.generic("V");
            FunctionSignature signature = resolver.function(
                    List.of(
                            resolver.tuple(k, resolver.primitive("int")),
                            v,
                            k
                    ),
                    resolver.tuple(v, k)
            );

            assertEquals(
                    resolver.tuple(resolver.primitive("bool"), resolver.primitive("str")),
                    resolver.resolveReturnType(signature, List.of(
                            resolver.tuple(resolver.primitive("str"), resolver.primitive("int")),
                            resolver.primitive("bool"),
                            resolver.primitive("str")
                    )),
                    "nested tuple bindings should resolve recursively"
            );
        });

        runTest("rejects conflicting generic assignments", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();
            Type x = resolver.generic("X");
            FunctionSignature signature = resolver.function(
                    List.of(x, resolver.tuple(x, resolver.primitive("bool"))),
                    x
            );

            assertThrows(() -> resolver.resolveReturnType(signature, List.of(
                    resolver.primitive("int"),
                    resolver.tuple(resolver.primitive("str"), resolver.primitive("bool"))
            )));
        });

        runTest("rejects wrong number of arguments", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();
            FunctionSignature signature = resolver.function(
                    List.of(resolver.generic("T"), resolver.primitive("int")),
                    resolver.generic("T")
            );

            assertThrows(() -> resolver.resolveReturnType(signature, List.of(resolver.primitive("str"))));
        });

        runTest("rejects primitive mismatch", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();
            FunctionSignature signature = resolver.function(
                    List.of(resolver.primitive("int")),
                    resolver.primitive("bool")
            );

            assertThrows(() -> resolver.resolveReturnType(signature, List.of(resolver.primitive("str"))));
        });

        runTest("rejects tuple shape mismatch", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();
            Type t = resolver.generic("T");
            FunctionSignature signature = resolver.function(
                    List.of(resolver.tuple(t, resolver.primitive("int"))),
                    t
            );

            assertThrows(() -> resolver.resolveReturnType(signature, List.of(
                    resolver.tuple(resolver.primitive("str"), resolver.primitive("int"), resolver.primitive("bool"))
            )));
        });
    }

    private static void assertThrows(Runnable runnable) {
        boolean threw = false;
        try {
            runnable.run();
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        assertTrue(threw, "Expected IllegalArgumentException");
    }
}

interface Type {
}

record PrimitiveType (String name) implements Type {}
record GenericType (String name) implements Type {}
record TupleType (List<Type> items) implements Type {}

record FunctionSignature(List<Type> parameterTypes, Type returnType) {
}
