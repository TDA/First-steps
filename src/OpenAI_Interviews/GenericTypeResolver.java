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

public class GenericTypeResolver {
}
