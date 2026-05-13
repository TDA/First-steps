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

import java.util.Comparator;
import java.util.PriorityQueue;

// Since we are stuck with Java, there is no better way to create a data class that also has setters in it. Alternative is to mark the fields as public but that's a very well known anti-pattern.
class GPUCredit {
    String creditID;
    int amount;
    int startTime;
    int expiryTime;
    int expiration;

    GPUCredit(String creditID, int amount, int timestamp, int expiration) {
        this.creditID = creditID;
        this.amount = amount;
        this.startTime = timestamp;
        this.expiryTime = timestamp + expiration;
        this.expiration = expiration;
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
    PriorityQueue<GPUCredit> creditPriorityQueue = new PriorityQueue<>(
            Comparator.comparing(GPUCredit::getStartTime).thenComparing(GPUCredit::getExpiryTime)
    );


    // O(1)
    private void addCredit(String creditId, int amount, int timestamp, int expiration) {
        creditPriorityQueue.add(new GPUCredit(creditId, amount, timestamp, expiration));
        System.out.println(creditPriorityQueue);
    }

    // O(n)
    private int getBalance(int timestamp) {
        // NOTE: Lazy expiry can be done here if get* requests are fewer than use* requests
        int sumBalance = 0;
        for (var credit : creditPriorityQueue) {
            if (timestamp >= credit.getStartTime() && timestamp <= credit.getExpiryTime()) sumBalance += credit.amount;
        }
        return sumBalance;
    }

    // O(n) -> I don't think this can be made more efficient even with a modified binary search. We would be able to find the floor and the ceiling for the search space by running a modified binary search. However, between the floor and the ceiling, we still need to iterate through all the possible credits if the pending amount is large enough. So worst case scenario this is still O, assuming that pending amount is such a large amount and spans the entire list.
    private void useCredit(int amount, int timestamp) {
        int pendingAmount = amount;
        var itemsInQueue = creditPriorityQueue.stream().toList();
        int index = 0; // 2

        // 1. Find the credits usable right now and reduce the amounts in each
        while (pendingAmount > 0 && index < itemsInQueue.size()) {
            var credit = itemsInQueue.get(index);
            if (credit == null) {
                index++;
                continue;
            }
            if (credit.amount <= 0) {
                // expire this credit grant for hard resets for future runs
                creditPriorityQueue.remove(credit);
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
                    // expire this credit grant for hard resets
                    creditPriorityQueue.remove(credit);
                }
            }
            index++;
        }
    }


    static void main() {
        var gpuCreditCalculator = new GPUCreditCalculator();

        // test case 1 - check balances for one entitlement / id
        gpuCreditCalculator.addCredit("microsoft", 10, 10, 30);

        System.out.println(gpuCreditCalculator.creditPriorityQueue);

        System.out.println(gpuCreditCalculator.getBalance(0)); //   -> 0
        System.out.println(gpuCreditCalculator.getBalance(10)); //  -> 10
        System.out.println(gpuCreditCalculator.getBalance(40)); //  -> 10
        System.out.println(gpuCreditCalculator.getBalance(41)); //  -> 0


        // test case 2  - use credit + check balances for one entitlement / id
        gpuCreditCalculator = new GPUCreditCalculator();
        gpuCreditCalculator.addCredit("amazon", 40, 10, 50);
        gpuCreditCalculator.useCredit(30, 30);
        System.out.println(gpuCreditCalculator.creditPriorityQueue);
        System.out.println(gpuCreditCalculator.getBalance(40)); //  -> 10


        // test case 3 - multiple credits to different entitlements and check balances (different from 1, here we already have amazon + google)
        gpuCreditCalculator.addCredit("google", 20, 60, 10);
        System.out.println(gpuCreditCalculator.getBalance(60)); //  -> 30
        System.out.println(gpuCreditCalculator.getBalance(61)); //  -> 20
        System.out.println(gpuCreditCalculator.getBalance(70)); //  -> 20
        System.out.println(gpuCreditCalculator.getBalance(71)); //  -> 0


        // test case 4 - complex case: Multiple credits available for a given ID with overlapping intervals. Balance is higher than all.
        gpuCreditCalculator.addCredit("microsoft", 10, 10, 30); // [10, 40] -> 10 credits
        gpuCreditCalculator.addCredit("microsoft", 10, 20, 30); // [20, 50] -> 10 credits
        gpuCreditCalculator.useCredit(100, 25);
        System.out.println(gpuCreditCalculator.getBalance(50)); //  -> 0
    }

}
