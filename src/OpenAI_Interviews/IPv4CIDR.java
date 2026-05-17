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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.assertFalse;
import static OpenAI_Interviews.TestHelpers.assertNull;
import static OpenAI_Interviews.TestHelpers.assertTrue;
import static OpenAI_Interviews.TestHelpers.runTest;

public class IPv4CIDR {
    String ip;
    Integer prefixMask;
    Long[] ipParts = new Long[4];
    Long blockSize = 0L;
    Long currentAddressAsNumber = 0L;
    Long endAddressAsNumber = 0L;

    public IPv4CIDR(String cidrString) {
        var parts = cidrString.split("/");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid CIDR string");
        this.ip = parts[0];
        try {
            this.prefixMask = Integer.parseInt(parts[1]);
            if (prefixMask < 0 || prefixMask > 32) throw new IllegalArgumentException("Prefix mask invalid");
            String[] ipParts = ip.split("\\.");
            for (int i = 0; i < ipParts.length; i++) {
                this.ipParts[i] = Long.parseLong(ipParts[i]);
            }
            this.blockSize = 1L << (32 - this.prefixMask);

            this.currentAddressAsNumber = this.ipParts[0] << 24 | this.ipParts[1] << 16 | this.ipParts[2] << 8 | this.ipParts[3];
            // IMPORTANT: Need to round down to nearest blockSize for starting number
            this.currentAddressAsNumber = (currentAddressAsNumber / blockSize) * blockSize;
            this.endAddressAsNumber = this.blockSize + this.currentAddressAsNumber - 1;
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid PrefixMask string");
        }
    }

    public boolean hasNext() {
        return currentAddressAsNumber <= endAddressAsNumber;
    }

    public String next() {
        if (currentAddressAsNumber > endAddressAsNumber) return null;
        // convert currentAddressAsNumber to octets
        Long num = currentAddressAsNumber;
        List<String> parts = new ArrayList<>(4);
        parts.add(String.valueOf((num >> 24) & 255));
        parts.add(String.valueOf((num >> 16) & 255));
        parts.add(String.valueOf((num >> 8) & 255));
        parts.add(String.valueOf((num) & 255));

        currentAddressAsNumber++;
        return String.join(".", parts);
    }

    public static void main(String[] args) {
        runTest("iterates sample CIDR block from network address", () -> {
            IPv4CIDR iterator = new IPv4CIDR("172.16.5.9/30");

            assertTrue(iterator.hasNext(), "iterator should start with available addresses");
            assertEquals("172.16.5.8", iterator.next());
            assertEquals("172.16.5.9", iterator.next());
            assertTrue(iterator.hasNext(), "iterator should still have two addresses left");
            assertEquals("172.16.5.10", iterator.next());
            assertEquals("172.16.5.11", iterator.next());
            assertFalse(iterator.hasNext(), "iterator should be exhausted");
            assertNull(iterator.next(), "next should keep returning null after exhaustion");
            assertNull(iterator.next(), "next should remain exhausted consistently");
        });

        runTest("iterates slash 32 block as a single address", () -> {
            IPv4CIDR iterator = new IPv4CIDR("10.0.0.7/32");

            assertTrue(iterator.hasNext(), "single-address block should have one address");
            assertEquals("10.0.0.7", iterator.next());
            assertFalse(iterator.hasNext(), "single-address block should be exhausted");
            assertNull(iterator.next(), "single-address block should return null after exhaustion");
        });

        runTest("handles carry across octets", () -> {
            IPv4CIDR iterator = new IPv4CIDR("192.168.1.255/31");

            assertEquals("192.168.1.254", iterator.next());
            assertEquals("192.168.1.255", iterator.next());
            assertFalse(iterator.hasNext(), "slash 31 should have exactly two addresses");
        });

        runTest("handles network crossing into next octet", () -> {
            IPv4CIDR iterator = new IPv4CIDR("192.168.1.255/30");

            assertEquals("192.168.1.252", iterator.next());
            assertEquals("192.168.1.253", iterator.next());
            assertEquals("192.168.1.254", iterator.next());
            assertEquals("192.168.1.255", iterator.next());
            assertFalse(iterator.hasNext(), "slash 30 should have exactly four addresses");
        });

        runTest("iterates from zero address", () -> {
            IPv4CIDR iterator = new IPv4CIDR("0.0.0.0/30");

            assertEquals("0.0.0.0", iterator.next());
            assertEquals("0.0.0.1", iterator.next());
            assertEquals("0.0.0.2", iterator.next());
            assertEquals("0.0.0.3", iterator.next());
            assertFalse(iterator.hasNext(), "zero slash 30 block should have four addresses");
        });
    }
}
