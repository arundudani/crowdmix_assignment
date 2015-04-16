package CommandProcessors;

import org.junit.Before;
import org.junit.Test;
import repositories.FollowerRepository;
import repositories.PostsRepository;

import java.util.Set;

import static org.junit.Assert.*;

public class FollowsCommandProcessorTest {

    @Before
    public void setUp() throws Exception {
        FollowerRepository.clear();
        PostsRepository.clear();
    }

    @Test
    public void testProcess() throws Exception {
        FollowsCommandProcessor followsCommandProcessor = new FollowsCommandProcessor();

        // User A follows B & C, and B follows A & C - setup in random order
        followsCommandProcessor.process("A", "B");
        followsCommandProcessor.process("B", "C");
        followsCommandProcessor.process("A", "C");
        followsCommandProcessor.process("B", "A");

        // some duplicates
        followsCommandProcessor.process("A", "C");
        followsCommandProcessor.process("B", "A");

        // follow self
        followsCommandProcessor.process("A", "A");
        followsCommandProcessor.process("C", "C");

        // tests
        testGetUsersBeingFollowedBy();
        testGetFollowersOf();
    }

    private void testGetUsersBeingFollowedBy() throws Exception {
        // for A it should return B & C
        Set<String> followedByA = FollowerRepository.getUsersBeingFollowedBy("A");
        assertTrue(followedByA.size() == 2 &&
                followedByA.contains("B") && followedByA.contains("C"));

        // for B it should return A & C
        Set<String> followedByB = FollowerRepository.getUsersBeingFollowedBy("B");
        assertTrue(followedByB.size() == 2 &&
                followedByB.contains("A") && followedByB.contains("C"));

        // for C nothing should be returned
        Set<String> followedByC = FollowerRepository.getUsersBeingFollowedBy("C");
        assertTrue(followedByC.size() == 0);

        // for C nothing should be returned
        Set<String> followedByD = FollowerRepository.getUsersBeingFollowedBy("D");
        assertTrue(followedByD.size() == 0);

    }

    private void testGetFollowersOf() throws Exception {
        // for A it should return B
        Set<String> followersOfA = FollowerRepository.getFollowersOf("A");
        assertTrue(followersOfA.size() == 1 &&
                followersOfA.contains("B"));

        // for B it should return A
        Set<String> followersOfB = FollowerRepository.getFollowersOf("B");
        assertTrue(followersOfB.size() == 1 &&
                followersOfB.contains("A"));

        // for C it should return A & B
        Set<String> followersOfC = FollowerRepository.getFollowersOf("C");
        assertTrue(followersOfC.size() == 2 &&
                followersOfC.contains("A") && followersOfC.contains("B"));

        // for D it should return nothing
        Set<String> followersOfD = FollowerRepository.getFollowersOf("D");
        assertTrue(followersOfD.size() == 0);
    }
}