package OpenAI_Interviews;

// GPU Credit Calculator: Problem Description
//
//You are building a GPU credit calculator for an account. Credits are added over time, can be partially consumed, and expire after a fixed lifetime.
//
//Your job is to design a data structure that supports adding credits, spending credits, and querying the remaining balance as of a given timestamp.
//Interface and language equivalents
//
//Implement a class (or equivalent) with these operations:
//
//    addCredit(creditID, amount, timestamp, expiration) -> void
//    useCredit(timestamp, amount) -> void
//    getBalance(timestamp) -> int
//
//Notes:
//
//    addCredit and useCredit return nothing (i.e., void).
//    getBalance returns an integer.
//    When balance is 0, getBalance returns 0.
//    The interface above is illustrative; adjust naming and types to match your programming language.
//
//Semantics
//Credits
//
//Calling addCredit(creditID, amount, timestamp, expiration) creates a new credit grant:
//
//    It becomes usable starting at timestamp.
//    It expires after expiration time units (treat time as discrete integer timestamps).
//    From the examples, treat the usable window as inclusive (bracket notation):
//        Usable during [timestamp, timestamp + expiration]
//        Not usable at timestamp + expiration + 1
//    A credit grant may be partially spent across multiple useCredit calls. Any remaining amount stays available until it is fully consumed or expires.
//
//Important: creditID is a label, not a unique grant identifier. Multiple calls may reuse the same creditID; each addCredit call creates a separate grant.
//Spending
//
//Calling useCredit(timestamp, amount) attempts to spend credit at that timestamp.
//
//Rules:
//
//    You may only spend from credits that are usable at that timestamp (not expired yet).
//    Balance cannot go negative.
//    If amount exceeds the available balance at that timestamp, spend as much as possible and ignore the remainder.
//
//Querying
//
//Calling getBalance(timestamp) returns the total credit remaining on the account as of that timestamp:
//
//    Sum of all unexpired, unconsumed credit grants that are usable at timestamp.
//    If the remaining balance is 0, return 0.
//
//Expected Behaviour (Examples)
//Example 1
//
//gpuCredit = GPUCredit()
//
//gpuCredit.addCredit("microsoft", 10, 10, 30)
//
//gpuCredit.getBalance(0)   -> 0
//gpuCredit.getBalance(10)  -> 10
//gpuCredit.getBalance(40)  -> 10
//gpuCredit.getBalance(41)  -> 0
//
//
//Explanation:
//
//    Credit is usable during [10, 40]. At 41 it has expired.
//
//Example 2
//
//gpuCredit = GPUCredit()
//
//gpuCredit.addCredit("amazon", 40, 10, 50)
//
//gpuCredit.useCredit(30, 30)
//
//gpuCredit.getBalance(40)  -> 10
//
//gpuCredit.addCredit("google", 20, 60, 10)
//
//gpuCredit.getBalance(60)  -> 30
//gpuCredit.getBalance(61)  -> 20
//gpuCredit.getBalance(70)  -> 20
//gpuCredit.getBalance(71)  -> 0
//
//
//Explanation:
//
//    The first credit is usable during [10, 60].
//    After spending 30 at time 30, 10 remains and is still usable at time 40.
//    At time 60, the second credit becomes usable and the first credit is still usable (inclusive end), so total is 10 + 20 = 30.
//    At time 61, the first credit is expired, leaving 20.
//    The second credit is usable during [60, 70] and expired at 71.

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

// Since we are stuck with Java, there is no better way to create a data class that also has setters in it. Alternative is to mark the fields as public but that's a very well known anti-pattern.
class GPUCredit {
    String creditID;
    int amount;
    int startTime;
    int expiryTime;

