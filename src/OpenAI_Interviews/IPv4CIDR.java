package OpenAI_Interviews;

// Problem Statement: IPv4 CIDR Iterator
//Overview
//
//Implement an iterator over all IPv4 addresses in a CIDR block.
//Task
//
//Create a class that is initialized with a CIDR string such as 172.16.0.0/30. The iterator should return each IPv4 address in that block in ascending order, starting from the network address of the block and ending at the last address in the block.
//
//Implement these methods:
//
//    constructor with a CIDR string
//    next() — return the next IP address as a string, or the language-appropriate null-like exhausted value when the iterator is exhausted
//    hasNext() — return whether another address is still available
//
//If the input IP is not already the network address for the prefix, treat the block as the CIDR network that contains it.
//Example
//
//iter = IPv4CIDRIterator("172.16.5.9/30")
//
//iter.next()    -> "172.16.5.8"
//iter.next()    -> "172.16.5.9"
//iter.hasNext() -> true
//iter.next()    -> "172.16.5.10"
//iter.next()    -> "172.16.5.11"
//iter.next()    -> null
//iter.hasNext() -> false
//
//A /30 block contains 4 addresses. The given IP is inside the block whose network address is 172.16.5.8.
//Constraints
//
//    Input uses valid IPv4 CIDR notation.
//    The prefix length is between 0 and 32 inclusive.
//    The iterator must return every address in the CIDR block, including the network address and the last address in the block.
//    Addresses must be returned in increasing numeric order within the block.
//    Once all addresses in the block have been returned, next() must continue to report exhaustion consistently.
//    hasNext() must accurately reflect whether another address can still be produced.

public class IPv4CIDR {
}
