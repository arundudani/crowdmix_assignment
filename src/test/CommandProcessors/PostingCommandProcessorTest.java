package CommandProcessors;

import domain.Post;
import org.junit.Before;
import org.junit.Test;
import repositories.FollowerRepository;
import repositories.PostsRepository;

import java.util.SortedSet;

import static org.junit.Assert.*;

public class PostingCommandProcessorTest {

    @Before
    public void setUp() throws Exception {
        FollowerRepository.clear();
        PostsRepository.clear();
    }

    @Test
    public void testProcess() throws Exception {
        PostingCommandProcessor postingCommandProcessor = new PostingCommandProcessor();

        Post p1 = postingCommandProcessor.process("arun", "post1");
        Thread.sleep(100);
        Post p2 = postingCommandProcessor.process("john", "post2");
        Thread.sleep(100);
        Post p3 = postingCommandProcessor.process("john", "post3");
        Thread.sleep(100);
        Post p4 = postingCommandProcessor.process("arun", "post4");

        // this will force all posts to be returned
        SortedSet<Post> posts = PostsRepository.getPosts(post -> post.getPostDateTimeInMs() > 0);

        // check count
        assertTrue(posts.size() > 0);

        // check ordering
        assertTrue(posts.first().equals(p4));
        assertTrue(posts.stream().toArray()[1].equals(p3));
        assertTrue(posts.stream().toArray()[2].equals(p2));
        assertTrue(posts.last().equals(p1));

        // confirm all posts are distinct
        assertTrue(posts.stream().distinct().count() == 4);
    }
}