    GPUCredit(String creditID, int amount, int timestamp, int expiration) {
        this.creditID = creditID;
        this.amount = amount;
        this.startTime = timestamp;
        this.expiryTime = timestamp + expiration;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getExpiryTime() {
        return expiryTime;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

public class GPUCreditCalculator {
    // The question actually has a gap around use credit. we appear to be using credits across entitlements.
    // So when we add a credit to a certain entitlement, the ID does nothing as the question is phrased
    // Clarify with interviewer if global useCredits or namespaced is needed -> i expect the per entitlement credit to be a follow-up
    // So we need to be careful about how we design this. because otherwise we need to completely restart how we key things.

    // Thinking through the original definition of the question, This seems to be from the point of view of a service provider who has a fixed amount
    // of quota overall and doesn't care who is using it as long as there is enough budget. it.
    // Reasonably certain that is the starting point where they don't care about tenancy and the extension will be about multi-tenancy.

    // Clarify assumption with the interviewer on whether the earliest timestamp credits must be used before future timestamp credits if credits have overlapping grants.
    // A priority queue only guarantees the order of the minimum item in the heap and does not guarantee order while iterating through the items.
    // While in practice for small sets this doesn't make a difference. For larger data sets it likely will start failing. So So we just use a regular plain old list here instead of using a priority queue.
    List<GPUCredit> creditsSortedList = new ArrayList<>();


    // O(1)
    private void addCredit(String creditId, int amount, int timestamp, int expiration) {
        // We offload the sorting to when credits are used, since there will be re-shuffles needed there either way
        // We keep this append-only to get O(1) perf
        creditsSortedList.add(new GPUCredit(creditId, amount, timestamp, expiration));
    }

    // O(n)
    private int getBalance(int timestamp) {
        // NOTE: Lazy expiry can be done here if get* requests are fewer than use* requests
        int sumBalance = 0;
        for (var credit : creditsSortedList) {
            if (timestamp >= credit.getStartTime() && timestamp <= credit.getExpiryTime()) sumBalance += credit.amount;
        }
        return sumBalance;
    }

    // O(n log on) -> I don't think this can be made more efficient even with a modified binary search. We would be able to find the floor and the ceiling for the search space by running a modified binary search. However, between the floor and the ceiling, we still need to iterate through all the possible credits if the pending amount is large enough. So worst case scenario this is still O, assuming that pending amount is such a large amount and spans the entire list.
    private void useCredit(int amount, int timestamp) {
        int pendingAmount = amount;
        int index = 0; // 2
        // This is O(n log n), but means that the other two methods can be lean
        creditsSortedList.sort(Comparator.comparing(GPUCredit::getStartTime).thenComparing(GPUCredit::getExpiryTime));

        // 1. Find the credits usable right now and reduce the amounts in each
        while (pendingAmount > 0 && index < creditsSortedList.size()) {
            var credit = creditsSortedList.get(index);
            if (credit == null) {
                index++;
                continue;
            }
            if (timestamp >= credit.getStartTime() && timestamp <= credit.getExpiryTime()) {
                // usable credit, debit this
                // case 1: enough to satisfy full debit
                if (credit.amount >= pendingAmount) {
                    credit.setAmount(credit.amount - pendingAmount);
                    pendingAmount = 0;
                } else {
                    // case 2: not enough to satisfy full, rollover to next
                    // Treating this as a likely extension because the original question tells us it's a best effort basis and if we run out of credits, we just ignore the pending amount.
                    pendingAmount -= credit.amount;
                    // optional if we want to keep soft-resets
                    credit.setAmount(0);
                }
            }
            index++;
        }

        // O(n + k log k) here since we are re-shuffling k items where k < n assuming at least one credit ran out
        creditsSortedList.removeIf(c -> c.amount <= 0);
    }


    public static void main(String[] args) {
        runTest("balance respects inclusive credit window", () -> {
            var gpuCreditCalculator = new GPUCreditCalculator();
            gpuCreditCalculator.addCredit("microsoft", 10, 10, 30);

            assertEquals(0, gpuCreditCalculator.getBalance(0), "credit should not be usable before start");
            assertEquals(10, gpuCreditCalculator.getBalance(10), "credit should be usable at start");
            assertEquals(10, gpuCreditCalculator.getBalance(40), "credit should be usable at inclusive expiry");
            assertEquals(0, gpuCreditCalculator.getBalance(41), "credit should expire after inclusive window");
        });

        runTest("useCredit spends available balance", () -> {
            var gpuCreditCalculator = new GPUCreditCalculator();
            gpuCreditCalculator.addCredit("amazon", 40, 10, 50);
            gpuCreditCalculator.useCredit(30, 30);

            assertEquals(10, gpuCreditCalculator.getBalance(40), "spending should reduce balance");
        });

        runTest("multiple credits combine and expire independently", () -> {
            var gpuCreditCalculator = new GPUCreditCalculator();
            gpuCreditCalculator.addCredit("amazon", 40, 10, 50);
            gpuCreditCalculator.useCredit(30, 30);
            gpuCreditCalculator.addCredit("google", 20, 60, 10);

            assertEquals(30, gpuCreditCalculator.getBalance(60), "overlapping credits should combine");
            assertEquals(20, gpuCreditCalculator.getBalance(61), "first credit should expire after time 60");
            assertEquals(20, gpuCreditCalculator.getBalance(70), "second credit should remain through inclusive expiry");
            assertEquals(0, gpuCreditCalculator.getBalance(71), "all credits should be expired");
        });

        runTest("overspend consumes all available active credits", () -> {
            var gpuCreditCalculator = new GPUCreditCalculator();
            gpuCreditCalculator.addCredit("microsoft", 10, 10, 30);
            gpuCreditCalculator.addCredit("microsoft", 10, 20, 30);
            gpuCreditCalculator.useCredit(100, 25);

            assertEquals(0, gpuCreditCalculator.getBalance(50), "overspend should leave no active balance");
        });
    }

}
