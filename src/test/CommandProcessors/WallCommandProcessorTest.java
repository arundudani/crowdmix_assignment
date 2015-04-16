package CommandProcessors;

import domain.Post;
import org.junit.Before;
import org.junit.Test;
import repositories.FollowerRepository;
import repositories.PostsRepository;

import static org.junit.Assert.*;

public class WallCommandProcessorTest {

    @Before
    public void setUp() throws Exception {
        FollowerRepository.clear();
        PostsRepository.clear();
        PostingCommandProcessor postingCommandProcessor = new PostingCommandProcessor();
        FollowsCommandProcessor followsCommandProcessor = new FollowsCommandProcessor();
        followsCommandProcessor.process("arun", "john");

        postingCommandProcessor.process("arun", "post1");
        Thread.sleep(100);
        postingCommandProcessor.process("john", "post2");
        Thread.sleep(100);
        postingCommandProcessor.process("john", "post3");
        Thread.sleep(100);
        postingCommandProcessor.process("arun", "post4");
    }

    @Test
    public void testProcess() throws Exception {
        WallCommandProcessor wallCommandProcessor = new WallCommandProcessor();

        // on arun's wall we should see 4 posts in reverse order of posting
        String[] arunWall = wallCommandProcessor.process("arun", null).split("\n");
        assertTrue(arunWall.length == 4);
        assertTrue(arunWall[0].startsWith("arun - post4 ("));
        assertTrue(arunWall[1].startsWith("john - post3 ("));
        assertTrue(arunWall[2].startsWith("john - post2 ("));
        assertTrue(arunWall[3].startsWith("arun - post1 ("));

        // on john's wall we should see 2 posts made by john in reverse order of posting
        String[] johnWall = wallCommandProcessor.process("john", null).split("\n");
        assertTrue(johnWall.length == 2);
        assertTrue(johnWall[0].startsWith("john - post3 ("));
        assertTrue(johnWall[1].startsWith("john - post2 ("));

        // on andy's wall we should not see anything - except for empty string inserted by split
        String[] andyWall = wallCommandProcessor.process("andy", null).split("\n");
        assertTrue(andyWall.length == 1);
        assertTrue(andyWall[0].trim().length() == 0);
    }
}