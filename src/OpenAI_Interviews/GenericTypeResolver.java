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

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.assertTrue;
import static OpenAI_Interviews.TestHelpers.runTest;


interface Type {
    List<Type> getItems();
}
record PrimitiveType (String name) implements Type {
    @Override
    public String toString() {
        return name;
    }

    @Override
    public List<Type> getItems() {
        return null;
    }
}
record GenericType (String name) implements Type {
    @Override
    public String toString() {
        return name;
    }
    @Override
    public List<Type> getItems() {
        return null;
    }
}
record TupleType (List<Type> items) implements Type {
    @Override
    public String toString() {
        return items.toString();
    }
    @Override
    public List<Type> getItems() {
        return items;
    }
}

record FunctionSignature(List<Type> parameterTypes, Type returnType) {}

public class GenericTypeResolver {

    // This is the actual method to implement
    Type resolveReturnType(FunctionSignature signature, List<Type> argumentTypes) {
        Map<String, Type> resolvedTypes = new HashMap<>();
        var declaredTypes = signature.parameterTypes();
        if (declaredTypes.size() != argumentTypes.size()) throw new IllegalArgumentException("Argument lengths do not match");

        for (int i = 0; i < declaredTypes.size(); i++) {
            var declaredType = declaredTypes.get(i);
            var actualType = argumentTypes.get(i);

            if (!match(declaredType, actualType, resolvedTypes)) {
                throw new IllegalArgumentException("Conflicting Generic type assignments");
            }
        }

        return getReturnType(signature.returnType(), resolvedTypes);
    }

    private Type getReturnType(Type type, Map<String, Type> resolvedTypes) {
        if (type instanceof PrimitiveType) return type;
        if (type instanceof GenericType) {
            return resolvedTypes.get(type.toString());
        } else {
            // Tuple types, can be nested
            List<Type> finalTypes = new ArrayList<>();
            for (var t : type.getItems()) {
                finalTypes.add(getReturnType(t, resolvedTypes));
            }
            return new TupleType(finalTypes);
        }
    }

    boolean match (Type declaredType, Type actualType, Map<String, Type> resolvedTypes) {
        if (declaredType instanceof PrimitiveType) {
            // primitive type, should be exact match
            return declaredType.equals(actualType);
        }

        if (declaredType instanceof TupleType) {
            var subtypes = declaredType.getItems();
            var actualSubtypes = actualType.getItems();
            if (subtypes != null) {
                // the actual type needs to be a tuple as well
                if (actualSubtypes == null) return false;
                // tuple type, check for length first before recursing
                if (subtypes.size() != actualType.getItems().size()) {
                    return false;
                }
                for (int i = 0; i < subtypes.size(); i++) {
                    if (!match(subtypes.get(i), actualSubtypes.get(i), resolvedTypes)) {
                        return false;
                    }
                }
            }
        } else if (declaredType instanceof GenericType) {
            // Generic types, need to check map
            // check if we have seen and stored this before, if so, check for mismatch
            Type resolvedType = resolvedTypes.get(declaredType.toString());
            if (resolvedType != null)
                return resolvedType.equals(actualType);
            //  resolve actual and store this for future rounds
            resolvedTypes.put(declaredType.toString(), actualType);
            return true;
        }
        return true;
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

        runTest("resolves concrete primitive return type", () -> {
            GenericTypeResolver resolver = new GenericTypeResolver();
            FunctionSignature signature = resolver.function(
                    List.of(resolver.primitive("int")),
                    resolver.primitive("bool")
            );

            assertEquals(
                    resolver.primitive("bool"),
                    resolver.resolveReturnType(signature, List.of(resolver.primitive("int"))),
                    "primitive return types should return themselves"
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
    // Helper methods, not the point of the interview - skip these.
    private Type primitive(String name) {
        return switch (name) {
            case "int", "float", "str", "bool" -> new PrimitiveType(name);
            default -> null;
        };
    }

    // Helper methods, not the point of the interview - skip these.
    private Type generic(String name) {
        return new GenericType(name);
    }

    // Helper methods, not the point of the interview - skip these.
    private Type tuple(Type... elements) {
        List<Type> items = List.of(elements);
        return new TupleType(items);
    }

    private FunctionSignature function(List<Type> parameterTypes, Type returnType) {
        return new FunctionSignature(parameterTypes, returnType);
    }
}
