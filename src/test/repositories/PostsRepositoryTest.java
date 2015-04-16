package repositories;

import domain.Post;
import org.junit.Before;
import org.junit.Test;

import java.util.SortedSet;

import static org.junit.Assert.*;

public class PostsRepositoryTest {

    @Before
    public void setUp() throws Exception {
        FollowerRepository.clear();
        PostsRepository.clear();
    }

    @Test
    public void testGetPosts() throws Exception {
        Post p1 = new Post("arun", "post1");
        Thread.sleep(100);
        Post p2 = new Post("john", "post2");
        Thread.sleep(100);
        Post p3 = new Post("john", "post3");
        Thread.sleep(100);
        Post p4 = new Post("arun", "post4");

        // posts may be stored in any order. it is during retrieval we expect chronological order
        PostsRepository.newPost(p1);
        PostsRepository.newPost(p3);
        PostsRepository.newPost(p4);
        PostsRepository.newPost(p2);

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