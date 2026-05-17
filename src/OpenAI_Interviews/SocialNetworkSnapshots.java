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


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.assertFalse;
import static OpenAI_Interviews.TestHelpers.assertTrue;
import static OpenAI_Interviews.TestHelpers.runTest;

class UserNode implements Comparable<UserNode> {
    String name;
    TreeSet<UserNode> followers;
    TreeSet<UserNode> followees;

    public UserNode(String name) {
        this.name = name;
        this.followers = new TreeSet<>();
        this.followees = new TreeSet<>();
    }
    public UserNode(String name, TreeSet<UserNode> followers, TreeSet<UserNode> followees) {
        this.name = name;
        this.followers = followers;
        this.followees = followees;
    }

    public UserNode(UserNode other) {
        this.name = other.getName();
        this.followers = new TreeSet<>(other.followers);
        this.followees = new TreeSet<>(other.followees);
    }

    @Override
    public int compareTo(UserNode o) {
        return this.name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }
}

public class SocialNetworkSnapshots {
    // Network itself can just be a large lookup map
    Map<String, UserNode> network = new HashMap<>();

    // Network versions can be stored for easy retrieval and immutability
    Map<Integer, Map<String, UserNode>> snapshots = new HashMap<>();

    Integer currentSnapshotVersion = 0;


    // Usernames are unique - so nodes can be indexed on names
    // DAG -> Follow relationships are directed: if A follows B, that does not imply B follows A.
    // getFollowers and getFollowees should return usernames in sorted order. -> Needs a TreeSet for holding followers

    List<String> getFollowers(String user) {
        var userNode = network.get(user);
        if (userNode == null) return null;
        return userNode.followers.stream().map(UserNode::getName).toList();
    }

    List<String> getFollowees(String user) {
        var userNode = network.get(user);
        if (userNode == null) return null;
        return userNode.followees.stream().map(UserNode::getName).toList();
    }

    void addUser(String name) {
        network.put(name, new UserNode(name));
    }

    void follow(String follower, String followee) {
        var followerUser = network.get(follower);
        var followeeUser = network.get(followee);
        if (followeeUser == null || followerUser == null) return;
        followerUser.followees.add(followeeUser);
        followeeUser.followers.add(followerUser);
    }

    void unfollow(String follower, String followee) {
        var followerUser = network.get(follower);
        var followeeUser = network.get(followee);
        if (followeeUser == null || followerUser == null) return;
        followerUser.followees.remove(followeeUser);
        followeeUser.followers.remove(followerUser);
    }

    int createSnapshot() {
        var snapshotId = currentSnapshotVersion++;
        var networkSnapshot = deepCopy(network);
        snapshots.put(snapshotId, networkSnapshot);
        return snapshotId;
    }

    private Map<String, UserNode> deepCopy(Map<String, UserNode> network) {
        Map<String, UserNode> deepCopy = new HashMap<>();
        for (var entry : network.entrySet()) {
            deepCopy.put(entry.getKey(), new UserNode(entry.getValue()));
        }
        return deepCopy;
    }

    boolean isFollowInSnapshot(Integer snapshotVersion, String follower, String followee) {
        var snapshot = snapshots.get(snapshotVersion);
        if (snapshot == null) return false;

        var followerUser = snapshot.get(follower);
        var followeeUser = snapshot.get(followee);
        if (followeeUser == null || followerUser == null) return false;
        return followerUser.followees.contains(followeeUser) && followeeUser.followers.contains(followerUser);
    }

    public static void main(String[] args) {
        runTest("adds users and returns empty relationships for isolated users", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");

            assertEquals(List.of(), network.getFollowers("Nina"));
            assertEquals(List.of(), network.getFollowees("Nina"));
            assertEquals(List.of(), network.getFollowers("Omar"));
            assertEquals(List.of(), network.getFollowees("Omar"));
        });

