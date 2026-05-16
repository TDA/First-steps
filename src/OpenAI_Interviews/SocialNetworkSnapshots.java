package OpenAI_Interviews;

// Problem Statement: Social Network Snapshots
//Overview
//
//Design a social network class that supports users, directed follow relationships, and immutable snapshots of the network state.
//Task
//
//Implement a class with these operations:
//
//    addUser(username) — register a user
//    follow(userA, userB) — userA starts following userB
//    unfollow(userA, userB) — userA stops following userB
//    getFollowers(user) — return the users currently following user, in sorted order
//    getFollowees(user) — return the users currently followed by user, in sorted order
//    createSnapshot() — store an immutable snapshot of the current follow graph and return its snapshot ID
//    isFollowInSnapshot(snapshotId, userA, userB) — report whether userA was following userB in that snapshot
//
//A snapshot must preserve the state exactly as it was at the moment it was created. Later calls to follow or unfollow must not change past snapshots.
//
//If the same follow relationship is added multiple times, it should still behave as a single relationship.
//
//If snapshotId does not refer to an existing snapshot, isFollowInSnapshot should return false.
//Example
//
//network = SocialNetwork()
//network.addUser("Nina")
//network.addUser("Omar")
//network.addUser("Pia")
//
//network.follow("Nina", "Omar")
//network.follow("Pia", "Omar")
//
//snap0 = network.createSnapshot()
//
//network.unfollow("Nina", "Omar")
//
//network.getFollowers("Omar")                       -> ["Pia"]
//network.isFollowInSnapshot(snap0, "Nina", "Omar") -> true
//network.isFollowInSnapshot(snap0, "Pia", "Omar")  -> true
//
//Constraints
//
//    Usernames are unique.
//    Follow relationships are directed: if A follows B, that does not imply B follows A.
//    Snapshots must remain unchanged after they are created.
//    getFollowers and getFollowees should return usernames in sorted order.
//    Snapshot queries should reflect the graph exactly as it existed when that snapshot was taken.

public class SocialNetworkSnapshots {
}
