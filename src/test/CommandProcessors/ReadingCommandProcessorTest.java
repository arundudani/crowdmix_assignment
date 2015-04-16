package CommandProcessors;

import domain.Post;
import org.junit.Before;
import org.junit.Test;
import repositories.FollowerRepository;
import repositories.PostsRepository;

import static org.junit.Assert.*;

public class ReadingCommandProcessorTest {

    @Before
    public void setUp() throws Exception {
        FollowerRepository.clear();
        PostsRepository.clear();
        PostingCommandProcessor postingCommandProcessor = new PostingCommandProcessor();

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
        ReadingCommandProcessor readingCommandProcessor = new ReadingCommandProcessor();

        String[] arunPosts = readingCommandProcessor.process("arun",null).split("\n");
        String[] johnPosts = readingCommandProcessor.process("john",null).split("\n");
        String[] andyPosts = readingCommandProcessor.process("andy",null).split("\n");

        // confirm lengths
        assertTrue(arunPosts.length == 2);
        assertTrue(johnPosts.length == 2);
        assertTrue(andyPosts.length == 1);  // empty string as result of split()

        // confirm empty strings as result for andy
        assertTrue(andyPosts[0].trim().length() == 0);

        // confirm arun's messages are in correct order
        assertTrue(arunPosts[0].startsWith("arun - post4 (") && arunPosts[1].startsWith("arun - post1 ("));

        // confirm john's messages are in correct order
        assertTrue(johnPosts[0].startsWith("john - post3 (") && johnPosts[1].startsWith("john - post2 ("));

    }
}