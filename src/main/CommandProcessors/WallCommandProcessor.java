package CommandProcessors;

import repositories.FollowerRepository;
import repositories.PostsRepository;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by arundudani on 16/04/2015.
 *
 * Implements <user name> wall command by displaying, in reverse chronological order, all the messaged posted
 *  by user as well as those of users being followed by this user
 */
public class WallCommandProcessor implements CommandProcessor<String> {
    @Override
    public String process(String user, String commandArgumentNotUsed) {
        // retrieve posts for those user is following as well as user itself
        Set<String> retrievePostsFor = FollowerRepository.getUsersBeingFollowedBy(user);
        retrievePostsFor.add(user);

        // what is happening here?
        // A: step 1 -> Criteria given to getPosts says that retrieve all posts where user is part of retrievePostsFor set
        //                  since getPosts() returns TreeSet, result is already sorted
        //    step 2 -> then loop over through each result, getting string representation of result
        //    step 3 -> those string representations are concatenated to single string for display purpose (separated by newline)
        return PostsRepository.getPosts(post -> retrievePostsFor.contains(post.getUser()))
                .stream()
                .map(post -> post.toString())
                .collect(Collectors.joining("\n"));
    }
}
