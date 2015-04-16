package repositories;

import domain.Post;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by arundudani on 16/04/2015.
 *
 * This repository contains all the posts made by all users
 */
public class PostsRepository {
    private final static Set<Post> posts = new TreeSet<>();

    public static void newPost(Post post) {
        posts.add(post);
    }

    /**
     * Returns posts based on whatever criteria given in predicate
     * Order in treeset is decided by whatever is implemented by value class
     * @param predicate
     * @return
     */
    public static TreeSet<Post> getPosts(Predicate<Post> predicate) {
        return posts.stream().filter(predicate).collect(Collectors.toCollection(TreeSet::new));
    }

    public static void clear() {
        posts.clear();
    }
}