        runTest("follow creates directed relationship in both indexes", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");
            network.follow("Nina", "Omar");

            assertEquals(List.of("Nina"), network.getFollowers("Omar"));
            assertEquals(List.of("Omar"), network.getFollowees("Nina"));
            assertEquals(List.of(), network.getFollowers("Nina"));
            assertEquals(List.of(), network.getFollowees("Omar"));
        });

        runTest("followers and followees are returned in sorted order", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Zara");
            network.addUser("Nina");
            network.addUser("Omar");
            network.addUser("Pia");

            network.follow("Zara", "Omar");
            network.follow("Nina", "Omar");
            network.follow("Pia", "Omar");
            network.follow("Omar", "Zara");
            network.follow("Omar", "Nina");

            assertEquals(List.of("Nina", "Pia", "Zara"), network.getFollowers("Omar"));
            assertEquals(List.of("Nina", "Zara"), network.getFollowees("Omar"));
        });

        runTest("duplicate follow is idempotent", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");

            network.follow("Nina", "Omar");
            network.follow("Nina", "Omar");
            network.follow("Nina", "Omar");

            assertEquals(List.of("Nina"), network.getFollowers("Omar"));
            assertEquals(List.of("Omar"), network.getFollowees("Nina"));
        });

        runTest("unfollow removes relationship from both indexes", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");
            network.addUser("Pia");

            network.follow("Nina", "Omar");
            network.follow("Pia", "Omar");
            network.unfollow("Nina", "Omar");

            assertEquals(List.of("Pia"), network.getFollowers("Omar"));
            assertEquals(List.of(), network.getFollowees("Nina"));
            assertEquals(List.of("Omar"), network.getFollowees("Pia"));
        });

        runTest("unknown users are ignored for follow and unfollow", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");

            network.follow("Nina", "Missing");
            network.follow("Missing", "Omar");
            network.unfollow("Missing", "Omar");
            network.unfollow("Nina", "Missing");

            assertEquals(List.of(), network.getFollowees("Nina"));
            assertEquals(List.of(), network.getFollowers("Omar"));
        });

        runTest("snapshot preserves follow state after later unfollow", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");
            network.addUser("Pia");
            network.follow("Nina", "Omar");
            network.follow("Pia", "Omar");

            int snapshotId = network.createSnapshot();
            network.unfollow("Nina", "Omar");

            assertEquals(List.of("Pia"), network.getFollowers("Omar"));
            assertTrue(network.isFollowInSnapshot(snapshotId, "Nina", "Omar"), "snapshot should preserve removed relationship");
            assertTrue(network.isFollowInSnapshot(snapshotId, "Pia", "Omar"), "snapshot should preserve existing relationship");
        });

        runTest("snapshot does not include relationships added later", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");
            network.addUser("Pia");
            network.follow("Nina", "Omar");

            int snapshotId = network.createSnapshot();
            network.follow("Pia", "Omar");

            assertTrue(network.isFollowInSnapshot(snapshotId, "Nina", "Omar"), "snapshot should include existing relationship");
            assertFalse(network.isFollowInSnapshot(snapshotId, "Pia", "Omar"), "snapshot should not include later relationship");
        });

        runTest("multiple snapshots preserve different versions", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");
            network.addUser("Pia");

            network.follow("Nina", "Omar");
            int snapshot0 = network.createSnapshot();

            network.follow("Pia", "Omar");
            int snapshot1 = network.createSnapshot();

            network.unfollow("Nina", "Omar");
            int snapshot2 = network.createSnapshot();

            assertTrue(network.isFollowInSnapshot(snapshot0, "Nina", "Omar"), "snapshot0 should include Nina -> Omar");
            assertFalse(network.isFollowInSnapshot(snapshot0, "Pia", "Omar"), "snapshot0 should not include Pia -> Omar");

            assertTrue(network.isFollowInSnapshot(snapshot1, "Nina", "Omar"), "snapshot1 should include Nina -> Omar");
            assertTrue(network.isFollowInSnapshot(snapshot1, "Pia", "Omar"), "snapshot1 should include Pia -> Omar");

            assertFalse(network.isFollowInSnapshot(snapshot2, "Nina", "Omar"), "snapshot2 should not include removed Nina -> Omar");
            assertTrue(network.isFollowInSnapshot(snapshot2, "Pia", "Omar"), "snapshot2 should still include Pia -> Omar");
        });

        runTest("invalid snapshot id returns false", () -> {
            SocialNetworkSnapshots network = new SocialNetworkSnapshots();

            network.addUser("Nina");
            network.addUser("Omar");
            network.follow("Nina", "Omar");

            assertFalse(network.isFollowInSnapshot(999, "Nina", "Omar"), "unknown snapshot should return false");
        });
    }
}
