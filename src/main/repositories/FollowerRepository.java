package repositories;

import java.util.*;

/**
 * Created by arundudani on 16/04/2015.
 *
 * This class, consisting of only static members represents Follower/Followee relationships among users
 *
 * Strictly speaking, Followee relationship was not part of requirements, but it is very simplistic addition
 *  which can be useful in implementing observer pattern later on
 */
public class FollowerRepository {
    // Instead of Map<String, Set<String>> we can also use multimap

    // if User A follows B & C, and B follows A & C
    //  this map would contain:
    //  A -> B, C
    //  B -> A, C
    private static final Map<String, Set<String>> byFollowers = new HashMap<>();

    // if User A follows B & C, and B follows A & C
    //  this map would contain:
    //  A -> B
    //  B -> A
    //  C -> A, B
    private static final Map<String, Set<String>> byFollowees = new HashMap<>();

    public static void createRelationship(String follower, String beingFollowed) {
        if (follower.equals(beingFollowed)) {
            // user always follows self in any case
            return;
        }

        if(!byFollowers.containsKey(follower)) {
            byFollowers.put(follower, new TreeSet<>());
        }
        byFollowers.get(follower).add(beingFollowed);

        if (!byFollowees.containsKey(beingFollowed)) {
            byFollowees.put(beingFollowed, new TreeSet<>());
        }
        byFollowees.get(beingFollowed).add(follower);
    }

    public static Set<String> getUsersBeingFollowedBy(String follower) {
        return emptySetIfNull(byFollowers.get(follower));
    }

    public static Set<String> getFollowersOf(String followee) {
        return emptySetIfNull(byFollowees.get(followee));
    }

    public static void clear() {
        byFollowees.clear();
        byFollowers.clear();
    }

    private static Set<String> emptySetIfNull(Set<String> in) {
        return (in==null) ? new HashSet() : in;
    }

}
